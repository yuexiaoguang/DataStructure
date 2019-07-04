package com.algorithm;

import java.util.Arrays;

/**
 * 希尔排序
 * 希尔排序是基于插入排序的以下两点性质而提出改进方法的：
 * 1. 插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率。
 * 2. 但插入排序一般来说是低效的，因为插入排序每次只能将数据移动一位。
 * 
 * 希尔排序是按照不同步长对元素进行插入排序，当刚开始元素很无序的时候，步长最大，所以插入排序的元素个数很少，速度很快；
 * 当元素基本有序了，步长很小，插入排序对于有序的序列效率很高。
 * 所以，希尔排序的时间复杂度会比o(n^2)好一些。
 */
public class ShellSort {
	
	static void shell_sort(int[] unsorted) {
	    int h;//h是增量
        /**h增量每次缩小为上一次的一半，小于1时退出*/
        // 根据h对数组进行分组
        for (h = unsorted.length / 2; h > 0; h /= 2) {//增量大小
            /**
             * i > j
             * 例子：h = 4 , 数组长度为10
             * 第一组：0, 4, 8进行排序交换
             * 第二组：1, 5, 9
             * 第三组：2, 6
             * 第四组：3, 7
             * ---------------
             * 第二遍：h = 2
             * 第一组：0, 2, 4, 6, 8
             * 第二组：1, 3, 5, 7, 9
             */
            for(int x = 0; x < h; x++) {
                for(int i = x + h; i < unsorted.length; i += h) {
                    /**插入排序, 一组(例如：0, 4, 8)进行排序*/
                    int temp = unsorted[i];
                    int j;
                    for(j = i - h; j >= 0 && unsorted[j] > temp; j -= h) {
                        unsorted[j + h] = unsorted[j];
                    }
                    unsorted[j + h] = temp;
                }
            }
        }
	}
	
	public static void main(String[] args) {
//		int[] x = { 6, 2, 4, 1, 5, 9 };
	    int[] x = { 9,8,7,6,5,4,3,2,1,0 };
        shell_sort(x);
        System.out.println(Arrays.toString(x));
	}
}