package com.tree.avl;

public class Node {
    private int key;//值
    private int height;//高度
    private Node lchild;//指向左儿子的地址
    private Node rchild;//指向右儿子的地址
    
    public Node(int value) {
        this.key = value;
    }
    
    public Node() {
    }
    
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Node getLchild() {
        return lchild;
    }
    public void setLchild(Node lchild) {
        this.lchild = lchild;
    }
    public Node getRchild() {
        return rchild;
    }
    public void setRchild(Node rchild) {
        this.rchild = rchild;
    }
    
    @Override
    public String toString() {
        return "key: " + this.key + "<-> height: " + this.height;
    }
}