package com.tree.avl;

public class AVLTreeImpl implements AVLTree {
    
    /*返回一棵树的高度*/
    int height(Node node) {
        if (node != null) {
            return node.getHeight();
        }
        return 0;
    }

    /*插入操作*/
    /*递归地进行插入*/
    /*返回插入后的根节点*/
    @Override
    public Node insert(Node pnode, int key) {
        if (pnode == null) {
            pnode = new Node(key);
        }else if (key > pnode.getKey()) {//插入值比当前结点值大，插入到当前结点的右子树上
            pnode.setRchild(insert(pnode.getRchild(), key));
            //插入后出现失衡
            if (2 == Math.abs(height(pnode.getRchild()) - height(pnode.getLchild()))) {
                if (key > pnode.getRchild().getKey()) {//情况一：插入右子树的右节点，进行左旋
                    pnode = leftRotation(pnode);
                } else {//情况三：插入右子树的左节点,进行先右再左旋转
                    pnode = rightLeftRotation(pnode);
                }
            }
        }else if (key < pnode.getKey()) {//插入值比当前节点值小，插入到当前结点的左子树上
            pnode.setLchild(insert(pnode.getLchild(), key));
            //插入后出现失衡
            if (2 == Math.abs(height(pnode.getLchild()) - height(pnode.getRchild()))) {
                if (key < pnode.getLchild().getKey()) {//情况二：插入到左子树的左孩子节点上，进行右旋
                    pnode = rightRotation(pnode);
                }else {//情况四：插入到左子树的右孩子节点上，进行先左后右旋转
                    pnode = leftRightRotation(pnode);
                }
            }
        }
        pnode.setHeight(Math.max(height(pnode.getLchild()), height(pnode.getRchild())) + 1);
        return pnode;
    }

    /*删除指定元素*/
    @Override
    public Node remove(Node pnode, int key) {
        if (pnode == null) {
            return null;
        }
        if (key == pnode.getKey()) { //找到删除的节点
            //因AVL也是二叉排序树，删除节点要维护其二叉排序树的条件
            if (pnode.getLchild() != null && pnode.getRchild() != null) {//若左右都不为空
                // 左子树比右子树高,在左子树上选择节点进行替换
                if (height(pnode.getLchild()) > height(pnode.getRchild())) {
                    //使用左子树最大节点来代替被删节点，而删除该最大节点
                    Node ppre = maximum(pnode.getLchild());//左子树最大节点
                    pnode.setKey(ppre.getKey());//将最大节点的值覆盖当前结点
                    pnode.setLchild(remove(pnode.getLchild(), ppre.getKey()));//递归地删除最大节点
                } else {//在右子树上选择节点进行替换
                    //使用最小节点来代替被删节点，而删除该最小节点
                    Node psuc = minimum(pnode.getRchild());//右子树的最小节点
                    pnode.setKey(psuc.getKey());//将最小节点值覆盖当前结点
                    pnode.setRchild(remove(pnode.getRchild(), psuc.getKey()));//递归地删除最小节点
                }
            } else {
                Node ptemp = pnode;
                if (pnode.getLchild() !=null) {
                    pnode = pnode.getLchild();
                }else if (pnode.getRchild() != null) {
                    pnode = pnode.getRchild();
                }
                return null;
            }
        }else if (key > pnode.getKey()) {//要删除的节点比当前节点大，则在右子树进行删除
            pnode.setRchild(remove(pnode.getRchild(), key));
            //删除右子树节点导致不平衡:相当于情况二或情况四
            if (height(pnode.getLchild()) - height(pnode.getRchild()) == 2) {
                //相当于在左子树上插入右节点造成的失衡（情况四）
                if (height(pnode.getLchild().getRchild()) > height(pnode.getLchild().getLchild())) {
                    pnode = leftRightRotation(pnode);
                }else {//相当于在左子树上插入左节点造成的失衡（情况二）
                    pnode = rightRotation(pnode);
                }
            }
        }else if (key < pnode.getKey()) {//要删除的节点比当前节点小，则在左子树进行删除
            pnode.setLchild(remove(pnode.getLchild(), key));
            //删除左子树节点导致不平衡：相当于情况三或情况一
            if (height(pnode.getRchild()) - height(pnode.getLchild()) == 2) {
                //相当于在右子树上插入左节点造成的失衡（情况三）
                if (height(pnode.getRchild().getLchild()) > height(pnode.getRchild().getRchild())) {
                    pnode = rightLeftRotation(pnode);
                }else {//相当于在右子树上插入右节点造成的失衡（情况一）
                    pnode = leftRotation(pnode);
                }
            }
        }
        return pnode;
    }

    /*返回树中最小节点值*/
    @Override
    public Node minimum(Node pnode) {
        if (pnode == null) {
            return null;
        }
        while (pnode.getLchild() != null) {
            pnode = pnode.getLchild();
        }
        return pnode;
    }

    /*返回树中最大节点*/
    @Override
    public Node maximum(Node pnode) {
        if (pnode == null) {
            return null;
        }
        while (pnode.getRchild() != null) {
            pnode = pnode.getRchild();
        }
        return pnode;
    }

    /*递归查找指定元素*/
    @Override
    public Node search_recurse(Node pnode, int key) {
        if (pnode == null) {
            return null;
        }
        if (key == pnode.getKey()) {
            return pnode;
        }
        if (key > pnode.getKey()) {
            return search_recurse(pnode.getRchild(), key);
        }else {
            return search_recurse(pnode.getLchild(), key);
        }
    }

    /*非递归查找指定元素*/
    @Override
    public Node search_iterator(Node pnode, int key) {
        while (pnode != null) {
            if (pnode.getKey() == key) {
                return pnode;
            }
            if (key > pnode.getKey()) {
                pnode = pnode.getRchild();
            }else {
                pnode = pnode.getLchild();
            }
        }
        return null;
    }

    /*左旋转操作*/
    /*pnode为最小失衡子树的根节点*/
    /*返回旋转后的根节点*/
    @Override
    public Node leftRotation(Node proot) {
        Node prchild = proot.getRchild();
        if (prchild == null) {
            System.out.println(proot);
        }
        proot.setRchild(prchild.getLchild());
        prchild.setLchild(proot);
        
        proot.setHeight(Math.max(height(proot.getLchild()), height(proot.getRchild())) + 1);
        prchild.setHeight(Math.max(height(prchild.getLchild()), height(prchild.getRchild())) + 1);
        return prchild;
    }

    /*右旋转操作*/
    /*pnode为最小失衡子树的根节点*/
    /*返回旋转后的根节点*/
    @Override
    public Node rightRotation(Node proot) {
        Node plchild = proot.getLchild();
        if (plchild == null) {
            System.out.println(proot);
        }
        proot.setLchild(plchild.getRchild());
        plchild.setRchild(proot);
        
        proot.setHeight(Math.max(height(proot.getLchild()), height(proot.getRchild())) + 1);
        plchild.setHeight(Math.max(height(plchild.getLchild()), height(plchild.getRchild())) + 1);
        return plchild;
    }

    /*先左后右做旋转*/
    /*参数proot为最小失衡子树的根节点*/
    /*返回旋转后的根节点*/
    @Override
    public Node leftRightRotation(Node proot) {
        proot.setLchild(leftRotation(proot.getLchild()));
        return rightRotation(proot);
    }

    /*先右旋再左旋*/
    /*参数proot为最小失衡子树的根节点*/
    /*返回旋转后的根节点*/
    @Override
    public Node rightLeftRotation(Node proot) {
        proot.setRchild(rightRotation(proot.getRchild()));
        return leftRotation(proot);
    }
}
