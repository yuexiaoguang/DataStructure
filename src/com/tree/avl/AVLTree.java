package com.tree.avl;

public interface AVLTree {
    
    Node insert(Node pnode, int key);
    Node remove(Node pnode, int key);//删除AVL树中节点pdel，并返回被删除的节点
    
    Node minimum(Node pnode);
    Node maximum(Node pnode);
    
    Node search_recurse(Node pnode, int key);
    Node search_iterator(Node pnode, int key);
    
    Node leftRotation(Node pnode);//单旋:左旋操作
    Node rightRotation(Node pnode);//单旋:右旋操作
    Node leftRightRotation(Node pnode);//双旋:先左旋后右旋操作
    Node rightLeftRotation(Node pnode);//双旋:先右旋后左旋操作
}