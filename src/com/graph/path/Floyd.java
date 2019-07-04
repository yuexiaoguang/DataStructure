package com.graph.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Floyd算法
 */
public class Floyd {
    private static final int mx = Integer.MAX_VALUE;
    // 顶点
    private static final char[] vertexs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
    // 有向边，行为起始节点 -> 列为目标节点
    private static final int[][] edges = {
           //  A   B   C   D   E   F   G
            {  0, 12, mx, mx, mx, 16, 14 },// A
            { 12,  0, 10, mx, mx,  7, mx },// B
            { mx, 10,  0,  3,  5,  6, mx },// C
            { mx, mx,  3,  0,  4, mx, mx },// D
            { mx, mx,  5,  4,  0,  2,  8 },// E
            { 16,  7,  6, mx,  2,  0,  9 },// F
            { 14, mx, mx, mx,  8,  9,  0 } // G
    };
    
    
    public static void exec() {
        int[][] dist = new int[vertexs.length][vertexs.length];
        /**
         * 初始化，复制原始矩阵
         */
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                dist[i][j] = edges[i][j];
            }
        }

        /**
         * 计算最短路径
         */
        
        /**
         * 1. 选中某个节点作为中间节点，以k表示
         */
        for (int k = 0; k < vertexs.length; k++) {
            /**
             * 2. 遍历所有节点; 以k选中的节点为中间节点，尝试打通一些不直接相邻的节点，并更新其路径长度
             * 由k的一个相邻的节点，经过k到另一个k相邻的节点的路径将不再是Integer.MAX_VALUE；
             * 由示例图，A和D之间至少经过两个节点，但是到选取C作为中间节点开始遍历时，A和C之间已经有路径相连，即有路径长度，
             * 虽然图中A和C不直接相邻，但是在“矩阵”中，A属于C的“直接相邻”节点
             */
            for (int i = 0; i < vertexs.length; i++) {
                for (int j = 0; j < vertexs.length; j++) {
                    
                    /**
                     * dist[i][k]遍历节点k在矩阵中的列；dist[k][j]遍历节点k在矩阵中的行
                     * 例如：k表示B，经过B，A和C可相连，则AC的路径为 BA + BC
                     */
                    int tmp = (dist[i][k]==Integer.MAX_VALUE || dist[k][j]==Integer.MAX_VALUE) ? Integer.MAX_VALUE : (dist[i][k] + dist[k][j]);
                    if (dist[i][j] > tmp) {
                        dist[i][j] = tmp;
                        
//                        System.out.println(vertexs[i] +" -> "+ vertexs[k] +" -> "+ vertexs[j]);
                    }
                }
            }
        }

        /**
         * 打印floyd最短路径的结果
         * 不论遍历节点的顺序如何，最后的结果应该是一样的
         */
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++)
                System.out.printf("%2d  ", dist[i][j]);
            System.out.printf("\n");
        }
    }
    
    public static void main(String[] args) {
        exec();
    }
}
