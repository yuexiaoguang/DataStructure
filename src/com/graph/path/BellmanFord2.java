package com.graph.path;

/**
 * 使用Floyd算法、Dijkstra算法的图
 */
public class BellmanFord2 {
    
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
    
    /**
     * 查找指定字符在“顶点”数组中的位置
     */
    private static int vertex(final char source) {
        for (int i = 0; i < vertexs.length; i++) {
            char c = vertexs[i];
            if (c == source) {
                return i;
            }
        }
        return -1;
    }
    
    public static void exec(final char start) {
        int poi = vertex(start);
        int[] dist = new int[vertexs.length];// 起点到各顶点的路径距离
        
        /**
         * 初始化起点到各顶点的路径距离；
         * 与起点直接相邻的节点有默认值，但不一定是最短的；其它都为无穷
         */
        for (int i = 0; i < vertexs.length; i++) {
            dist[i] = edges[poi][i]; //对dist[ ]初始化
        }
        
        /**
         * 网上的算法，时间复杂度为O(VE)，即顶点数乘以边数，但E = V * V，故还是V*V*V
         */
        /**
         * k: 表示遍历N遍，起点到各顶点之间最大有n-1个边；
         * 例如：(i=2[DC]、j=1[CB]) DB不通，但DC + CB是通的，则DB也将贯通；
         * 下一次DB贯通，就可以找DE = DB + BE等路径
         * -----------------------------------------------------------------
         * 第一遍：DF = DC + CF 或 DF = DE + EF / DB = DC + CB / DG = DE + EG
         * 第二遍：DA = DF + FA 或 DA = DB + BA 或 DA = DG + GA
         * 以此类推，逐步扩展范围，有点类似于Floyd算法
         */
        for (int k = 0; k < vertexs.length; k++) {
            for (int i = 0; i < vertexs.length; i++) {
                for (int j = 0; j < vertexs.length; j++) {
                    /**
                     * i == j 则dist[j] == dist[i]且edges[i][j]为0
                     */
                    if (i == j) {
                        continue;
                    }
                    // 超出int最大范围的加法，就变成负数了
                    if (dist[i] == Integer.MAX_VALUE || edges[i][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    /**
                     * dist[j]: 依次为DA、DB、DC、DD、DE、DF、DG的距离
                     * dist[i]: 依次为DA、DB、DC、DD、DE、DF、DG的距离
                     * edges[i][j]: 依次遍历整个邻接矩阵
                     */
                    if (dist[j] > dist[i] + edges[i][j]) {
                        dist[j] = dist[i] + edges[i][j];
                    }
                }
            }
        }
        
        // 校验负环
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                if (i == j) {
                    continue;
                }
                if (dist[i] == Integer.MAX_VALUE || edges[i][j] == Integer.MAX_VALUE) {
                    continue;
                }
                /**
                 * 此时应该不存在指向起点的负值路径，如果存在，则在单个间隔节点的情况下，再次出现替换；
                 * 之所以是单个间隔节点，是因为上面的循环已经将“起点”可达的所有节点的路径长度都放入dist中了；
                 */
                if (dist[j] > dist[i] + edges[i][j]) {
                    System.out.println("负环");
                    break;
                }
            }
        }
        
        System.out.print("源点" + start + ": ");
        for (int i = 0; i < dist.length; i++) {
            System.out.print(vertexs[i] + ":(" + dist[i] + ") ");
        }
    }
    
    public static void main(String[] args) {
        exec('D');
    }
}
