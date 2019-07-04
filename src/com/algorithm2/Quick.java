package com.algorithm2;

import java.util.Arrays;

class Quick {
    public static void main(String[] args) {
        int[] x = { 6, 2, 4, 1, 5, 9 };
        sort(x, 0, x.length - 1);
        System.out.println(Arrays.toString(x));
    }
    
    static void sort(final int[] arr, final int start, final int end) {
        int l = start;
        int h = end;
        int povit = arr[start];// 第一个为基准值
        
        if (start >= end) {
            return;
        }
        
        /**
         * 不断的位置对换，从而大于基准值的在右边，小于基准值的在左边
         * 保证每个位都与基准值比较过一次
         * l、h递增、递减防止多次重复比较
         */
        while(l < h) {
            while(l < h && arr[h] >= povit) {// h在变动，需要判断 l < h
                h--;
            }
            if(l < h){// 从后往前，有小于基准值的，交换位置
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                l++;// l处的值已经比较过了，需要跳过
            }
            // 第一轮，此时h位是基准值
            while(l < h && arr[l] <= povit) {// 从前往后比较
                l++;
            }
            
            if(l < h){
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                h--;// h处的值已经比较过了，需要跳过
            }
        }
        
        sort(arr, start, l-1);
        sort(arr, l+1, end);
    }
    
    
    
    /**
     * 减少交换次数，提高效率
     */
    static void sort3(final int[] arr, final int start, final int end) {
        int l = start, h = end;
        int pivot = arr[start];
        
        if (start >= end) {
            return;
        }
        
        while(l < h) {
            /*按h--方向遍历目标数组，直到比pivot小的值为止*/
            while(l < h && arr[h] >= pivot) {
                h--;
            }
            if(l < h) {
                /*
                 * targetArr[l]已经保存在pivot中，可将后面的数填入
                 * 意图为：由于arr[h] < arr[l1](即pivot), 将h处的值交换到l1处
                 * h处的值现在作废
                 */
                arr[l] = arr[h];/**此处的l记为 l1*/
                l++;// l处的值已经比较过了，需要跳过
            }
            /*按l++方向遍历目标数组，直到比pivot大的值为止*/
            while(l < h && arr[l] <= pivot) {
                l++;
            }
            if(l < h) {
                /**l1和l2不是同一个位置*/
                /*
                 * targetArr[h]已保存在targetArr[l]中，可将前面的值填入
                 * 意图为：由于arr[l2] > pivot, 将l2处的值交换到h处
                 * l2处的值作废，原来h处的值赋值arr[l2]
                 */
                arr[h] = arr[l];/**此处的l记为l2*/
                h--;// h处的值已经比较过了，需要跳过
            }
        }
        /**此处的l为l2，将基准值放到l2处，此时完成h、l1、l2三个点位置的移动*/
        arr[l] = pivot;
         
        /*递归调用，把key前面的完成排序*/
        sort3(arr, start, l-1);
         
        /*递归调用，把key后面的完成排序*/
        sort3(arr, l+1, end);
    }
}