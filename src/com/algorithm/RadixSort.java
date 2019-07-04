package com.algorithm;

import java.util.Arrays;

/**
 * 基数排序
 */
public class RadixSort {
    
	/**
	 * 会有一些浪费，比如只有一位数的排序，需要多循环9次；
	 * 1. 先按个位数排序，这样十位数相同的，个位数大的在后面；
	 * 2. 再按十位数排序，这样百位数相同的，十位数大的在后面；
	 * 3. ...以此类推
	 */
	static void radixSort1(int[] unsorted) {
		int array_x = 10;//单个数字最大值，不会大于10，也表示10位数以内的上限
		int array_y = unsorted.length;
        for (int i = 0; i < array_x; i++) {
            int[][] bucket = new int[array_x][ array_y];
            for (int item : unsorted) {
                int temp = (item / (int)Math.pow(10, i)) % 10;//先循环个位数，再循环十位数，以此类推
                for (int l = 0; l < array_y; l++) {
                	// 依次按照个位数、十位数、百位数...大小排列进二维数组，在某一位数字上已经完成排序
                    if (bucket[temp][l] == 0) {
                        bucket[temp][l] = item;
                        break;
                    }
                }
            }
            /*
             * bucket[][] 个位数排序为：
             *  [10, 40, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[91, 31, 61, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[2, 52, 62, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
				[23, 93, 33, 43, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
				[34, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[15, 85, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
				[67, 77, 17, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[18, 38, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[9, 29, 99, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
				
				十位数排序为：
				[2, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[10, 15, 16, 17, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[23, 27, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[31, 33, 34, 38, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[40, 43, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[50, 52, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[61, 62, 67, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[77, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[85, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
				[91, 93, 99, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
				
				百位数排序为：
				[2, 9, 10, 15, 16, 17, 18, 23, 27, 29, 31, 33, 34, 38, 40, 43, 50, 52, 61, 62, 67, 77, 85, 91, 93, 99], 
				[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
				[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], 
             * */
            //按照二维数组的位置，逐一放入数据，比如：
            //第一次为：10,40,50,91,31,61,2...
            //第二次为：2,9,10,15,16,17,18,23...
            for (int o = 0, x = 0; x < array_x; x++) {
                for (int y = 0; y < array_y; y++) {
                    if (bucket[x][y] == 0) continue;
                    // unsorted要填充修改array_x遍
                    unsorted[o++] = bucket[x][y];
                }
            }
        }
    }
	
	public static void main(String[] args) {
		int[] x = { 6, 2, 4, 1, 5, 9 };
        radixSort1(x);
        System.out.println(Arrays.toString(x));
	}
}
