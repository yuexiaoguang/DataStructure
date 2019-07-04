package com.graph.traverse;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 无向图的邻接矩阵存储的广度优先算法
 */
public class UndirectedMatrixBFS {
    
    private final char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};// 顶点
    // 边的数组顺序 <-> 对应顶点的数组顺序
    private final int[][] edge = {
                                  // A  B  C  D  E  F  G
                                    {0, 0, 1, 1, 0, 1, 0},// A
                                    {0, 0, 1, 0, 0, 0, 0},// B
                                    {1, 1, 0, 1, 0, 0, 0},// C
                                    {1, 0, 1, 0, 0, 0, 0},// D
                                    {0, 0, 0, 0, 0, 0, 1},// E
                                    {1, 0, 0, 0, 0, 0, 1},// F
                                    {0, 0, 0, 0, 1, 1, 0} // G
                                };// 边, 对称的矩阵
    private final int[] matrix = new int[edge.length * (edge.length + 1) / 2];// 需要存储的个数
    {
        Arrays.fill(matrix, -1);
    }
    
    public static void main(String[] args) {
        UndirectedMatrixBFS bfs = new UndirectedMatrixBFS();
        bfs.toArray();
        
        bfs.queue();
    }
    
    
    /**
     * 边的压缩存储，只存储下三角；
     * 如果不包括对角线，会麻烦的多
     */
    public void toArray() {
        int p = 0;
        for (int i = 0; i < edge.length; i++) {
            for (int j = 0; j <= i; j++) {
                matrix[p] = edge[i][j];
                p++;
            }
        }
    }
    
    
    /**
     * 查找指定字符在“顶点”数组中的位置
     */
    private int vertex(final char source) {
        for (int i = 0; i < vertex.length; i++) {
            char c = vertex[i];
            if (c == source) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 将原矩阵的行和列转换为压缩存储后的数组中的位置
     */
    private int convert(final int row, final int col) {
        if (row < col) {// 上三角
            return col * (col + 1)/2 + row;
        } else {
            return row * (row + 1)/2 + col;
        }
    }
    
// -------------------------------------------队列-------------------------------------------
    
    public void queue() {
        Queue<Character> queue = new PriorityQueue<>();
        boolean[] visited = new boolean[vertex.length];
        
        /**
         * 有向图的起始节点挑选，必须可以访问到其它节点；
         * 否则走不下去了
         */
        queue.add(vertex[0]);
        visited[0] = true;
        System.out.print(vertex[0] + " -> ");
        
        while (! queue.isEmpty()) {
            char head = queue.poll();
            
            for (int j = 0; j < vertex.length; j++) {// 行
                char r = vertex[j];
                int hp = convert(vertex(head), j);
                
                if (1 == matrix[hp] && (! visited[j])) {
                    queue.add(r);
                    visited[j] = true;
                    System.out.print(r + " -> ");
                }
            }
        }
    }
}
