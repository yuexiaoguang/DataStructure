package com.algorithm;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertionSort {
	/**
	 * 时间复杂度：n*(n-1)/2
	 * 直接插入排序，从小到大排序
	 */
	static void insertionSort1(int[] unsorted) {
        for (int i = 1; i < unsorted.length; i++) {
            int temp = unsorted[i];// 这一轮的基准值，基准值之前的值一定是有序的
            int j = i;
            /**
             * 最多只循环一次，因为之前的数据是有序的
             * 原始值：{ 6, 2, 4, 1, 5, 9 }
             * [2, 6, 4, 1, 5, 9]
             * [2, 4, 6, 1, 5, 9]
             * [1, 2, 4, 6, 5, 9]
             * [1, 2, 4, 5, 6, 9]
             * [1, 2, 4, 5, 6, 9]
             * [1, 2, 4, 5, 6, 9]
             */
            // temp在这一轮中是不变的
            while (j > 0 && unsorted[j - 1] > temp) {
                /*
                 * 将前面>temp的大值往后复制传递；
                 * 这个循环中间，j - 1这个位置是作废的，但是这个位置的实际值也是不断变化的；
                 */
                unsorted[j] = unsorted[j - 1];
                j--;
            }
            unsorted[j] = temp;
        }
    }
    
    /**
     * 二分插入算法，效率更高
     */
    static void insertionSort2(int[] source) {  
        int i, j;  
        int high, low, mid;  
        int temp;
        for (i = 1; i < source.length; i++) {  
            low = 0;  
            // 查找区下界  
            high = i - 1;  
            // 当前待插入记录
            temp = source[i];  
            while (low <= high) {  
                /**
                 * 从前面已有序的部分数据中找出中间值，而避免可能的逐个比较（最坏情况）
                 */
                mid = (low + high) / 2;  
                // 如果待插入记录比中间记录小，跳过mid到high的比较
                if (temp < source[mid]) {
                    // 插入点在低半区  
                    high = mid - 1;  
                } else {// 如果待插入记录比中间记录大，跳过low到mid的比较
                    // 插入点在高半区  
                    low = mid + 1;  
                }
            }
            /**
             * 到这里low - high = 1，确定到两个数之间，如果source[j + 1] 和source[j]相等，也会交换位置；
             * 将前面所有大于当前待插入记录的记录后移;
             * 此处必须循环，以将low到i - 1之间的数据，往后移动一位
             */
            for (j = i - 1; j >= low; j--) {  
                source[j + 1] = source[j];  
            }  
            // 将待插入记录回填到正确位置.   
            source[low] = temp;
        }  
    }  

	public static void main(String[] args) {
		int[] x = { 6, 2, 4, 1, 5, 9 };
		insertionSort2(x);
        System.out.println(Arrays.toString(x));
	}
}