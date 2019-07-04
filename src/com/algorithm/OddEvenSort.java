package com.algorithm;

import java.util.Arrays;

/**
 * 奇偶排序
 */
public class OddEvenSort {
	
	static void oddEvenSort2(int[] ary) {  
        //奇偶排序  
        boolean flag = true;  
        while (flag) {  
            boolean odd = false, even = false;  
            for (int i = 0; i < ary.length - 1; i+=2) {//从偶数位置开始，
                if (ary[i] > ary[i + 1]) {
                	int temp = ary[i];
                	ary[i] = ary[i+1];
                	ary[i+1] = temp;
                    odd = true;  
                }   
            }  
            for (int i = 1; i < ary.length - 1; i+=2) {//从奇数位置开始
                if (ary[i] > ary[i + 1]) {  
                	int temp = ary[i];
                	ary[i] = ary[i+1];
                	ary[i+1] = temp;
                    even = true;  
                }   
            }  
            flag = odd || even; //若为false，表示不论奇偶序列，一个符合条件的比较都没有  
        }  
    }
	
	public static void main(String[] args) {
		int[] x = { 9,8,7,6,5,4,3,2,1,0 };
		oddEvenSort2(x);
		System.out.println(Arrays.toString(x));
	}
}