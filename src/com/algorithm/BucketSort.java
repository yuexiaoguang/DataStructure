package com.algorithm;

import java.util.Arrays;

/**
 * 桶排序
 */
public class BucketSort {
	
	/**用空间换时间，不能对0和重复的数值进行排序
	 * @param unsorted 待排序数组
	 * @param maxNumber 要排序的数组中的最大数值
	 * @return 已排序数组，包含很多个0（空桶）
	 */
	static int[] bucket_sort(int[] unsorted, int maxNumber) {
        int[] sorted = new int[maxNumber + 1];
        for (int i = 0; i < unsorted.length; i++) {
            sorted[unsorted[i]] = unsorted[i];
        }
        return sorted;
    }
	
	public static void main(String[] args) {
		int[] x = { 99, 65, 24, 47, 50, 88,33, 66, 67, 31, 18 };
		int[] sorted = bucket_sort(x, 99);
		System.out.println(Arrays.toString(sorted));
	}
}