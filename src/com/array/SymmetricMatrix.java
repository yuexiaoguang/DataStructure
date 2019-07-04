package com.array;

/**
 * 对称矩阵
 * 
 * |1 2 3|
 * |2 4 5|
 * |3 5 6|
 * 
 * 存储分配策略： 每一对对称元只分配一个存储单元,即只存储下三角（包括对角线）的元, 所需空间数为: n(n+1)/2
 * 存储分配方法： 用一维数组sa[n(n+1)/2]作为存储结构。
 * 原矩阵 = {1, 2, 4, 3, 5, 6}下三角
 * 下标 K = i * (i + 1)/2 + j ; i>=j [i 为行, j 为列]
 * ----------------------------------------------------
 * 原矩阵 = {1, 2, 3, 4, 5, 6}上三角
 * 下标 K = j * (j + 1)/2 + i ; i<j [i 为行, j 为列]
 */
public class SymmetricMatrix {
    static final int[] matrix = {1, 2, 4, 3, 5, 6};
    
    public static void main(String[] args) {
        SymmetricMatrix matrix = new SymmetricMatrix();
        matrix.printMatrix();
    }
    
    /**
     * 打印矩阵
     */
    public void printMatrix() {
        // 确定是矩阵的维数
        int d = (int) Math.sqrt(2 * matrix.length);
        
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                int p = 0;
                if (i < j) {// 按行打印，先得到上三角
                    p = j * (j + 1)/2 + i;
                } else {
                    p = i * (i + 1)/2 + j;
                }
                System.out.print(matrix[p]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
