package com.algorithm;

import java.util.Arrays;

/**
 * 鸽巢排序
 */
public class PigeonholeSort {
	/**
	 * @param unsorted
	 * @param maxNumber 数组中出现的最大数值
	 * @return
	 */
	static int[] pogeon_sort(int[] unsorted, int maxNumber) {
        int[] pogeonHole = new int[maxNumber + 1];
        for (int item : unsorted) {
            pogeonHole[item]++;
        }
        return pogeonHole;
        /*
         * pogeonHole[10] = 4; 的含意是
         * 在待排数组中有4个10出现,同理其它
         */
    }
	
	public static void main(String[] args) {
		int[] x = { 99, 65, 24, 47, 47, 50, 99, 88, 66, 33, 66, 67, 31, 18, 24 };
        int[] sorted = pogeon_sort(x, 99);
        System.out.println(Arrays.toString(sorted));
	}
}
