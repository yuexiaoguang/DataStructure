package com.string;

import java.util.Arrays;

/**
 * KMP算法
 */
public class KMPMatch {

    /**
     * 计算部分匹配表
     * 
     * 在某个字符失配时，该字符对应的next 值会指示下一步匹配中，模式串应该跳到哪个位置（跳到next [j] 的位置）。
     * 如果next [j] 等于0或-1，则跳到模式串的开头字符；
     * 若next [j] = k 且 k > 0，代表下次匹配跳到j 之前的某个字符，而不是跳到开头。
     */
    public static int[] getNext(String pattern) {
        int j = 0, k = -1;// j 是next数组的下标，从0开始递增
        int[] next = new int[pattern.length()];
        next[0] = -1;// 值为 -1 用于让目标串向后移动一个字符，再与模式串第一个字符匹配
        
        while (j < pattern.length() - 1) {
            /**
             * pattern.charAt(j)表示模式串pattern中的字符，从0开始递增；
             * pattern.charAt(k)表示模式串开头的字符；
             * k与j同时递增，表示pattern.charAt(j)字符之前的字符中与开头的字符相等（相同前缀）的累加值
             */
            if (-1 == k || pattern.charAt(j) == pattern.charAt(k)) {
                j++;
                k++;
                next[j] = k;
            } else {
                /**
                 * 一个不断的回溯过程。模式串中也会有多次重复的子串，从而可以应用KMP算法；
                 * 模式串的KMP算法，利用已知的前面字符对应的next数组，判断要跳转的位置，从而不必从头开始匹配
                 */
                k = next[k];
            }
        }
        return next;
    }
    
    
    /**
     * 优化后的next数组
     */
    static int[] onext(String pattern) {
        int j = 0, k = -1;// j 是next数组的下标，从0开始递增
        int[] next = new int[pattern.length()];
        next[0] = -1;// 值为 -1 用于让目标串向后移动一个字符，再与模式串第一个字符匹配
        
        while (j < pattern.length() - 1) {
            if (-1 == k || pattern.charAt(j) == pattern.charAt(k)) {
                j++;
                k++;
                next[j] = k;
                
                /**
                 * 如果字符pattern.charAt(j)对应的next[j]要跳转到的字符等于它自己，那么这个跳转是没有意义的；
                 * 就要“直接”跳转到next[next[j]]，避免没有意义的重复跳转
                 */
                if (pattern.charAt(j) == pattern.charAt(next[j])) {
                    next[j] = next[next[j]];
                }
            } else {
                k = next[k];
            }
        }
        return next;
    }

    /**
     * KMP算法
     */
    static int kmpMatch(String target, String pattern) {
        int i = 0, j = 0, index = 0;
        
        int[] next = getNext(pattern); // 计算部分匹配表
        System.out.println(Arrays.toString(next));
        System.out.println(Arrays.toString(onext(pattern)));

        while (i < target.length() && j < pattern.length()) {
            /**
             * j 等于 -1 表示pattern第一个字符与target的当前字符不匹配；
             * 而i和j同时加一，则j = 0 模式串还是第一个字符，而target则向后移动了一个字符；
             * 从而体现了next[0] = -1 的作用，为了让target目标串向后移动一个字符，再与pattern第一个字符匹配
             */
            if (-1 == j || target.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                // 失配时，模式串P相对于文本串S向右移动了j - next [j] 位。
                j = next[j]; // 如果出现部分不匹配，获取跳过的位置
            }
        }

        if (j >= pattern.length())
            index = i - pattern.length(); // 匹配成功，返回匹配子串的首字符下标
        else
            index = -1; // 匹配失败

        return index;

    }

    public static void main(String[] args) {
        String target = "BBC ABCDAB ABCDABCDABDE";
        String pattern = "ABCDABD";
        
        int index = kmpMatch(target, pattern);
        System.out.format("[%s] 起始位置:  %d of [%s]", pattern, index, target);
    }
}
