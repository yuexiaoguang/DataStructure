package com.graph.path;

import java.util.ArrayList;
import java.util.List;

/**
 * Dijkstra最短路径
 */
public class Dijkstra {
    // 顶点
    private static final char[] vertexs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
    // 有向边，行为起始节点 -> 列为目标节点
    private static final int[][] edges = {
           //  A   B   C   D   E   F   G
            {  0, 12,  0,  0,  0, 16, 14 },// A
            { 12,  0, 10,  0,  0,  7,  0 },// B
            {  0, 10,  0,  3,  5,  6,  0 },// C
            {  0,  0,  3,  0,  4,  0,  0 },// D
            {  0,  0,  5,  4,  0,  2,  8 },// E
            { 16,  7,  6,  0,  2,  0,  9 },// F
            { 14,  0,  0,  0,  8,  9,  0 } // G
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
        boolean[] visited = new boolean[vertexs.length];// 是否已访问，顺序与vertexs一致
        /**
         * 起点到各顶点的距离，顺序与vertexs一致；
         * 0表示起点本身，Integer.MAX_VALUE表示不可达
         */
        int[] distance = new int[vertexs.length];
        List<Character> sorted = new ArrayList<>(vertexs.length);// 已得到的最短路径节点
        
        /**
         * 1. 初始化
         */
        int startPoi = vertex(start);
        for (int i = 0; i < edges[startPoi].length; i++) {
            int edge = edges[startPoi][i];
            if (edge == 0) {
                distance[i] = Integer.MAX_VALUE;
                continue;
            }
            distance[i] = edge;
        }
        visited[startPoi] = true;
        distance[startPoi] = 0;// 起点
        sorted.add(start);
        
        // 起点到所有节点的距离，故遍历所有节点
        for (int i = 0; i < vertexs.length; i++) {
            int nearestIndex = startPoi;
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
                sorted.add(vertexs[nearestIndex]);
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
                if (0 == edge) {
                    continue;
                }
                int nextFar = distance[j];// 该顶点之前到起点的距离，不一定是Integer.MAX_VALUE
                int curFar = nearestFar + edge;/** 当前到起点的距离 */
                if (curFar < nextFar) {
                    distance[j] = curFar;// 设置其最短路径
                }
            }
        }

        for (Character vertex : sorted) {
            System.out.print(vertex + "( " + distance[vertex(vertex)] + " ); ");
        }
    }
    
    public static void main(String[] args) {
        exec('D');
    }
}
