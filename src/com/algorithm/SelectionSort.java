package com.algorithm;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectionSort {
	/**选择排序的时间复杂度：简单选择排序的比较次数与序列的初始排序无关。 
	 * 假设待排序的序列有 N 个元素，则比较次数永远都是N (N - 1) / 2。
	 * 而移动次数与序列的初始排序有关。当序列正序时，移动次数最少，为 0。当序列反序时，移动次数最多，为3N (N - 1) /  2。
	 * 所以，综上，简单排序的时间复杂度为 O(N2)。
	 */
	static void selection_sort(int[] unsorted) {
        for (int i = 0; i < unsorted.length; i++) {
            int min = unsorted[i], min_index = i;
            for (int j = i; j < unsorted.length; j++) {
                if (unsorted[j] < min) {//先找到最小的元素
                    min = unsorted[j];
                    min_index = j;
                }
            }
            //和冒泡排序不一样的是，选择排序是先找到最小元素之后，再替换；冒泡排序是一旦找到当前最小元素，马上替换，冒泡排序的最小元素是在替换中找到的
            if (min_index != i) {//交换位置，最小元素移动到第一位
                int temp = unsorted[i];
                unsorted[i] = unsorted[min_index];
                unsorted[min_index] = temp;
            }
        }
    }
	
	public static void main(String[] args) {
		int[] x = { 6, 2, 4, 1, 5, 9 };
        selection_sort(x);
        System.out.println(Arrays.toString(x));
	}
}