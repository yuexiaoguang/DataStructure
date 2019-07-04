package com.graph.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 最小生成树克鲁斯卡尔(Kruskal)算法
 * 
 * 1. 将图中的边按权值从小到大的顺序依次选取
 * 2. 若选取的一条边使生成树T不形成回路 ，则把它并入生成树的边集TE中，保留作为T中的一条边
 * 3. 若选取的一条边使生成树T形成回路，则将其舍弃
 * 4. 如此进行下去，直到TE中包含n-1条边为止，此时的T即为图G的最小生成
 */
public class Kruskal {
    private final char[] vertex = {'0', '1', '2', '3', '4', '5'};
    /**
     * 0 表示无穷，对称矩阵；
     */
    private final int[][] edge = new int[][] {
        //  0   1   2   3   4   5
          { 0, 18,  0,  0,  4, 23},// 0
          {18,  0,  5,  8,  0, 12},// 1
          { 0,  5,  0, 10,  0,  0},// 2
          { 0,  8, 10,  0,  0, 15},// 3
          { 4,  0,  0,  0,  0, 25},// 4
          {23, 12,  0, 15, 25,  0},// 5
      };
      
    // 存储所有边，用于排序和挑选
    private final List<Edge> ready = new LinkedList<>();
  
    /**
     * 已连接、到达的顶点集，其最终顺序和vertex不一样
     */
    private final List<Character> reachedVertex = new ArrayList<>(vertex.length);
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
        Kruskal kruskal = new Kruskal();
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
        private char from;// 起始顶点
        private int col;// 目标顶点在顶点数组中的索引
        private char to;// 目标顶点
        private int value;// 权值
        private Edge next;
       
        public Edge(int row, char from, int col, char to, int value) {
            super();
            this.row = row;
            this.from = from;
            this.col = col;
            this.to = to;
            this.value = value;
        }
        public char getFrom() {
            return from;
        }
        public void setFrom(char from) {
            this.from = from;
        }
        public char getTo() {
            return to;
        }
        public void setTo(char to) {
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
