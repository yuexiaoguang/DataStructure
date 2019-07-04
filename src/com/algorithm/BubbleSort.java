package com.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序
 * 由小到大
 */
public class BubbleSort {
	
	/**
	 * 时间复杂度：n*(n-1)/2
	 * 以26个元素为例，需要步骤325
	 * 把小的往前放，第一次确定第一个最小的元素，第二次确定第二个元素，以此类推。已经确定的元素不需要再比较
	 */
	static int bubbleSort1(int[] unsorted) {
		int count = 0;
        for (int i = 0; i < unsorted.length-1; i++) {
            for (int j = i+1; j < unsorted.length; j++) {
            	count++;
                //如果前一个大于后一个，交换
                if (unsorted[i] > unsorted[j]) {
                    int temp = unsorted[i];
                    unsorted[i] = unsorted[j];
                    unsorted[j] = temp;
                    //unsorted[0]始终是较小的那个，即，当i=0, 比较到最后一个元素, unsorted[0]可以确定为最小的一个
                    //第二次循环，unsorted[0]是序列中最小的一个，unsorted[1]可以确定为剩下的序列中（除了unsorted[0]）最小的一个
                    //以此类推
                }
            }
        }
        return count;
    }
	
	/**
	 * 时间复杂度是(n-1)*(n-1)
	 * 以26个元素为例，需要步骤625
	 * 两两比较，左边的比右边的大，交换位置，否则不动。所有的元素都要和其他元素比一遍，会有一半的重复比较
	 */
	static int bubbleSort2(int[] unsorted) {
		int count = 0;
		for (int i = 0; i < unsorted.length-1; i++) {
			for (int j = 0; j < unsorted.length-1; j++) {
				int temp = unsorted[j];
				int pos = j + 1;
				count++;
				if (temp > unsorted[pos]) {
					unsorted[j] = unsorted[pos];
					unsorted[pos] = temp;
				}
			}
        }
		return count;
	}
	
	/**
	 * 时间复杂度：(n)*(n-1)/2
	 * 以26个元素为例，需要步骤325
	 * 把大的往后放，第一次确定最后一个最大的，第二次确定倒数第二个最大的，以此类推。已经确定的元素不需要再比较
	 */
	static int bubbleSort3(int[] unsorted) {
		int count = 0;
		for (int i = 0; i < unsorted.length-1; i++) {
			for (int j = 0; j < unsorted.length - i - 1; j++) { 
				count++;
                //如果前一个大于后一个，交换
                if (unsorted[j] > unsorted[j + 1]) {
                    int temp = unsorted[j];  
                    unsorted[j] = unsorted[j + 1];  
                    unsorted[j + 1] = temp;
                    // i=0,j=0, j循环(n-1)次，unsorted[0]保存的是unsorted[0]和unsorted[1]中较小的值
                    // i=0,j=1, j循环(n-1)次，unsorted[1]保存的是unsorted[1]和unsorted[2]中较小的值
                    // 以此类推，直到将序列中最大的一个值移动到序列末尾
                    // i=1,j=0, j循环(n-2)次，unsorted[0]保存的是unsorted[0]和unsorted[1]中较小的值
                    // 以此类推，直到将剩下的序列中的最大值移动到序列倒数第二位
                    //以此类推
                }  
            }  
        }
		return count;
	}
	
	public static void main(String[] args) {
		int[] x = { 2, 67, 9, 10, 77, 15, 16, 17, 18, 85, 23, 27, 91, 93, 29, 31, 33, 34, 99, 38, 40, 43, 50, 52, 61, 62 };
		System.out.println(bubbleSort3(x));
		System.out.println(Arrays.toString(x));
		
		
//		Set<Integer> set = new HashSet<>();
//		Random random = new Random();
//		for (int i = 0; i < 30; i++) {
//			set.add(random.nextInt(100));
//		}
//		System.out.println(set);
	}
}