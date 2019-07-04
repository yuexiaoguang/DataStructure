package com.graph.storage;

import java.util.Arrays;

/**
 * 无向图的邻接矩阵
 */
class UndirectedMatrix {
    
    private final char[] vertex = {'0', '1', '2', '3', '4'};// 顶点
    // 边的数组顺序 <-> 对应顶点的数组顺序
    private final int[][] edge = {
                                  // 0  1  2  3  4
                                    {0, 1, 0, 1, 1},// 0
                                    {1, 0, 1, 1, 0},// 1
                                    {0, 1, 0, 1, 1},// 2
                                    {1, 1, 1, 0, 1},// 3
                                    {1, 0, 1, 1, 0} // 4
                                };// 边, 对称的矩阵
    
    private final int[] matrix = new int[edge.length * (edge.length + 1) / 2];// 需要存储的个数
    
    {
        Arrays.fill(matrix, -1);
    }
    
    public static void main(String[] args) {
        UndirectedMatrix matrix = new UndirectedMatrix();
        matrix.toArray();
        System.out.println(Arrays.toString(matrix.matrix));
        matrix.print();
        System.out.println("----------------------------------");
        System.out.println(matrix.hasEdge('3', '2'));
        System.out.println("----------------------------------");
        matrix.printEdge('1');
    }
    
    /**
     * 边的压缩存储，只存储下三角；
     * 如果不包括对角线，会麻烦的多
     */
    public void toArray() {
        int p = 0;
        for (int i = 0; i < edge.length; i++) {
            for (int j = 0; j <= i; j++) {
                matrix[p] = edge[i][j];
                p++;
            }
        }
    }
    
    
    public void print() {
        // 确定是矩阵的维数
        int d = (int) Math.sqrt(2 * matrix.length);// + 1 由于矩阵对角线未保存
        
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
    
    
    /**
     * 两个“顶点”之间是否存在边
     */
    public boolean hasEdge(char source, char target) {
        int si = -1, ti = -1;
        
        for (int i = 0; i < vertex.length; i++) {
            char c = vertex[i];
            if (c == source) {
                si = i;
            }
            if (c == target) {
                ti = i;
            }
        }
        if (si < ti) {// 将定位在上三角的数据，变换到下三角
            int t = si;
            si = ti;
            ti = t;
        }
        int p = si * (si + 1)/2 + ti;
        return 1 == matrix[p] ? true : false;
    }
    
    
    /**
     * 打印指定“顶点”与其它“顶点”之间的边
     */
    public void printEdge(char source) {
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
        
        /**
         * 如果si为行
         */
        for (int i = 0; i < vertex.length; i++) {
            char t = vertex[i];
            /**
             * 这里用临时变量替换，防止修改了实际的值
             */
            int ti = i;
            int ai = si;
            
            if (ai < i) {// 将定位在上三角的数据，变换到下三角
                int te = ai;
                ai = ti;
                ti = te;
            }
            int p = ai * (ai + 1)/2 + ti;
            System.out.println(source + "<-->" + t + " : " + (1 == matrix[p] ? true : false));
        }
    }
}
