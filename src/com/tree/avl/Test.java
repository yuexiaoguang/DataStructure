package com.tree.avl;

public class Test {
    public static void main(String[] args) {
//      int[] values = new int[] {4,5,6};
//      int[] values = new int[] {4,5,6,2,3};
//      int[] values = new int[] {4,5,6,2,3,0,1,7,8};
        int[] values = new int[] {1,2,3,4,5,6,7,8,9,10,12,11,13,14,15};
        AVLTree tree = new AVLTreeImpl();
        Node node = null;
        for (int i : values) {
            node = tree.insert(node, i);
        }
//      tree.remove(node, 3);
//      tree.remove(node, 6);
//      tree.remove(node, 7);
        System.out.println(node);
    }
}