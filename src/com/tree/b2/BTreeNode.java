package com.tree.b2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * B树中的节点，包含多个关键字。
 */
public class BTreeNode<K, V> {
    
    /** 节点的项，按键非降序存放 */
    private List<Entry<K, V>> entrys;
    /** 内节点的子节点 */
    private List<BTreeNode<K, V>> children;
    /** 是否为叶子节点 */
    private boolean leaf;
    /** 键的比较函数对象 */
    private Comparator<K> kComparator;

    public BTreeNode() {
        entrys = new ArrayList<Entry<K, V>>();
        children = new ArrayList<BTreeNode<K, V>>();
        leaf = false;
    }

    public BTreeNode(Comparator<K> kComparator) {
        this();
        this.kComparator = kComparator;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * 返回项的个数。如果是非叶子节点，根据B树的定义， 该节点的子节点个数为({@link #size()} + 1)。
     * 
     * @return 关键字的个数
     */
    public int size() {
        return entrys.size();
    }

    @SuppressWarnings("unchecked")
    int compare(K key1, K key2) {
        return kComparator == null ? ((Comparable<K>) key1).compareTo(key2) : kComparator.compare(key1, key2);
    }

    /**
     * 在节点中查找给定的键。 如果节点中存在给定的键，则返回一个<code>SearchResult</code>，
     * 标识此次查找成功，给定的键在节点中的索引和给定的键关联的值； 如果不存在，则返回<code>SearchResult</code>，
     * 标识此次查找失败，给定的键应该插入的位置，该键的关联值为null。
     * <p/>
     * 如果查找失败，返回结果中的索引域为[0, {@link #size()}]； 如果查找成功，返回结果中的索引域为[0, {@link #size()} -
     * 1]
     * <p/>
     * 这是一个二分查找算法，可以保证时间复杂度为O(log(t))。
     * 
     * @param key - 给定的键值
     * @return - 查找结果
     */
    public SearchResult<V> searchKey(K key) {
        int low = 0;
        int high = entrys.size() - 1;
        int mid = 0;
        while (low <= high) {
            mid = (low + high) / 2; // 先这么写吧，BTree实现中，l+h不可能溢出
            Entry<K, V> entry = entrys.get(mid);
            if (compare(entry.getKey(), key) == 0) // entrys.get(mid).getKey() == key
                break;
            else if (compare(entry.getKey(), key) > 0) // entrys.get(mid).getKey() > key
                high = mid - 1;
            else // entry.get(mid).getKey() < key
                low = mid + 1;
        }
        boolean result = false;
        int index = 0;
        V value = null;
        if (low <= high) {// 说明查找成功
            result = true;
            index = mid; // index表示元素所在的位置
            value = entrys.get(index).getValue();
        } else {
            result = false;
            index = low; // index表示元素应该插入的位置
        }
        return new SearchResult<V>(result, index, value);
    }

    /**
     * 将给定的项追加到节点的末尾， 你需要自己确保调用该方法之后，节点中的项还是 按照关键字以非降序存放。
     * 
     * @param entry - 给定的项
     */
    public void addEntry(Entry<K, V> entry) {
        entrys.add(entry);
    }

    /**
     * 删除给定索引的<code>entry</code>。
     * <p/>
     * 你需要自己保证给定的索引是合法的。
     * 
     * @param index   - 给定的索引
     * @param 给定索引处的项
     */
    public Entry<K, V> removeEntry(int index) {
        return entrys.remove(index);
    }

    /**
     * 得到节点中给定索引的项。
     * <p/>
     * 你需要自己保证给定的索引是合法的。
     * 
     * @param index - 给定的索引
     * @return 节点中给定索引的项
     */
    public Entry<K, V> entryAt(int index) {
        return entrys.get(index);
    }

    /**
     * 如果节点中存在给定的键，则更新其关联的值。 否则插入。
     * 
     * @param entry - 给定的项
     * @return null，如果节点之前不存在给定的键，否则返回给定键之前关联的值
     */
    public V putEntry(Entry<K, V> entry) {
        SearchResult<V> result = searchKey(entry.getKey());
        if (result.isExist()) {
            V oldValue = entrys.get(result.getIndex()).getValue();
            entrys.get(result.getIndex()).setValue(entry.getValue());
            return oldValue;
        } else {
            insertEntry(entry, result.getIndex());
            return null;
        }
    }

    /**
     * 在该节点中插入给定的项， 该方法保证插入之后，其键值还是以非降序存放。
     * <p/>
     * 不过该方法的时间复杂度为O(t)。
     * <p/>
     * <b>注意：</b>B树中不允许键值重复。
     * 
     * @param entry - 给定的键值
     * @return true，如果插入成功，false，如果插入失败
     */
    public boolean insertEntry(Entry<K, V> entry) {
        SearchResult<V> result = searchKey(entry.getKey());
        if (result.isExist())
            return false;
        else {
            insertEntry(entry, result.getIndex());
            return true;
        }
    }

    /**
     * 在该节点中给定索引的位置插入给定的项， 你需要自己保证项插入了正确的位置。
     * 
     * @param key   - 给定的键值
     * @param index - 给定的索引
     */
    public void insertEntry(Entry<K, V> entry, int index) {
        /*
         * 通过新建一个ArrayList来实现插入真的很恶心，先这样吧 要是有类似C中的reallocate就好了。
         */
        List<Entry<K, V>> newEntrys = new ArrayList<Entry<K, V>>();
        int i = 0;
        // index = 0或者index = keys.size()都没有问题
        for (; i < index; ++i)
            newEntrys.add(entrys.get(i));
        newEntrys.add(entry);
        for (; i < entrys.size(); ++i)
            newEntrys.add(entrys.get(i));
        entrys.clear();
        entrys = newEntrys;
    }

    /**
     * 返回节点中给定索引的子节点。
     * <p/>
     * 你需要自己保证给定的索引是合法的。
     * 
     * @param index - 给定的索引
     * @return 给定索引对应的子节点
     */
    public BTreeNode<K, V> childAt(int index) {
        if (isLeaf())
            throw new UnsupportedOperationException("Leaf node doesn't have children.");
        return children.get(index);
    }

    /**
     * 将给定的子节点追加到该节点的末尾。
     * 
     * @param child - 给定的子节点
     */
    public void addChild(BTreeNode<K, V> child) {
        children.add(child);
    }

    /**
     * 删除该节点中给定索引位置的子节点。
     * </p>
     * 你需要自己保证给定的索引是合法的。
     * 
     * @param index - 给定的索引
     */
    public void removeChild(int index) {
        children.remove(index);
    }

    /**
     * 将给定的子节点插入到该节点中给定索引 的位置。
     * 
     * @param child - 给定的子节点
     * @param index - 子节点带插入的位置
     */
    public void insertChild(BTreeNode<K, V> child, int index) {
        List<BTreeNode<K, V>> newChildren = new ArrayList<BTreeNode<K, V>>();
        int i = 0;
        for (; i < index; ++i)
            newChildren.add(children.get(i));
        newChildren.add(child);
        for (; i < children.size(); ++i)
            newChildren.add(children.get(i));
        children = newChildren;
    }
    
    
    
    
    /**
     * B树节点中的键值对。
     * <p/>
     * B树的节点中存储的是键值对。 通过键访问值。
     * 
     * @param <K> - 键类型
     * @param <V> - 值类型
     */
    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + ":" + value;
        }
    }
}
