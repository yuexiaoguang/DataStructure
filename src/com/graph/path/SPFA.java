package com.graph.path;

import java.util.LinkedList;
import java.util.Queue;

/**
 * SPFA算法是Bellman算法的改进版，它们是适用范围是相同的
 */
public class SPFA {
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
        queue.add(poi);
        in[poi] = true;
        dist[poi] = 0;
        
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
        
        System.out.print("源点" + start + ": ");
        for (int i = 0; i < dist.length; i++) {
            System.out.print(vertexs[i] + ":(" + dist[i] + ") ");
        }
    }
    
    
    public static void main(String[] args) {
        exec('0');
    }
}
