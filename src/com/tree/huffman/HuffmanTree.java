package com.tree.huffman;

abstract class HuffmanTree implements Comparable<HuffmanTree> {
    private final int frequency; // 频率
    
    public HuffmanTree(int freq) { frequency = freq; }
 
    // 频率比较，从小到大
    @Override
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }

    public int getFrequency() {
        return frequency;
    }
}
