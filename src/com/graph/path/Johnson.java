package com.graph.path;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 最短路径Johnson算法
 * 使用SPFA算法、Bellman-Ford算法的图
 */
public class Johnson {
    
    private static final int mx = Integer.MAX_VALUE;
    // 顶点
    private static final char[] vertexs = { '0', '1', '2', '3', '4', '5', '6' };
    // 有向边，行为起始节点 -> 列为目标节点
    private static final int[][] edges = {
           //  0   1   2   3   4   5   6
            {  0,  6,  5,  5, mx, mx, mx },// 0
            { mx,  0, mx, mx, -1, mx, mx },// 1
            { mx, -2,  0, mx,  1, mx, mx },// 2
            { mx, mx, -2,  0, mx, -1, mx },// 3
            { mx, mx, mx, mx,  0, mx,  3 },// 4
            { mx, mx, mx, mx, mx,  0,  3 },// 5
            { mx, mx, mx, mx, mx, mx,  0 } // 6
    };
    
    private static void print(final int value) {
        if (value == Integer.MAX_VALUE) {
            System.out.print("mx  ");
            return;
        }
        System.out.print(value + "  ");
    }
    
    
    /**
     * 构建新的矩阵，必须构建正确
     */
    private static void matrix(char[] nvertexs, int[][] nedges) {
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                nedges[i][j] = edges[i][j];
                print(nedges[i][j]);
            }
            nedges[i][vertexs.length] = mx;// 每一行的最后一列都为0
            System.out.print("mx");
            System.out.println();
        }
        
        /**
         * 最后一行，必须是无穷，没有回路；否则就会有“负环”；
         * 最后一个元素为0
         */
        for (int i = 0; i < vertexs.length; i++) {
            nedges[vertexs.length][i] = 0;
            print(nedges[vertexs.length][i]);
            nvertexs[i] = vertexs[i];
        }
        nedges[vertexs.length][vertexs.length] = 0;
        System.out.print("0");
        System.out.println();
        nvertexs[vertexs.length] = 's';
    }
    
    public static void exec() {
        /**
         * 1. 增加虚拟节点，构造新的边集，虚拟节点位于二维数组尾部;
         * 虚拟节点到每个顶点的距离都为0，每个顶点到虚拟节点的距离都为无穷
         */
        System.out.println("新的矩阵: ");
        int[][] nedges = new int[vertexs.length + 1][vertexs.length + 1];
        char[] nvertexs = new char[vertexs.length + 1];
        matrix(nvertexs, nedges);
        
        
        /**
         * 2. 虚拟节点到各个节点的距离，SPFA算法
         * ndistance的值要么是0，要么是负数
         */
        int[] ndistance = spfa(vertexs.length, nedges, nvertexs);
        // 打印
        System.out.println("----------------------------------------------------------------");
        System.out.println("虚拟节点: " + Arrays.toString(ndistance));
        System.out.println("----------------------------------------------------------------");
        
        
        /**
         * 3. 修改原始邻接矩阵: w(u, v) = w(u, v) + (h[u] - h[v]).
         */
        for (int u = 0; u < vertexs.length; u++) {// 源顶点，对应矩阵的行
            for (int v = 0; v < vertexs.length; v++) {// 目标顶点，对应矩阵的列
                if (edges[u][v] == mx) {// 原来无穷大的不变
                    continue;
                }
                edges[u][v] = edges[u][v] + (ndistance[u] - ndistance[v]);
            }
        }
        // 打印修改后的矩阵
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                print(edges[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------");
        
        
        /**
         * 4. 对每个顶点运行 Dijkstra 算法求得最短路径
         * 此时的边都为正值，但要得到正确的值，需要减去原来各边加上的权值
         */
        for (int i = 0; i < vertexs.length; i++) {
            dijkstra(i, ndistance);
        }
    }
    
    
    /**
     * SPFA算法
     * 
     * @param vp 新增的虚拟节点的位置
     */
    public static int[] spfa(final int vp, int[][] edges, char[] vertexs) {
        int[] dist = new int[vertexs.length];// 起点到各顶点的路径距离
        /**
         * 这里的初始化，必须都是无穷；
         * 否则，如果起点start的直接邻接顶点的路径放入dist之后，下面的循环不会替换值，只循环一次就完了
         */
        for (int i = 0; i < vertexs.length; i++) {
            dist[i] = mx;
        }
        
        Queue<Integer> queue = new LinkedList<>();// 存放各顶点在vertexs中的索引
        boolean[] in = new boolean[vertexs.length];
        int[] times = new int[vertexs.length];
        
        /**将起点放入队列，准备开始*/
        queue.add(vp);
        in[vp] = true;
        dist[vp] = 0;
        
        negative:
        while (! queue.isEmpty()) {/** 队列中的元素，同属于一层，与起点之间的路径条数一样 */
            int p = queue.poll();
            in[p] = false;
            
            /**
             * 遍历所有元素，确定当前节点p有效的路径
             */
            for (int i = 0; i < vertexs.length; i++) {
                if (p == i) {
                    continue;
                }
                
                if (dist[p] == Integer.MAX_VALUE || edges[p][i] == Integer.MAX_VALUE) {
                    continue;
                }
                
                /**
                 * 层层向外扩展，类似于广度优先算法
                 * 由起点0开始，显示第一层1,2,3，再是第二层4,5，再是第三层6
                 */
                if (dist[i] > dist[p] + edges[p][i]) {
                    dist[i] = dist[p] + edges[p][i];
                    
                    times[i]++;
                    /** 防止重复多余的遍历，不判断应该也可以，例如：1,2会同时把4放入队列 */
                    if (! in[i]) {
                        queue.add(i);
                        in[i] = true;
                    }
                    /**
                     * 进入队列达到n次，则表明图中存在负环，没有最短路径。
                     */
                    if (times[i] >= vertexs.length) {
                        System.out.println("负环");
                        break negative;
                    }
                }
            }
        }
        return dist;
    }
    
    
    /**
     * Dijkstra 算法
     * 
     * @param p 起点在vertexs数组中的位置
     */
    public static void dijkstra(final int p, int[] ndistance) {
        boolean[] visited = new boolean[vertexs.length];// 是否已访问，顺序与vertexs一致
        /**
         * 起点到各顶点的距离，顺序与vertexs一致；
         * 0表示起点本身，Integer.MAX_VALUE表示不可达
         */
        int[] distance = new int[vertexs.length];
        
        /**
         * 1. 初始化
         */
        for (int i = 0; i < edges[p].length; i++) {
            int edge = edges[p][i];
            if (edge == 0) {
                distance[i] = Integer.MAX_VALUE;
                continue;
            }
            distance[i] = edge;
        }
        visited[p] = true;
        distance[p] = 0;// 起点
        
        // 起点到所有节点的距离，故遍历所有节点
        for (int i = 0; i < vertexs.length; i++) {
            int nearestIndex = p;
            int nearestFar = Integer.MAX_VALUE;
            /**
             * 2. 找出当前distance中距离起点最近的节点，获取其距离和索引
             */
            for (int k = 0; k < distance.length; k++) {
                int far = distance[k];
                
                // 距离最近，且没有被访问
                if (far < nearestFar && (! visited[k])) {
                    nearestFar = far;
                    nearestIndex = k;
                }
            }
            
            /**
             * 3. 将本次找到的最短路径的顶点放入已排序数组中，并设置其已访问
             */
            if (! visited[nearestIndex]) {
                visited[nearestIndex] = true;
            }
            
            /**
             * 4. 重新整理各顶点到起点的距离
             */
            for (int j = 0; j < vertexs.length; j++) {
                if (visited[j]) {// 已访问过，不再更新其距离
                    continue;
                }
                /**
                 * 只需要更新当前新找到的nearestIndex节点的直接后继节点的距离，因为只有它们的距离会有变化；
                 */
                int edge = edges[nearestIndex][j];
                if (mx == edge || nearestFar == mx) {
                    continue;
                }
                int nextFar = distance[j];// 该顶点之前到起点的距离，不一定是Integer.MAX_VALUE
                int curFar = nearestFar + edge;/** 当前到起点的距离 */
                if (curFar < nextFar) {
                    distance[j] = curFar;// 设置其最短路径
                }
            }
        }
        
        System.out.println(vertexs[p] + ": ");
        for (int i = 0; i < distance.length; i++) {
            int d = distance[i] + ndistance[i];
            if (mx == distance[i]) {
                d = mx;// 无穷，不变
            }
            System.out.print(vertexs[i] + "(" + d + ") ");
        }
        System.out.println();
    }
    
    
    public static void main(String[] args) {
        exec();
    }
}
