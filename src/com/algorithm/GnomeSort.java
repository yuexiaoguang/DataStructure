package com.algorithm;

import java.util.Arrays;

/**
 * 地精排序
 */
public class GnomeSort {
	
    
	static void gnome_sort(int[] unsorted) {
        int i = 0;
        while (i < unsorted.length) {
            if (i == 0 || unsorted[i - 1] <= unsorted[i]) {// 正确的顺序是从小到大
                i++;
            } else {
                int tmp = unsorted[i];
                unsorted[i] = unsorted[i - 1];
                unsorted[i - 1] = tmp;
                /**
                 * 如果有交换，倒退一位比较，将交换过来的（第一个乱序的元素）与之前的元素逐一比较；
                 * 直到将其放入正确的次序
                 */
                i--;
            }
        }
    }
	
	public static void main(String[] args) {
		int[] unsorted = { 9,8,7,6,5,4,3,2,1,0 };
		gnome_sort(unsorted);
		System.out.println(Arrays.toString(unsorted));
	}
}
