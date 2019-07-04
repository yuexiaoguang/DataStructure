package com.graph.path;

/**
 * 最短路径Bellman-Ford算法
 */
public class BellmanFord {
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
    
    
    /**
     * 查找指定字符在“顶点”数组中的位置
     */
    private static int vertex(final char source) {
        for (int i = 0; i < vertexs.length; i++) {
            char c = vertexs[i];
            if (c == source) {
                return i;
            }
        }
        return -1;
    }
    
    public static void exec(final char start) {
        int poi = vertex(start);
        int[] dist = new int[vertexs.length];// 起点到各顶点的路径距离
        
        /**
         * 初始化起点到各顶点的路径距离；
         * 与起点直接相邻的节点有默认值，但不应是最短的；其它都为无穷
         */
        for (int i = 0; i < vertexs.length; i++) {
            dist[i] = edges[poi][i];
        }
        
        /**
         * 网上的算法，时间复杂度为O(VE)，即顶点数乘以边数，但E = V * V，故还是V*V*V
         */
        /**
         * k: 表示遍历N遍，起点到各顶点之间最大有n-1个边；
         * 第一遍：隔一个节点，有两条边；
         * 第二遍：在第一遍的基础上，实际隔两个节点，有三条边；
         * --但矩阵表示里面，都只分为两段路径，因为上一遍遍历扩展了起点的可达范围
         * 逐步扩展范围，有点类似于Floyd算法
         */
        for (int k = 0; k < vertexs.length; k++) {
            for (int i = 0; i < vertexs.length; i++) {
                for (int j = 0; j < vertexs.length; j++) {
                    /**
                     * i == j 则dist[j] == dist[i]且edges[i][j]为0
                     */
                    if (i == j) {
                        continue;
                    }
                    // 超出int最大范围的加法，就变成负数了
                    if (dist[i] == Integer.MAX_VALUE || edges[i][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    /**
                     * dist[j]: 依次为0-0、0-1、0-2、0-3、0-4、0-5、0-6的距离
                     * dist[i]: 依次为0-0、0-1、0-2、0-3、0-4、0-5、0-6的距离
                     * edges[i][j]: 依次遍历整个邻接矩阵: 0-1 = 0-0 + 0-1 或 0-1 = 0-2 + 2-1 等
                     */
                    if (dist[j] > dist[i] + edges[i][j]) {
                        dist[j] = dist[i] + edges[i][j];
                    }
                }
            }
        }
        
        // 校验负环
        negative:
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                if (i == j) {
                    continue;
                }
                if (dist[i] == Integer.MAX_VALUE || edges[i][j] == Integer.MAX_VALUE) {
                    continue;
                }
                /**
                 * 此时应该不存在指向起点的负值路径，如果存在，则在单个间隔节点的情况下，再次出现替换；
                 * 之所以是单个间隔节点，是因为上面的循环已经将“起点”可达的所有节点的路径长度都放入dist中了；
                 * 负环的含义：0-0为0，但如果3-0(-7) + 0-3(5) < 0 则表示负环；但如果3-0(-3) + 0-3(5) > 0则不影响
                 */
                if (dist[j] > dist[i] + edges[i][j]) {
                    System.out.println("负环");
                    break negative;
                }
            }
        }
        
        System.out.print("源点" + start + ": ");
        for (int i = 0; i < dist.length; i++) {
            System.out.print(vertexs[i] + ":(" + dist[i] + ") ");
        }
    }
    
    public static void main(String[] args) {
        exec('0');
    }
}
