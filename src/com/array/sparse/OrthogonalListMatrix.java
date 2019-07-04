package com.array.sparse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 稀疏矩阵的十字链表
 */
public class OrthogonalListMatrix {
    
    private final int[][] matrix =
                                            {
                                                {3, 0, 0, 5, 0, 4, 0, 7},
                                                {0, 1, 2, 0, 8, 0, 2, 0},
                                                {2, 0, 0, 2, 0, 0, 6, 0},
                                                {3, 3, 0, 5, 0, 3, 0, 0},
                                                {0, 1, 0, 0, 3, 0, 5, 2},
                                                {2, 0, 2, 0, 0, 2, 0, 0},
                                                {3, 0, 1, 5, 0, 2, 0, 7},
                                                {0, 1, 0, 0, 4, 0, 4, 0},
                                                {2, 0, 2, 0, 7, 0, 3, 0}
                                            };

    private final int rows = matrix.length, cols = matrix[0].length;
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
        OrthogonalListMatrix matrix = new OrthogonalListMatrix();
        matrix.toArray();
        matrix.print();
        System.out.println("------------------");
        System.out.println("行索引rhead: " + Arrays.toString(matrix.rhead));
        System.out.println("列索引chead: " + Arrays.toString(matrix.chead));
        Element e = matrix.eles.get(1);
        System.out.println("行: " + e.getRow() + "; 列: " + e.getCol() + "; 值: " + e.getValue() + "; 右: " + e.getRight() + "; 下: " + e.getDown());
        System.out.println();
        matrix.get(3, 5);
    }
    
    
    public void toArray() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = matrix[i][j];
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
    
    public void print() {
        int[][] copy = new int[rows][cols];
        for (Element e : eles) {
            copy[e.getRow()][e.getCol()] = e.getValue();
        }
        for (int[] c : copy) {
            for (int e : c) {
                System.out.print(e);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    
    public void get(final int row, final int col) {
        if (row > rhead.length || col > chead.length) {
            return;
        }
        Element rf = this.eles.get(rhead[row]);// 这一行的第一个非零元素
        Element cf = this.eles.get(chead[col]);// 这一列的第一个非零元素
        if (rf == cf) {// 刚好是这一个
            System.out.println(rf.getValue());
            return;
        }
        
        /**
         * 比较哪个索引离得最近
         */
        // 行索引离得比较近
        if ((row - rf.getRow()) < (col - cf.getCol())) {
            while (rf.getCol() != col) {
                rf = rf.getRight();
            }
            System.out.println(rf.getValue());
            return;
        }
        // 列索引离得比较近
        while (cf.getRow() != row) {
            cf = cf.getDown();
        }
        System.out.println(cf.getValue());
    }
    
    
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
