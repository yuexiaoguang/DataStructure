package com.graph.traverse;

import java.util.Stack;

/**
 * 有向图的邻接列表存储的深度优先算法
 * *类似于无向图的*
 */
public class DirectedListDFS {
    
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
        DirectedListDFS dfs = new DirectedListDFS();
        dfs.toArray();
        
        dfs.traverse();
        System.out.println();
        dfs.stack();
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
/*************************************递归***************************************************/
    /**
     * 从顶点数组第一列开始，查找 i 行第一个为 1 的列的位置
     */
    private int firstVertex(int i) {
        if (null == list[i].getFirst()) {
            return -1;
        }
        return list[i].getFirst().getNode().getIndex();
    }
    
    /**
     * 从w + 1列开始，查找 i 行为 1 的列的位置
     */
    private int nextVertex(int i, int w) {
        Edge e = list[i].getFirst();
        // 肯定不会是第一个
        
        while (e.getNext() != null) {
            e = e.getNext();
            if (e.getNode().getIndex() > w) {
                return e.getNode().getIndex();
            }
        }
        return -1;
    }
    
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
    
/*************************************栈***************************************************/
    public void stack() {
        boolean[] invited = new boolean[vertex.length];// 记录已访问的顶点
        Stack<Character> stack = new Stack<>();// 记录当前遍历的轨迹，以用于后退
        stack.push(vertex[0]);
        invited[0] = true;
        System.out.print(vertex[0] + " -> ");
        
        while (! stack.isEmpty()) {
            char head = stack.peek();
            
            int vp = vertex(head);
            Edge f = list[vp].getFirst();
            while (f != null) {
                if (! invited[f.getNode().getIndex()]) {
                    /**
                     * 访问它，并将其放入栈中，准备访问它下面的相邻节点
                     */
                    stack.push(f.getNode().getName());
                    invited[f.getNode().getIndex()] = true;
                    System.out.print(f.getNode().getName()+ " -> ");
                    break;// 一次只访问一个节点，然后跳到下一行
                }
                f = f.getNext();
            }
            // 找不到，就回退到上一个节点
            if (head == stack.peek()) {
                stack.pop();
            }
        }
    }
    
    
/*************************************通用***************************************************/
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
