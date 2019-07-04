package com.tree.b2;

/**
 * 在B树节点中搜索给定键值的返回结果。
 * <p/>
 * 该结果有两部分组成。第一部分表示此次查找是否成功， 如果查找成功，第二部分表示给定键值在B树节点中的位置，
 * 如果查找失败，第二部分表示给定键值应该插入的位置。
 */
public class SearchResult<V> {
    
    private boolean exist;
    private int index;
    private V value;

    public SearchResult(boolean exist, int index) {
        this.exist = exist;
        this.index = index;
    }

    public SearchResult(boolean exist, int index, V value) {
        this(exist, index);
        this.value = value;
    }

    public boolean isExist() {
        return exist;
    }

    public int getIndex() {
        return index;
    }

    public V getValue() {
        return value;
    }
}
