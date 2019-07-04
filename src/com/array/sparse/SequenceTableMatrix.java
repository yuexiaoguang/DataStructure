package com.array.sparse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 稀疏矩阵的顺序表
 * 
 * |0 0 1 0 0 0 0|
 * |0 2 0 0 0 0 0|
 * |3 0 0 0 0 0 0|
 * |0 0 0 5 0 0 0|
 * |0 0 0 0 6 0 0|
 * |0 0 0 0 0 7 4|
 */
public class SequenceTableMatrix {
    
    private final int[][] matrix =
                                    {
                                        {0, 0, 1, 0, 0, 0, 0},
                                        {0, 2, 0, 0, 0, 0, 0},
                                        {3, 0, 0, 0, 0, 0, 0},
                                        {0, 0, 0, 5, 0, 0, 0},
                                        {0, 0, 0, 0, 6, 0, 0},
                                        {0, 0, 0, 0, 0, 7, 4}
                                    };

    private final int rows = matrix.length, cols = matrix[0].length;
    private final List<Element> eles = new ArrayList<>();
    /**
     * 区别于三元组，保存每行第一个非零元素索引；
     * 能快速定位行的非零元素，eles中的下一个元素就很可能是该行第二个非零元素；
     */
    private final int[] index = new int[rows];
    
    {
        Arrays.fill(index, -1);// -1表示该行没有非零元素
    }
    
    public static void main(String[] args) {
        SequenceTableMatrix matrix = new SequenceTableMatrix();
        matrix.toArray();
        matrix.print();
        System.out.println("--------------------------");
        System.out.println(Arrays.toString(matrix.index));
        System.out.println("--------------------------");
        matrix.get(3, 3);
    }
    
    public void toArray() {
        for (int i = 0; i < rows; i++) {
            int p = -1;
            for (int j = 0; j < cols; j++) {
                int value = matrix[i][j];
                if (0 == value) {
                    continue;
                }
                Element ele = new Element(i, j, value);
                eles.add(ele);
                
                if (-1 == p) {
                    p = eles.lastIndexOf(ele);// 每行第一个非零元素在eles中的位置
                }
            }
            if (-1 != p) {// 说明这一行不全为零
                index[i] = p;
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
    
    
    /**
     * 获取指定行元素的快捷方法
     * 
     * @param row
     * @param col
     */
    public void get(int row, int col) {
        int index = this.index[row];
        if (-1 == index) {
            return;
        }
        Element e = null;
        index--;
        while (null == e) {
            Element t = this.eles.get(index);
            if (t.getRow() == row && t.getCol() == col) {
                e = t;
                break;
            }
            index++;
        }
        if (null == e) {
            return;
        }
        System.out.println(e.getValue());
    }
    
    /**
     * 一个元
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
