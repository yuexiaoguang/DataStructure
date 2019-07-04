package com.algorithm;

import java.util.Arrays;

/**
 * 鸡尾酒排序
 */
public class CocktailSort {
	/**
	 * 冒泡排序的一种
	 */
	static void cocktail_sort(int[] unsorted) {
        boolean swapped = false;
        do{
        	for (int i = 0; i < unsorted.length - 1; i++) {
                if (unsorted[i] > unsorted[i + 1]) {//小的放前面
                    int temp = unsorted[i];
                    unsorted[i] = unsorted[i + 1];
                    unsorted[i + 1] = temp;
                    swapped = true;
                }
            }
        	swapped = false;
            for (int j = unsorted.length - 1; j > 1; j--) {//大的放后面
                if (unsorted[j] < unsorted[j - 1]) {
                    int temp = unsorted[j];
                    unsorted[j] = unsorted[j - 1];
                    unsorted[j - 1] = temp;
                    swapped = true;
                }
            }
		}while (swapped);
    }
	
	public static void main(String[] args) {
		int[] x = { 6, 2, 4, 1, 5, 9 };
		cocktail_sort(x);
		System.out.println(Arrays.toString(x));
	}
}