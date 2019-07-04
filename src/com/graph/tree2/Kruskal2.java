package com.graph.tree2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Kruskal2 {
    private final String[] vertex = {"v0", "v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8"};
    /**
     * 0 表示无穷，对称矩阵；
     * 不再用压缩存储的方式，进行转换，直接用这个矩阵计算；
     */
    private final int[][] edge = new int[][] {
                                              // v0  v1  v2  v3  v4  v5  v6  v7  v8
                                                { 0, 10,  0,  0,  0, 11,  0,  0,  0},// v0
                                                {10,  0, 18,  0,  0,  0, 16,  0, 12},// v1
                                                { 0, 18,  0, 22,  0,  0,  0,  0,  8},// v2
                                                { 0,  0, 22,  0, 20,  0, 24, 16, 21},// v3
                                                { 0,  0,  0, 20,  0, 26,  0,  7,  0},// v4
                                                {11,  0,  0,  0, 26,  0, 17,  0,  0},// v5
                                                { 0, 16,  0, 24,  0, 17,  0, 19,  0},// v6
                                                { 0,  0,  0, 16,  7,  0, 19,  0,  0},// v7
                                                { 0, 12,  8, 21,  0,  0,  0,  0,  0} // v8
                                            };
    // 存储所有边，用于排序和挑选
    private final List<Edge> ready = new LinkedList<>();
      
        /**
     * 已连接、到达的顶点集，其最终顺序和vertex不一样
     */
    private final List<String> reachedVertex = new ArrayList<>(vertex.length);
    /**
     * 已连接、到达的顶点之间连接的边
     */
    private final List<Edge> reachedEdge = new ArrayList<>(vertex.length - 1);
    /**
     * 各个顶点的初始分组号，初始时各不相同，最终所有值都会相同；
     * 用于防止环，是一个两两合并的过程，即森林合并为一个树的过程；
     * 顺序与vertex顶点数组一致
     */
    private final int[] group = new int[vertex.length];
    {
        // 值是多少不重要，只要能区分不同分组
        for (int i = 0; i < vertex.length; i++) {
            group[i] = i;
        }
    }
    
    
    public static void main(String[] args) {
        Kruskal2 kruskal = new Kruskal2();
        kruskal.toArray();
        kruskal.kruskal();
        
        System.out.println(kruskal.reachedVertex.stream().map(v -> v.toString()).collect(Collectors.joining(",")));
        System.out.println(kruskal.reachedVertex.size());
        System.out.println(kruskal.reachedEdge.stream().map(e -> e.toString()).collect(Collectors.joining(",")));
        System.out.println(kruskal.reachedEdge.size());
    }
    
    public void kruskal() {
        /**
         * 1. 对所有边进行排序，权重小的在上面
         */
        // 边按权重排序
        ready.sort(new Comparator<Edge>() {
              @Override
              public int compare(Edge o1, Edge o2) {
                  return o1.getValue() - o2.getValue();
              }
        });
        
        /**
         * 2. 从小到大迭代所有边，开始挑选
         */
        // 使用迭代器，因为迭代器可以删除；也可以变成for
        Iterator<Edge> edges = ready.iterator();
        while (edges.hasNext()) {
            Edge edge = (Edge) edges.next();
            
            int oe = edge.getRow();// 边的一端
            int ae = edge.getCol();// 边的另一端
            
            int goe = group[oe];// 分组中，边的一端的分组号
            int gae = group[ae];// 分组中，边的另一端的分组号
            
            /**
             * 3. 同处一个分组(树)的节点、边不能再合并
             */
            if (goe == gae) {// 如果两个分组号一致，表示它们已经连接在一起，抛弃此边
                continue;
            }
            
            /**
             * 4. 保存挑选出来的顶点、边
             */
            if (! reachedVertex.contains(edge.getFrom())) {
                reachedVertex.add(edge.getFrom());
            }
            if (! reachedVertex.contains(edge.getTo())) {
                reachedVertex.add(edge.getTo());
            }
            reachedEdge.add(edge);
            
            /**
             * 5. 合并分组，将分组号设为一致
             */
            // 由于group分组中可能有多个顶点已经合并在一起，故需要循环，全部修改
            for (int i = 0; i < group.length; i++) {
                int g = group[i];
                if (g == gae) {
                    group[i] = goe;// 全部改成起始节点的分组号
                }
            }
            
            // 如果边的数量到达极限，结束多余的循环
            if (reachedEdge.size() == vertex.length - 1) {
                break;
            }
        }
    }
    
    
    // --------------------------------------用于存储矩阵的结构--------------------------------------------------------------------
    /**
     * 获取所有顶点之间的边
     */
    public void toArray() {
        for (int i = 0; i < vertex.length; i++) {// 行
            for (int j = 0; j < edge.length; j++) {// 列
                if (i == j) {
                    continue;
                }
                if (0 == edge[i][j]) {
                    continue;
                }
                Edge e = new Edge(i, vertex[i], j, vertex[j], edge[i][j]);
                ready.add(e);
            }
        }
    }
    
    /**
     * 顶点与顶点的边
     */
    class Edge {
        private int row;// 起始顶点在顶点数组中的索引
        private String from;// 起始顶点
        private int col;// 目标顶点在顶点数组中的索引
        private String to;// 目标顶点
        private int value;// 权值
        private Edge next;
       
        public Edge(int row, String from, int col, String to, int value) {
            super();
            this.row = row;
            this.from = from;
            this.col = col;
            this.to = to;
            this.value = value;
        }
        public String getFrom() {
            return from;
        }
        public void setFrom(String from) {
            this.from = from;
        }
        public String getTo() {
            return to;
        }
        public void setTo(String to) {
            this.to = to;
        }
        public int getValue() {
            return value;
        }
        public void setValue(int value) {
            this.value = value;
        }
        
        public int getRow() {
            return row;
        }
        public void setRow(int row) {
            this.row = row;
        }
        public int getCol() {
            return col;
        }
        public void setCol(int col) {
            this.col = col;
        }
        public Edge getNext() {
            return next;
        }
        public void setNext(Edge next) {
            this.next = next;
        }
        @Override
        public String toString() {
            return "[" + getFrom() + "--" + getValue() + "-->" + getTo() + "]";
        }
    }
}
