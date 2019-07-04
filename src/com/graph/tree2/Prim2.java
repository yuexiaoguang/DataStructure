package com.graph.tree2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Prim2 {
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
     // 存储矩阵的邻接列表
    private final Vertex[] list = new Vertex[vertex.length];
    {
        for (int i = 0; i < vertex.length; i++) {
            String v = vertex[i];
            list[i] = new Vertex(v, i);// list与vertex的顺序对应
        }
    }
    
    /**
     * 已连接、到达的顶点集，其最终顺序和vertex不一样
     */
    private final List<String> reachedVertex = new ArrayList<>(vertex.length);
    /**
     * 已连接、到达的顶点之间连接的边
     */
    private final List<Edge> reachedEdge = new ArrayList<>(vertex.length - 1);
    /**
     * 已连接的顶点，防止形成环;
     * 其顺序与vertex的顺序一致
     */
    private final boolean[] visited = new boolean[vertex.length];
    
    
    public static void main(String[] args) {
        Prim2 prim = new Prim2();
        prim.toArray();
        prim.prim();
        
        System.out.println(prim.reachedVertex.stream().map(v -> v.toString()).collect(Collectors.joining(",")));
        System.out.println(prim.reachedVertex.size());
        System.out.println(prim.reachedEdge.stream().map(e -> e.toString()).collect(Collectors.joining(",")));
        System.out.println(prim.reachedEdge.size());
    }
    
    
    public void prim() {
        /**
         * 1. 挑选第一个点作为起点，开始查找
         */
        String first = vertex[0];// 挑选第一个顶点作为起始
        reachedVertex.add(first);
        visited[0] = true;
        
        while (reachedEdge.size() < vertex.length - 1) {
            List<Edge> ready = new ArrayList<>();// 所有备选的边
            /**
             * 2. 查找与已连接的顶点中相邻的边
             */
            for (int i = 0; i < reachedVertex.size(); i++) {// 遍历cv中的所有节点，找到“已访问顶点”相邻的所有边
                String v = reachedVertex.get(i);
                if (null == v) {
                    continue;
                }
                int vp = position(v);
                Vertex vt = list[vp];// 准备获取当前行的边
                Edge edge = vt.getFirst();
                while (null != edge) {
                    if (! ready.contains(edge)) {// 防止过多的重复，不过还会有重复
                        // 将所有边放入备选集合
                        ready.add(edge);
                    }
                    edge = edge.getNext();
                }
            }
            /**
             * 3. 按边的权重值排序，保证最小权重的边在最上边
             */
            ready.sort(new Comparator<Edge>() {
                @Override
                public int compare(Edge o1, Edge o2) {
                    return o1.getValue() - o2.getValue();
                }
            });
            
            /**
             * 4. 排除将会造成环的边，即边的两个顶点已经连接，不能重复连接
             */
            // 排除不符合条件的，即已经到达的顶点
            Edge nearest = null;
            for (Edge edge : ready) {
                if (visited[edge.getCol()]) {// 要连接的顶点已到达，不能再选择这个边，会形成环
                    /**
                     * TODO 删除已经无效的边，防止重复循环;
                     * 但是可能很麻烦
                     */
                    continue;
                }
                /**
                 * 5. 挑选有效的，权重最小的边；并将对应的顶点设置为已连接
                 */
                nearest = edge;
                visited[edge.getCol()] = true;// 设置该点已到达
                break;// 只需找到第一个有效的最短的边即可
            }
            
            /**
             * 6. 将新连接的顶点加入已连接顶点集合，将选择的边加入已连接的边集合
             */
            // 设置数据
            reachedEdge.add(nearest);
            reachedVertex.add(nearest.getTo());
        }
    }
    
    
    /**
     * 查找字符在vertex顶点数组中的位置
     */
    private int position(final String c) {
        for (int i = 0; i < vertex.length; i++) {
            if (c.equals(vertex[i])) {
                return i;
            }
        }
        return -1;
    }
    
    
    
    // --------------------------------------用于存储矩阵的结构--------------------------------------------------------------------
    /**
     * 采用邻接列表存储，方便存储和比较，减少不必要的对象创建和重复比较
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
                Vertex c = list[i];
                Edge f = c.getFirst();
                if (null == f) {
                    c.setFirst(new Edge(i, vertex[i], j, vertex[j], edge[i][j]));
                    continue;
                }
                
                // 已经有第一个节点了
                while (f.getNext() != null) {
                    f = f.getNext();
                }
                f.setNext(new Edge(i, vertex[i], j, vertex[j], edge[i][j]));
            }
        }
    }
    
    
    class Vertex {
        private String name;// 顶点名称
        private int index;// 在顶点数组中的索引
        private Edge first;// 第一个边
        
        public Vertex(String name, int index) {
            super();
            this.name = name;
            this.index = index;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public Edge getFirst() {
            return first;
        }
        public void setFirst(Edge first) {
            this.first = first;
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
