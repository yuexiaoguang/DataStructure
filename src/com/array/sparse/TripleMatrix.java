package com.array.sparse;

import java.util.ArrayList;
import java.util.List;

/**
 * 稀疏矩阵的三元组
 * 
 * |0 0 1 0 0 0 0|
 * |0 2 0 0 0 0 0|
 * |3 0 0 0 0 0 0|
 * |0 0 0 5 0 0 0|
 * |0 0 0 0 6 0 0|
 * |0 0 0 0 0 7 4|
 */
public class TripleMatrix {
    
    private static final int[][] matrix =
                                            {
                                                {0, 0, 1, 0, 0, 0, 0},
                                                {0, 2, 0, 0, 0, 0, 0},
                                                {3, 0, 0, 0, 0, 0, 0},
                                                {0, 0, 0, 5, 0, 0, 0},
                                                {0, 0, 0, 0, 6, 0, 0},
                                                {0, 0, 0, 0, 0, 7, 4}
                                            };
    
    private static final int rows = matrix.length, cols = matrix[0].length;
    private static final List<Element> eles = new ArrayList<>();
    
    public static void main(String[] args) {
        TripleMatrix matrix = new TripleMatrix();
        matrix.toArray();
        matrix.print();
    }
    
    /**
     * 保存
     */
    public void toArray() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = matrix[i][j];
                if (0 == value) {
                    continue;
                }
                Element ele = new Element(i, j, value);
                eles.add(ele);
            }
        }
    }
    
    
    /**
     * 打印
     */
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
    
    /**
     * 三元组的一个元
     */
    class Element {
        private int row;// 矩阵的第几行
        private int col;// 矩阵的第几列
        private int value;// 值
        
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
    }
}
