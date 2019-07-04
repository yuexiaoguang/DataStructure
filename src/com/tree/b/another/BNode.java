package com.tree.b.another;

import java.util.LinkedList;

/**
 * B树的节点，包含多个关键字
 * 
 * 要求：
 * 1. 方便地值的插入、删除中间元素，同时位置索引能够确定
 * 2. 有序的值能够直接插入指定位置
 * 3. 方便的修改上下级关系
 * 4. 节点内的关键字链可以合并、断开
 * 5. 上下级节点内的关键字移动
 * 6. 查找其左右兄弟节点
 * 7. 子节点顺序与关键字顺序的动态对应
 * 8. 插入、删除头结点
 * 9. 插入、删除尾节点
 */
public class BNode {
    
    private final int m;// 阶数
    private BNode parent;
    
    /**
     * 节点内的关键字
     */
    private LinkedList<Integer> items;
    
    /**
     * 该节点的子节点
     * 按顺序与items中的关键字大小对应，但不准确
     */
    private LinkedList<BNode> children;
    
    /**
     * @param m B树的阶数
     */
    public BNode(final int m, final BNode parent) {
        this.m = m;
        this.setItems(new LinkedList<>());
        this.setParent(parent);
    }
    
    
    public boolean isLeaf() {
        return children == null ? true : (children.isEmpty() ? true : false);
    }
    
    /**
     * 折半查找，返回其索引
     * 
     * @param value 要搜索的值
     */
    public Result search(final int value) {
        int start = 0;
        int end = this.items.size() - 1;
        int mid = (start + end)/2;
        
        while (start <= end) {
            mid = (start + end)/2;
            
            if (items.get(mid) == value) {
                return new Result(true, mid, 0);
            }
            if (items.get(mid) < value) {// 后半段
                start = mid + 1;
            }
            if (items.get(mid) > value) {// 前半段
                end = mid - 1;
            }
        }
        if (mid == end) {
            // 如果value大于items中的所有值，试一试最后一个
            return new Result(false, -1, mid + 1);
        }
        return new Result(false, -1, mid);
    }
    
    
    public int insert(final int value) {
        if (0 == items.size()) {
            items.add(value);
            return 1;
        }
        int start = items.size();
        for (int i = 0; i < items.size(); i++) {
            int v = items.get(i);
            if (v < value) {
                continue;
            }
            items.add(i, value);
            break;
        }
        if (items.size() == start) {// 此时value最大，应该插入末尾
            items.addLast(value);
        }
        return items.size();
    }
    
    
    public boolean remove(final int value) {
        return this.items.removeIf(e -> e.equals(value));
    }
    
    
    public void addNode(BNode node) {
        if (children == null) {
            children = new LinkedList<>();
        }
        if (children.isEmpty()) {
            children.add(node);
            return;
        }
        int last = node.getItems().getLast();
        
        int firstValue = this.items.getFirst();
        int lastValue = this.items.getLast();
        if (firstValue > last) {// 这个节点最小
            children.addFirst(node);
            return;
        }
        if (lastValue < last) {
            children.addLast(node);// 这个子节点最大
            return;
        }
        
        for (int i = 0; i < items.size(); i++) {
            int v = this.items.get(i);
            if (last < v) {
                children.add(i, node);
                break;
            }
        }
    }


    public BNode getParent() {
        return parent;
    }


    public void setParent(BNode parent) {
        this.parent = parent;
    }


    public LinkedList<Integer> getItems() {
        return items;
    }


    public void setItems(LinkedList<Integer> items) {
        this.items = items;
    }


    public LinkedList<BNode> getChildren() {
        return children;
    }


    public void setChildren(LinkedList<BNode> children) {
        this.children = children;
    }
    
}
