package com.graph.traverse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 有向图的邻接矩阵存储的广度优先算法
 */
public class DirectedMatrixBFS {
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
    
    private final int rows = edge.length, cols = edge[0].length;
    private final List<Element> eles = new ArrayList<>();
    /**
     * 每一行的第一个非零元素在eles中的索引
     */
    private final int[] rhead = new int[rows];
    /**
     * 每一列的第一个非零元素在eles中的索引
     */
    private final int[] chead = new int[cols];
    
    {
        Arrays.fill(rhead, -1);
        Arrays.fill(chead, -1);
    }
    
    public static void main(String[] args) {
        DirectedMatrixBFS bfs = new DirectedMatrixBFS();
        bfs.toArray();
        bfs.queue();
    }
    
    
    public void toArray() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = edge[i][j];
                if (0 == value) {
                    continue;
                }
                Element ele = new Element(i, j, value);
                eles.add(ele);
                
                // 刚刚放进去的元素的索引
                int p = eles.lastIndexOf(ele);
                // 1. 这一行的第一个非零元素的索引
                int rh = rhead[i];
                if (-1 == rh) {
                    rhead[i] = p;
                }
                
                // 2. 这一列的第一个非零元素的索引
                int ch = chead[j];
                if (-1 == ch) {// 这一列还没有设置第一个非零元素的索引
                    chead[j] = p;
                }
                
                // 3. 设置这一行元素的链
                Element rl = this.eles.get(rhead[i]);// 这一行最后一个非零元素
                while (rl.getRight() != null) {
                    rl = rl.getRight();
                }
                if (rl.getRow() != i || rl.getCol() != j) {// 不是自己
                    rl.setRight(ele);
                }
                
                // 4. 设置这一列元素的链
                Element cl = this.eles.get(chead[j]);// 这一列最后一个非零元素
                while (cl.getDown() != null) {
                    cl = cl.getDown();
                }
                if (cl.getRow() != i || cl.getCol() != j) {// 不是自己
                    cl.setDown(ele);
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
            if (-1 == rhead[vp]) {// 这一行都没有元素
                queue.poll();
                continue;
            }
            Element e = eles.get(rhead[vp]);
            
            while (e != null) {
                if (! visited[e.getCol()]) {
                    queue.add(vertex[e.getCol()]);
                    visited[e.getCol()] = true;
                    System.out.print(vertex[e.getCol()] + " -> ");
                }
                /**
                 * 可以用行索引的
                 */
                e = e.getRight();
            }
        }
    }
    
    
    /**
     * 十字链表的一个元
     */
    class Element {
        private int row;// 矩阵的第几行
        private int col;// 矩阵的第几列
        private int value;// 值
        
        private Element right;// 该节点右侧的节点
        private Element down;// 该节点下边的节点
        
        public Element(int row, int col, int value) {
            super();
            this.row = row;
            this.col = col;
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

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Element getRight() {
            return right;
        }

        public void setRight(Element right) {
            this.right = right;
        }

        public Element getDown() {
            return down;
        }

        public void setDown(Element down) {
            this.down = down;
        }
        
        @Override
        public String toString() {
            return "[行: " + this.getRow() + "; 列: " + this.getCol() + "; 值: " + this.getValue() +"]";
        }
    }
}
