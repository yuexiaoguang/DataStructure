package com.tree.b.another;

import java.util.LinkedList;

/**
 * B树实现，只允许插入整数，不允许有重复值
 */
public class BTree {
    
    /**
     * 阶数
     */
    private static final int m = 5;
    
    private static final int mid = m/2;
    /**
     * 关键字个数最少(m/2) - 1
     */
    private static final int limit = m/2;
    /**
     * 根节点
     */
    private static BNode root = new BNode(m, null);
    
    
    /**
     * 搜索
     */
    public int search(final int value) {
        BNode node = root;
        
        while (node != null) {
            
            Result result = node.search(value);
            if (result.isSucess()) {
                return result.getKeyIndex();
            }
            node = node.getChildren().get(result.getNodeIndex());
        }
        return -1;
    }
    
    
    /**
     * 插入
     */
    public void insert(final int value) {
        BNode node = root;// 当前节点
        
        if (node.isLeaf()) {// 没有子节点
            int size = node.insert(value);
            if (size <= m - 1) {// 不需要分裂
                return;
            }
            
            // 分裂
            BNode left = new BNode(m, node);
            BNode right = new BNode(m, node);
            
            int itemSize = node.getItems().size();
            int midValue = node.getItems().get(mid);
            while (itemSize > 0) {// node中只保留一个元素
                int index = itemSize - 1;
                int v = node.getItems().get(index);
                if (v == midValue) {
                    itemSize--;
                    continue;
                }
                if (index < mid) {// 左节点
                    left.insert(v);
                } else if (index > mid) {// 排除mid
                    right.insert(v);
                }
                node.getItems().remove(index);
                itemSize--;
            }
            
            node.addNode(left);
            node.addNode(right);
            return;
        }
        
        
        // 应该一直找到叶子节点，然后再不断上浮关键字并分裂节点
        while (node.getChildren() != null) {
            if (value > node.getItems().getLast()) {// 确定到最右边的节点
                node = node.getChildren().getLast();
                break;
            }
            for (int i = 0; i < node.getItems().size(); i++) {
                int v = node.getItems().get(i);
                if (v > value) {
                    node = node.getChildren().get(i);
                    break;
                }
            }
        }
        
        // 最底层的叶子节点
        int size = node.insert(value);
        while (size > m - 1) {// 需要分裂
            if (node.getParent() == null) {// 根节点需要分裂，重新设置根节点
                root = new BNode(m, null);
                node.setParent(root);
                node.getParent().addNode(node);
            }
            
            BNode right = new BNode(m, node.getParent());
            
            int midValue = node.getItems().get(mid);
            
            // 左节点还使用node
            int itemSize = node.getItems().size();
            while (itemSize > (mid - 1)) {// 至少含有[m/2] - 1个关键字
                int v = node.getItems().get(itemSize - 1);
                
                if (v == midValue) {// 分裂出来的右节点不应该包含中间值midValue
                    node.getItems().removeLast();
                    break;
                }
                
                right.insert(v);
                node.getItems().removeLast();
                itemSize--;
            }
            
            // 子节点引用分裂，mid右侧的子节点放入right
            if (! node.isLeaf()) {
                int childSize = node.getChildren().size();
                while (childSize > mid + 1) {// 至少有[m/2]棵子树
                    BNode child = node.getChildren().get(childSize - 1);
                    
                    // 每次插入right的头结点，因为right.addNode(child)不好排序
                    LinkedList<BNode> children = right.getChildren();
                    children = children == null ? new LinkedList<>() : children;
                    children.addFirst(child);
                    right.setChildren(children);
                    node.getChildren().removeLast();
                    childSize--;
                }
            }
            
            node.getParent().insert(midValue);// 子节点的中间值加入父节点
            node.getParent().addNode(right);// right加入父节点中midValue的右侧
            
            // 判断上级节点是否需要分裂
            node = node.getParent();
            size = node.getItems().size();// 此时node已经是parent了
        }
    }
    
    
    /**
     * 删除
     */
    public void delete(final int value) {
        BNode node = root;
        int keyIndex = -1;// 节点中value关键字所在位置索引
        int nodeIndex = -1;// 该节点在其父节点中的索引
        
        while (node != null) {
            Result result = node.search(value);
            if (result.isSucess()) {
                keyIndex = result.getKeyIndex();
                break;
            }
            nodeIndex = result.getNodeIndex();
            if (null == node.getChildren()) {
                break;
            }
            node = node.getChildren().get(result.getNodeIndex());
        }
        if (keyIndex < 0) {
            System.out.println("未找到: " + value);
            return;
        }
        
        /**
         * 在叶子节点
         */
        if (node.isLeaf()) {
            /**
             * 直接删除
             */
            if (node.getItems().size() > limit) {
                node.remove(value);
                return;
            }
            
            /**
             * 兄弟够借
             */
            BNode left = node.getParent().getChildren().get(nodeIndex - 1);// 左兄弟节点
            BNode right = nodeIndex + 1 >= node.getParent().getChildren().size() ? null :node.getParent().getChildren().get(nodeIndex + 1);// 右兄弟节点
            
            if (null != left && left.getItems().size() > limit) {/** 左子树够借 */
                int leftLastValue = left.getItems().getLast();// 左兄弟节点最后一个元素
                
                int downParentValue = node.getParent().getItems().get(nodeIndex - 1);// 父节点中要下移的关键字
                
                /**将左兄弟节点最后一个元素上移到父节点*/
                node.getParent().getItems().set(nodeIndex - 1, leftLastValue);
                left.getItems().removeLast();
                /**父节点关键字下移到当前节点，该关键字是有序的，直接放到头节点*/
                node.getItems().addFirst(downParentValue);
                node.remove(value);
                return;
            } else if (null != right && right.getItems().size() > limit) {/** 右子树够借 */
                int rightFirstValue = right.getItems().getFirst();// 右兄弟节点第一个元素
                
                int downParentValue = node.getParent().getItems().get(nodeIndex);// 父节点中要下移的关键字
                
                /**将右兄弟节点第一个元素上移到父节点*/
                node.getParent().getItems().set(nodeIndex, rightFirstValue);
                right.getItems().removeFirst();
                /**父节点关键字下移到当前节点，该关键字是有序的，直接放到尾节点*/
                node.getItems().addLast(downParentValue);
                node.remove(value);
                return;
            }
            
            
            /**
             * 兄弟不够借
             * TODO 父节点关键字下移，导致数量也可能不够，也需要合并
             */
            if (null != left && left.getItems().size() == limit) {/**与左兄弟节点合并*/
                int mergeParentValue = node.getParent().getItems().get(nodeIndex - 1);// 父节点中介于左兄弟节点与当前节点之间的关键字
                
                /**合并到当前节点*/
                node.getItems().addFirst(mergeParentValue);
                // 叶子节点，只需要合并关键字
                for (int i = left.getItems().size() - 1; i >= 0; i--) {
                    int item = left.getItems().get(i);
                    
                    node.getItems().addFirst(item);// 该值是有序的，直接插入链尾
                }
                
                /**删除父节点关键字、当前节点引用*/
                node.getParent().getItems().remove(nodeIndex - 1);
                node.getParent().getChildren().remove(nodeIndex - 1);
            } else if (null != right && right.getItems().size() == limit) {/**与右兄弟节点合并*/
                int mergeParentValue = node.getParent().getItems().get(nodeIndex);// 父节点中介于当前节点与右兄弟节点之间的关键字
                
                /**合并到当前节点*/
                node.getItems().addLast(mergeParentValue);
                // 叶子节点，只需要合并关键字
                for (int i = 0; i < right.getItems().size(); i++) {
                    int item = right.getItems().get(i);
                    
                    node.getItems().addLast(item);// 该值是有序的，直接插入链尾
                }
                
                /**删除父节点关键字、右兄弟节点引用*/
                node.getParent().getItems().remove(nodeIndex);
                node.getParent().getChildren().remove(nodeIndex + 1);
            }
            node.remove(value);
            return;
        }
        
        
        /**
         * 不在叶子节点
         */
        int leftSubNum = node.getChildren().get(keyIndex).getItems().size();// 该节点左子树的元素个数
        int rightSubNum = node.getChildren().get(keyIndex + 1).getItems().size();// 该节点右子树的元素个数
        
        if (leftSubNum > limit) {/**可以从左子树中借用*/
            int leftLastValue = node.getChildren().get(keyIndex).getItems().getLast();// 左子树的最后一个元素
            
            /**用leftLastValue替换要删除的值，树还是平衡的*/
            node.getItems().set(keyIndex, leftLastValue);
            node.getChildren().get(keyIndex).getItems().removeLast();// 删除左子树最后一个元素, 实现左子树元素的上移
        } else if (rightSubNum > limit) {/**可以从右子树中借用*/
            int rightFirstValue = node.getChildren().get(keyIndex + 1).getItems().getFirst();// 右子树的第一个元素
            
            /**用rightFirstValue替换要删除的值，树还是平衡的*/
            node.getItems().set(keyIndex, rightFirstValue);
            node.getChildren().get(keyIndex + 1).getItems().removeFirst();
        } else {/**都不能借用，直接将两个子节点合并，同时删除这个关键字*/ /** TODO 删除后，可能导致关键字数量不足，需要*/
            // 合并左右子树，将右子树合并入左子树
            BNode left = node.getChildren().get(keyIndex);
            BNode right = node.getChildren().get(keyIndex + 1);
            // 先合并值
            for (int i = 0; i < right.getItems().size(); i++) {
                int item = right.getItems().get(i);
                
                left.getItems().addLast(item);// 该值是有序的，直接插入链尾
            }
            // 再合并子节点的引用
            for (int i = 0; i < right.getChildren().size(); i++) {
                BNode child = right.getChildren().get(i);
                
                left.getChildren().addLast(child);// 该值是有序的，直接插入链尾
            }
            
            node.remove(value);// 删除节点的关键字
            node.getChildren().remove(keyIndex + 1);// 删除右子树的引用
            // 关键字和子树的个数都减 1
        }
        node.remove(value);
    }
    
    
    /**
     * 删除节点中的值，如果该节点内关键字数量不够，就需要循环合并节点；
     * value一定在node节点中，不再查找
     */
    private void mergeNode(BNode node, int value) {
        node.remove(value);
        int size = node.getItems().size();
        while (size < limit) {// 需要合并
            BNode parent = node.getParent();
            int nodeIndex = 0;
            int leftIndex = 0;
            int rightIndex = 0;
            if (parent == null) {// node是根节点
                
            } else {
                for (int i = 0; i < parent.getChildren().size(); i++) {
                    BNode child = parent.getChildren().get(i);
                    if (node == child) {
                        nodeIndex = i;
                        break;
                    }
                }
                leftIndex = nodeIndex - 1;
                rightIndex = nodeIndex + 1;
            }
            
            
            /**
             * 兄弟够借
             */
            BNode left = leftIndex < 0 ? null : parent.getChildren().get(leftIndex);// 左兄弟节点
            BNode right = rightIndex >= parent.getChildren().size() ? null :parent.getChildren().get(rightIndex);// 右兄弟节点
            
            if (null != left && left.getItems().size() > limit) {/** 左子树够借 */
                int leftLastValue = left.getItems().getLast();// 左兄弟节点最后一个元素
                BNode leftLastChildNode = left.getChildren().get(left.getItems().size() - 1);// 左兄弟节点大于leftLastValue的子节点引用
                
                int downParentValue = parent.getItems().get(leftIndex);// 父节点中要下移的关键字
                
                /**将左兄弟节点最后一个元素上移到父节点*/
                parent.getItems().set(leftIndex, leftLastValue);
                left.getItems().removeLast();
                /**父节点关键字下移到当前节点，该关键字是有序的，直接放到头节点*/
                node.getItems().addFirst(downParentValue);
                node.remove(value);
                
                /** 还需要移动左兄弟节点的子节点*/
                if (leftLastChildNode != null) {
                    BNode nodeFirstChildNode = node.getChildren().getFirst();
                    
                }
                return;
            } else if (null != right && right.getItems().size() > limit) {/** 右子树够借 */
                int rightFirstValue = right.getItems().getFirst();// 右兄弟节点第一个元素
                
                int downParentValue = parent.getItems().get(nodeIndex);// 父节点中要下移的关键字
                
                /**将右兄弟节点第一个元素上移到父节点*/
                parent.getItems().set(nodeIndex, rightFirstValue);
                right.getItems().removeFirst();
                /**父节点关键字下移到当前节点，该关键字是有序的，直接放到尾节点*/
                node.getItems().addLast(downParentValue);
                node.remove(value);
                return;
            }
            
            
            /**
             * 兄弟不够借
             * TODO 父节点关键字下移，导致数量也可能不够，也需要合并
             */
            if (null != left && left.getItems().size() == limit) {/**与左兄弟节点合并*/
                int mergeParentValue = parent.getItems().get(leftIndex);// 父节点中介于左兄弟节点与当前节点之间的关键字
                
                /**合并到当前节点*/
                node.getItems().addFirst(mergeParentValue);
                // 叶子节点，只需要合并关键字
                for (int i = left.getItems().size() - 1; i >= 0; i--) {
                    int item = left.getItems().get(i);
                    
                    node.getItems().addFirst(item);// 该值是有序的，直接插入链尾
                }
                
                /**删除父节点关键字、当前节点引用*/
                parent.getItems().remove(leftIndex);
                parent.getChildren().remove(leftIndex);
            } else if (null != right && right.getItems().size() == limit) {/**与右兄弟节点合并*/
                int mergeParentValue = parent.getItems().get(nodeIndex);// 父节点中介于当前节点与右兄弟节点之间的关键字
                
                /**合并到当前节点*/
                node.getItems().addLast(mergeParentValue);
                // 叶子节点，只需要合并关键字
                for (int i = 0; i < right.getItems().size(); i++) {
                    int item = right.getItems().get(i);
                    
                    node.getItems().addLast(item);// 该值是有序的，直接插入链尾
                }
                
                /**删除父节点关键字、右兄弟节点引用*/
                parent.getItems().remove(nodeIndex);
                parent.getChildren().remove(rightIndex);
            }
        }
    }
    
    
    /**
     * 打印
     */
    public void print(int level, BNode node) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append("    ");
        }
        for (Integer integer : node.getItems()) {
            System.out.print(builder.toString());
            System.out.println(integer);
        }
        System.out.println(builder.toString());
        if (node.isLeaf()) {
            return;
        }
        for (BNode sub : node.getChildren()) {
            print(level + 1, sub);
        }
    }
    
    
    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        BTree tree = new BTree();
        
        int[] values = new int[] {39, 22, 97, 41, 53, 13, 21, 40, 30, 27, 33, 36, 35, 34, 24, 29, 26, 17, 28, 23, 31, 32};
        
        for (int i = 0; i < values.length; i++) {
            int value = values[i];
            
//            if (i == 17) {
//                break;
//            }
            
            tree.insert(value);
        }
//        tree.print(0, tree.root);
        
        
        // 删除
        tree.delete(21);
        tree.delete(27);
        tree.delete(32);
        tree.delete(40);
        tree.print(0, tree.root);
    }
}
