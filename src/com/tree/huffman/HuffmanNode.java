package com.tree.huffman;

class HuffmanNode extends HuffmanTree {
    private final HuffmanTree left, right; // subtrees
 
    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.getFrequency() + r.getFrequency());
        left = l;
        right = r;
    }

    public HuffmanTree getLeft() {
        return left;
    }

    public HuffmanTree getRight() {
        return right;
    }
}
