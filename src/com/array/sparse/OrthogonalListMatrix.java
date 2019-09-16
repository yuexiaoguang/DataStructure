package com.array.sparse;

import java.util.Arrays;

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
    
    /**
     * 每一行的第一个非零元素在eles中的索引
     */
    private final Element[] rhead = new Element[rows];
    /**
     * 每一列的第一个非零元素在eles中的索引
     */
    private final Element[] chead = new Element[cols];
    
    
    public static void main(String[] args) {
        OrthogonalListMatrix matrix = new OrthogonalListMatrix();
        matrix.toArray();
        matrix.print();
        System.out.println("------------------");
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
                
                // 1. 这一行的第一个非零元素
                Element rh = rhead[i];
                if (null == rh) {
                    rhead[i] = ele;
                }
                
                // 2. 这一列的第一个非零元素
                Element ch = chead[j];
                if (null == ch) {// 这一列还没有设置第一个非零元素的索引
                    chead[j] = ele;
                }
                
                // 3. 设置这一行元素的链
                Element rl = rhead[i];// 这一行最后一个非零元素
                while (rl.getRight() != null) {
                    rl = rl.getRight();
                }
                if (rl.getRow() != i || rl.getCol() != j) {// 不是自己
                    rl.setRight(ele);
                }
                
                // 4. 设置这一列元素的链
                Element cl = chead[j];// 这一列最后一个非零元素
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
        // 按行遍历
        for (int i = 0; i < rhead.length; i++) {
            Element e = rhead[i];// 这一行的第一个非零元
            for (int j = 0; j < chead.length; j++) {
                if (null != e && i == e.getRow() && j == e.getCol()) {// 非零元
                    System.out.print(e.getValue());
                    System.out.print(" ");
                    
                    e = e.getRight();
                    continue;
                }
                // 其它都打印 0
                System.out.print(0);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    
    public void get(final int row, final int col) {
        if (row > rhead.length || col > chead.length) {
            return;
        }
        Element rf = rhead[row];// 这一行的第一个非零元素
        Element cf = chead[col];// 这一列的第一个非零元素
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
