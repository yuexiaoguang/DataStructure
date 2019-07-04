package com.array;

/**
 * 三角矩阵
 * 
 * |1 0 0|
 * |2 3 0|
 * |4 5 6|
 * --------------
 * |1 2 4|
 * |0 3 5|
 * |0 0 6|
 * 
 * 只需存储除0之外的元素，个数为：n(n+1)/2
 * 存储分配方法： 用一维数组sa[n(n+1)/2]作为存储结构。
 * 下三角矩阵 = {1, 2, 4, 3, 5, 6}
 * 位置 K = i * (i + 1)/2 + j ; i>=j [i 为行, j 为列]
 * ----------------------------------------------------
 * 上三角矩阵 = {1, 2, 3, 4, 5, 6}
 * 位置 K = i * (2n - i + 1)/2 + j - i ; i<=j [i 为行, j 为列, n 为矩阵阶数]
 */
public class TriangularMatrix {
    // 两者互为转置矩阵
    static final int[] umatrix = {1, 2, 4, 3, 5, 6};
    static final int[] lmatrix = {1, 2, 3, 4, 5, 6};
    
    public static void main(String[] args) {
        TriangularMatrix matrix = new TriangularMatrix();
        matrix.printUpperMatrix();
        System.out.println("---------------------");
        matrix.printLowerMatrix();
    }
    
    /**
     * 上三角矩阵
     */
    public void printUpperMatrix() {
        // 确定是矩阵的维数
        int d = (int) Math.sqrt(2 * umatrix.length);
        
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (i > j) {// 按行打印，先得到上三角
                    System.out.print(0);
                    System.out.print(" ");
                } else {
                    int p = (i * (2 * d - i + 1)/2 + j - i);
                    System.out.print(umatrix[p]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * 下三角矩阵
     */
    public void printLowerMatrix() {
        // 确定是矩阵的维数
        int d = (int) Math.sqrt(2 * lmatrix.length);
        
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (i < j) {// 按行打印，先得到上三角
                    System.out.print(0);
                    System.out.print(" ");
                } else {
                    int p = i * (i + 1)/2 + j;
                    System.out.print(lmatrix[p]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
