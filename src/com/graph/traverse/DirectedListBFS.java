package com.graph.traverse;

import java.util.PriorityQueue;
import java.util.Queue;

import com.graph.traverse.UndirectedListBFS.Edge;
import com.graph.traverse.UndirectedListBFS.Node;
import com.graph.traverse.UndirectedListBFS.Vertex;

/**
 * 有向图的邻接列表存储的广度优先算法
 * *类似于无向图*
 */
public class DirectedListBFS {
    private final char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};// 顶点
    // 边的数组顺序 <-> 对应顶点的数组顺序
    private final int[][] edge = {
                                  // A  B  C  D  E  F  G
                                    {0, 1, 0, 0, 0, 0, 0},// A
                                    {0, 0, 1, 0, 1, 1, 0},// B
                                    {0, 0, 0, 0, 1, 0, 0},// C
                                    {0, 0, 1, 0, 0, 0, 0},// D
                                    {0, 1, 0, 1, 0, 0, 0},// E
                                    {0, 0, 0, 0, 0, 0, 1},// F
                                    {0, 0, 0, 0, 0, 0, 0} // G
                                };// 边, 对称的矩阵
    
    private final Vertex[] list = new Vertex[vertex.length];
    {
        for (int i = 0; i < vertex.length; i++) {
            char v = vertex[i];
            list[i] = new Vertex(v, i);// list与vertex的顺序对应
        }
    }
    
    public static void main(String[] args) {
        DirectedListBFS bfs = new DirectedListBFS();
        bfs.toArray();
        bfs.queue();
    }
    
    public void toArray() {
        for (int i = 0; i < vertex.length; i++) {
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
            
            int vp = vertex(head);
            Edge f = list[vp].getFirst();
            while (f != null) {
                if (! visited[f.getNode().getIndex()]) {
                    queue.add(f.getNode().getName());
                    visited[f.getNode().getIndex()] = true;
                    System.out.print(f.getNode().getName() + " -> ");
                }
                f = f.getNext();
            }
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
