package com.tree.b.another;

public class Result {
    
    private boolean sucess;
    private int keyIndex;// 在节点的关键字中的索引
    private int nodeIndex;// 指示应该查找哪个子节点
    
    public Result(boolean sucess, int keyIndex, int nodeIndex) {
        super();
        this.sucess = sucess;
        this.setKeyIndex(keyIndex);
        this.setNodeIndex(nodeIndex);
    }
    
    public boolean isSucess() {
        return sucess;
    }
    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public int getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }
}
