package com.graph.topological;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 拓扑排序
 */
public class Topological {
    // 顶点
    private static final char[] vertexs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
    // 有向边，行为起始节点 -> 列为目标节点
    private static final int[][] edges = {
           // A  B  C  D  E  F  G
            { 0, 0, 0, 0, 0, 0, 1 },// A
            { 1, 0, 0, 1, 0, 0, 0 },// B
            { 0, 0, 0, 0, 0, 1, 1 },// C
            { 0, 0, 0, 0, 1, 1, 0 },// D
            { 0, 0, 0, 0, 0, 0, 0 },// E
            { 0, 0, 0, 0, 0, 0, 0 },// F
            { 0, 0, 0, 0, 0, 0, 0 } // G
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
    
    /**
     * 拓扑排序
     * 用邻接矩阵实现，可能邻接表更合适
     */
    public static void exec() {
        Queue<Character> queue = new LinkedList<>();// 结果队列
        boolean[] visited = new boolean[vertexs.length];// 是否已入队，顺序与顶点顺序一致
        int[] input = new int[vertexs.length];// 各节点入度数量，顺序与顶点顺序一致
        
        Queue<Character> mediate = new LinkedList<>();// 中间结果队列，当前的起始节点
        
        /**
         * 1. 统计顶点的入度数量
         */
        for (int i = 0; i < edges.length; i++) {
            char source = vertexs[i];// 起始节点
            int[] targets = edges[i];
            for (int j = 0; j < targets.length; j++) {
                char target = vertexs[j];// 目标节点
                int edge = targets[j];
                if (edge == 0) {
                    continue;// 没有边
                }
                System.out.println("源: " + source + " --> 目标: " + target);
                input[j]++;// 目标节点的入度加 1
            }
        }
        
        /**
         * 2. 找出第一组没有前驱的节点
         */
        for (int i = 0; i < input.length; i++) {
            char vertex = vertexs[i];
            int num = input[i];
            if (0 == num) {
                queue.add(vertex);
                mediate.add(vertex);
                visited[i] = true;
            }
        }
        if (mediate.isEmpty()) {
            System.out.println("未找到起始节点，该图无效");
            return;
        }
        
        /**
         * 3. 遍历mediate的结果，查找下一级直接节点
         */
        while (! mediate.isEmpty()) {
            char vertex = mediate.poll();
            
            // 节点的位置
            int poi = vertex(vertex);
            // i为后继节点在vertexs中的位置
            for (int i = 0; i < edges[poi].length; i++) {
                char next = vertexs[i];
                int edge = edges[poi][i];
                if (edge == 0) {
                    continue;
                }
                /**
                 * 4. 当前vertex已经入队，其下一级节点的入度减一
                 */
                input[i]--;
                
                /**
                 * 5. 保证当前节点未被访问过、入度为零
                 */
                if ((! visited[i]) && input[i] == 0) {
                    // 将下一级节点放入队列
                    queue.add(next);
                    mediate.add(next);
                    visited[i] = true;
                }
            }
        }
        
        for (Character vertex : queue) {
            System.out.print(vertex + " -> ");
        }
    }
    
    public static void main(String[] args) {
        exec();
    }
}
