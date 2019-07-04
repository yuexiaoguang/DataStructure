package com.algorithm;

import java.util.Arrays;

/**
 * 计数排序
 */
public class CountingSort {
    
    /**
     * 允许有零值和重复值
     * 
     * @param array
     * @param maxValue 最大的值
     */
    public static void countSort(int[] array, int maxValue) {  
        int[] countArray = new int[maxValue + 1];  
        for (int i = 0; i < array.length; i++) {//桶排序
            int value = array[i];  
            countArray[value] += 1;//array中某个元素的个数，和桶排序有所区别
        }  
        /**
         * 累加出待排序的数组中，各个元素应该待的位置
         */
        for (int i = 1; i < countArray.length; i++) {  
            countArray[i] += countArray[i - 1];  
        }  

        int[] temp = new int[array.length];  
        for (int i = array.length - 1; i >= 0; i--) {  
            int value = array[i];
            int position = countArray[value] - 1;//当前元素应该在的位置

            temp[position] = value;// 排好序的数组
            countArray[value] -= 1;// 取出一个元素，就要减1，防止值相等的元素覆盖
        }  

        for (int i = 0; i < array.length; i++) {  
            array[i] = temp[i];  
        }  
    }
    
    public static void main(String[] args) {  
        int[] array = { 9,8,7,6,5,4,3,2,1,0 };
        countSort(array, 10);  
        System.out.println(Arrays.toString(array));
    }  
}