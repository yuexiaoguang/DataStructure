package com.graph.storage;

/**
 * 有向图的邻接列表
 */
class DirectedList {
    
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
    private final Vertex[] list = new Vertex[vertex.length];
    {
        for (int i = 0; i < vertex.length; i++) {
            char v = vertex[i];
            list[i] = new Vertex(v, i);// list与vertex的顺序对应
        }
    }
    
    public static void main(String[] args) {
        DirectedList obj = new DirectedList();
        obj.toArray();
        obj.print();
        System.out.println("----------------------------------");
        System.out.println(obj.hasEdge('3', '0'));
        System.out.println("----------------------------------");
        obj.printEdge('2');
    }
    
    public void toArray() {
        for (int i = 0; i < edge.length; i++) {
            for (int j = 0; j < edge.length; j++) {
                if (i == j) {
                    continue;
                }
                final Vertex t = list[j];
                if (1 == edge[i][j]) {
                    Vertex c = list[i];
                    Edge f = c.getFirst();
                    if (null == f) {
                        c.setFirst(new Edge(new Node(t.getName(), t.getIndex())));
                        continue;
                    }
                    
                    // 已经有第一个节点了
                    while (f.getNext() != null) {
                        f = f.getNext();
                    }
                    f.setNext(new Edge(new Node(t.getName(), t.getIndex())));
                }
            }
        }
    }
    
    
    public void print() {
        for (Vertex v : list) {
            System.out.print(v.getName());
            System.out.print("-->");
            Edge e = v.getFirst();
            while (e != null) {
                System.out.print(e.getNode().getName());
                System.out.print("-->");
                e = e.getNext();
            }
            System.out.println();
        }
    }
    
    public boolean hasEdge(final char source, final char target) {
        int si = -1;
        
        for (int i = 0; i < vertex.length; i++) {
            char c = vertex[i];
            if (c == source) {
                si = i;
            }
        }
        
        final Vertex sv = list[si];
        
        Edge e = sv.getFirst();
        if (null == e) {
            return false;
        }
        if (target == e.getNode().getName()) {
            return true;
        }
        while (null != e.getNext()) {
            e = e.getNext();
            if (target == e.getNode().getName()) {
                return true;
            }
        }
        return false;
    }
    
    
    public void printEdge(final char source) {
        int si = -1;
        for (int i = 0; i < vertex.length; i++) {
            char c = vertex[i];
            if (c == source) {
                si = i;
            }
        }
        if (-1 == si) {
            return;
        }
        final Vertex sv = list[si];
        Edge e = sv.getFirst();
        if (null == e) {
            return;
        }
        System.out.print(source);
        System.out.print("-->");
        while (e != null) {
            System.out.print(e.getNode().getName());
            System.out.print("-->");
            e = e.getNext();
        }
    }
    
    class Vertex {
        private char name;// 顶点名称
        private int index;// 在顶点数组中的索引
        private Edge first;// 第一个边
        
        public Vertex(char name, int index) {
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
        public Edge getFirst() {
            return first;
        }
        public void setFirst(Edge first) {
            this.first = first;
        }
    }
    
    /**
     * 区别于Vertex，防止循环引用
     */
    class Node {
        private char name;// 顶点名称
        private int index;// 在顶点数组中的索引
        
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
        private Node node;// 表示不同于源顶点的另一个顶点
        private Edge next;// 下一个与源顶点连着的边
        
        public Edge(Node node) {
            super();
            this.setNode(node);
        }
        public Edge getNext() {
            return next;
        }
        public void setNext(Edge next) {
            this.next = next;
        }
        public Node getNode() {
            return node;
        }
        public void setNode(Node node) {
            this.node = node;
        }
    }
}
