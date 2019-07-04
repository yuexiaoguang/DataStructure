package com.graph.traverse;

import java.util.Arrays;
import java.util.Stack;

/**
 * 无向图的邻接矩阵存储的深度优先算法
 */
public class UndirectedMatrixDFS {
    
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
    /**
     * 下三角 K = i * (i + 1)/2 + j ; i>=j [i 为行, j 为列]
     * 上三角 K = j * (j + 1)/2 + i ; i<j [i 为行, j 为列]
     */
    private final int[] matrix = new int[edge.length * (edge.length + 1) / 2];// 需要存储的个数
    {
        Arrays.fill(matrix, -1);
    }
    
    public static void main(String[] args) {
        UndirectedMatrixDFS dfs = new UndirectedMatrixDFS();
        dfs.toArray();
        
        dfs.traverse();
        System.out.println();
        dfs.stack();
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
    
    
    /**
     * 从顶点数组第一列开始，查找 i 行第一个为 1 的列的位置
     */
    private int firstVertex(int i) {
        for (int j = 0; j < vertex.length; j++) {
            int p = convert(i, j);
            if (1 == matrix[p]) {
                return j;
            }
        }
        return -1;
    }
    
    /**
     * 从w + 1列开始，查找 i 行为 1 的列的位置
     */
    private int nextVertex(int i, int w) {
        for (int j = w + 1; j < vertex.length; j++) {
            int p = convert(i, j);
            if (1 == matrix[p]) {
                return j;
            }
        }
        return -1;
    }
    
//------------------------------------------递归----------------------------------------------
    /**
     * 递归
     * 递归也是利用了方法栈，类似于栈的实现方式
     */
    public void traverse() {
        boolean[] visited = new boolean[vertex.length];
        for (int i = 0; i < vertex.length; i++) {
            if (!visited[i])
                DFS(i, visited);
        }
    }
    
    /*
     * 深度优先搜索遍历图的递归实现
     */
    private void DFS(int i, boolean[] visited) {

        visited[i] = true;
        System.out.print(vertex[i] + " -> ");
        
        // 遍历该顶点的所有邻接顶点。若是没有访问过，那么继续往下走
        for (int w = firstVertex(i); w >= 0; w = nextVertex(i, w)) {
            if (!visited[w])
                DFS(w, visited);
        }
    }

//------------------------------------------栈-------------------------------------------------
    
    public void stack() {
        boolean[] invited = new boolean[vertex.length];// 记录已访问的顶点
        Stack<Character> stack = new Stack<>();// 记录当前遍历的轨迹，以用于后退
        stack.push(vertex[0]);
        invited[0] = true;
        System.out.print(vertex[0] + " -> ");
        
        while (! stack.isEmpty()) {
            char head = stack.peek();
            
            // 遍历其相邻的节点
            for (int j = 0; j < vertex.length; j++) {
                char t = vertex[j];
                int hp = convert(vertex(head), j);
                // 第一个相邻的, 且没有被访问过的节点
                if (1 == matrix[hp] && (! invited[j])) {
                    /**
                     * 访问它，并将其放入栈中，准备访问它下面的相邻节点
                     */
                    stack.push(t);
                    invited[j] = true;
                    System.out.print(t + " -> ");
                    break;// 结束当前循环，进入下一个节点
                }
            }
            // 找不到，就回退到上一个节点
            if (head == stack.peek()) {
                stack.pop();
            }
        }
    }
}
