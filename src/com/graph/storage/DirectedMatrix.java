package com.graph.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * 有向图的邻接矩阵
 */
class DirectedMatrix {
    
    private final char[] vertex = {'0', '1', '2', '3', '4'};// 顶点
    // 边的数组顺序 <-> 对应顶点的数组顺序
    // 行是源顶点，列是目标顶点
    private final int[][] edge = {
                                  // 0  1  2  3  4
                                    {0, 1, 0, 1, 0},// 0
                                    {0, 0, 1, 1, 0},// 1
                                    {0, 0, 0, 1, 1},// 2
                                    {0, 0, 0, 0, 0},// 3
                                    {1, 0, 0, 1, 0} // 4
                                };// 边, 不对称的矩阵
    // 三元组
    private final List<Edge> edges = new ArrayList<>();
    
    public static void main(String[] args) {
        DirectedMatrix matrix = new DirectedMatrix();
        matrix.toArray();
        matrix.print();
        System.out.println(matrix.hasEdge('1', '3'));
    }
    
    public void toArray() {
        for (int i = 0; i < edge.length; i++) {
            char rv = vertex[i];
            for (int j = 0; j < edge.length; j++) {
                char cv = vertex[j];
                int e = edge[i][j];
                if (1 == e) {
                    edges.add(new Edge(new Node(rv, i), new Node(cv, j)));
                }
            }
        }
    }
    
    
    public void print() {
        int[][] copy = new int[edge.length][edge.length];
        for (Edge e : edges) {
            copy[e.getRow().getIndex()][e.getCol().getIndex()] = 1;
        }
        for (int[] c : copy) {
            for (int e : c) {
                System.out.print(e);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    
    /**
     * 从source到target，反过来不管
     */
    public boolean hasEdge(final char source, final char target) {
        int si = -1, ti = -1;
        
        for (int i = 0; i < vertex.length; i++) {
            char c = vertex[i];
            if (c == source) {
                si = i;
            }
            if (c == target) {
                ti = i;
            }
        }
        for (Edge e : edges) {
            if (e.getRow().getIndex() == si && e.getCol().getIndex() == ti) {
                return true;
            }
        }
        return false;
    }
    
    class Node {
        private char name;// 表示的"顶点"
        private int index;// “顶点”在顶点数组中的索引位置
        
        public Node(char name, int index) {
            super();
            this.name = name;
            this.index = index;
        }
        public char getName() {
            return name;
        }
        public void setName(char name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
    }
    
    class Edge {
        private Node row;// 行
        private Node col;// 列
        // 存在这个对象，就表示有边；不存在，就表示没有
        public Edge(Node row, Node col) {
            super();
            this.row = row;
            this.col = col;
        }
        public Node getRow() {
            return row;
        }
        public void setRow(Node row) {
            this.row = row;
        }
        public Node getCol() {
            return col;
        }
        public void setCol(Node col) {
            this.col = col;
        }
    }
}
