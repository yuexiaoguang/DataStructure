package com.algorithm;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {
	
	/**
	 * last是数组的长度，unsorted[last]要么越界，要么属于无效数据；
	 * 
	 * 逻辑上：
	 * 将unsorted[first -> mid]看作第一个有序的数组；
	 * 将unsorted[mid -> last]看作第二个有序的数组；
	 * 此方法将这两个数组合并成一个有序的数组
	 */
	static void merge(int[] unsorted, int first, int mid, int last, int[] sorted) {
        int i = first, j = mid;
        int k = 0;//sorted[]的索引
        while (i < mid && j < last) {
            /**
             * 要归并的两个数组:
             * 先从双方第一个最小的元素开始比较；
             * 然后第一个元素中较大的值，与另一个数组中的第二个元素比较；
             * 以此类推，直到比较完其中一个数组，跳出循环，下一步
             */
            if (unsorted[i] < unsorted[j]) {
                sorted[k] = unsorted[i];
                k++;
                i++;
            } else {
                sorted[k] = unsorted[j];
                k++;
                j++;
            }
        }
        /**
         * 两个while只会执行其中一个，因为只会有一个数组会有剩余；
         * 剩余部分依次放入有序的sorted数组；
         */
        while (i < mid) {
            sorted[k] = unsorted[i];
            k++;
            i++;
        }
        while (j < last) {
            sorted[k] = unsorted[j];
            k++;
            j++;
        }
        /**
         * 将unsorted[]上参与排序的first->last元素，替换为sorted中排好顺序的对应的数值；
         * 其实就是将unsorted中的first->last元素排好顺序放回去；
         * k值，防止复制没有参与排序的其它无关数据(0或上次剩余的数据)，sorted是会被复用的；
         */
        for (int v = 0; v < k; v++) {
            unsorted[first + v] = sorted[v];
        }
    }

    /**
     * 将一个数组，拆分成多个逻辑上的小数组，每个小数组包括两个或三个元素，再逐一进行合并排序
     */
    static void merge_sort(int[] unsorted, int first, int last, int[] sorted) {
        // 一直分到只剩两个数据为止
        if (first + 1 < last) {
            int mid = (first + last) / 2;
            merge_sort(unsorted, first, mid, sorted);
            merge_sort(unsorted, mid, last, sorted);
            /**
             * 到这里，先开始最小的子数组的排序，之后，逐步向上递归返回，扩大子数组的排序范围
             */
            merge(unsorted, first, mid, last, sorted);
        }
    }
    
    /**
     * sorted的逐次变化：
     * [2, 4, 0, 0, 0, 0, 0]
     * [2, 4, 6, 0, 0, 0, 0]
     * [1, 5, 6, 0, 0, 0, 0]
     * [8, 9, 6, 0, 0, 0, 0]
     * [1, 5, 8, 9, 0, 0, 0]
     * [1, 2, 4, 5, 6, 8, 9]
     * 
     * unsorted的逐次变化：
     * [6, 2, 4, 1, 5, 9, 8]
     * [6, 2, 4, 1, 5, 9, 8]
     * [2, 4, 6, 1, 5, 9, 8]
     * [2, 4, 6, 1, 5, 9, 8]
     * [2, 4, 6, 1, 5, 8, 9]
     * [2, 4, 6, 1, 5, 8, 9]
     * [1, 2, 4, 5, 6, 8, 9]
     */
    public static void main(String[] args) {
    	int[] x = {6,2,4,1,5,9,8};
        int[] sorted = new int[x.length];
        merge_sort(x, 0, x.length, sorted);
        System.out.println(Arrays.toString(sorted));
	}
}
