package com.algorithm;

import java.util.Arrays;

/**
 * 堆排序
 * PriorityQueue
 * 基于一维数组的堆排序，节点索引之间的关系：
 * leftNo = parentNo * 2 + 1
 * rightNo = parentNo * 2 + 2
 * parentNo = (nodeNo - 1) / 2  查找元素父节点的方法，可以根据数组最后一个元素（必定是叶子节点）找出最后一个有子元素的节点
 */
public class HeapSort {
	public static void main(String[] args) {  
        int[] array = { 0,1,2,3,4,5,6,7,8,9};  
        heapSort(array);
        System.out.println(Arrays.toString(array));
    }  

    /**
     * 要得到有序的数组，相当于要array.length次堆排序，才能得出
     */
    public static void heapSort(int[] array) {  
        buildMaxHeap(array);// 第一步，初步有序的堆
        
        /**
         * 到这里只保证二叉树的父节点是其子树中的最小值，不能够保证数组有序；
         * 但是堆顶的根节点，一定是最大的；
         * 1. 将array[0]这个最大的根节点交换到数组最后，不参与之后的排序；
         * 2. 排序array[0-倒数第二个]，得到另一个最大的元素，放入数组的倒数第二个位置；
         * 3. 以此类推...
         */
        for (int l = array.length - 1; l >= 1; l--) {
            // l是要参与排序的数组的长度
            exchangeElements(array, 0, l);

            maxHeap(array, l, 0);
        }  
    }  

    private static void buildMaxHeap(int[] array) {
        /**
         * 由于节点之间的关系（见上），找到二叉树中最后一个有子元素的节点，开始最小的子二叉树的堆排序；
         * 然后继续倒数第二个有子元素的节点，得到这个子二叉树的有序结果；
         * 以此类推...一直到上一层，包含三层数据的节点；
         * 在三层中，也只需比较两级即可，因为最下层已经是排序好的
         */
        int half = (array.length - 1) / 2;
        for (int i = half; i >= 0; i--) {
            maxHeap(array, array.length, i);
        }  
    }  

    private static void maxHeap(int[] array, int heapSize, int p) {  
        int left = p * 2 + 1;// 获取父节点的左子节点索引
        int right = p * 2 + 2;// 获取父节点的右子节点索引

        int largest = p;// 默认子树是有序的
        if (left < heapSize && array[left] > array[p]) {  
            largest = left;
        }  

        if (right < heapSize && array[right] > array[largest]) {  
            largest = right;  
        }  

        if (p != largest) {// 如果子树是无序的
            exchangeElements(array, p, largest);
            /**
             * 递归比较更下面的子树，将子树中最大的元素交换到堆顶；
             * 此时的largest是p的其中一个子节点的索引
             */
            maxHeap(array, heapSize, largest);
        }  
    }

    /**
     * 交换元素顺序
     */
    private static void exchangeElements(int[] array, int index1, int index2) {  
        int temp = array[index1];  
        array[index1] = array[index2];  
        array[index2] = temp;  
    }  
}
