package com.tree.huffman;

class HuffmanLeaf extends HuffmanTree {
    private final char value; // 这个叶子代表的字符
 
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }

    public char getValue() {
        return value;
    }
}
