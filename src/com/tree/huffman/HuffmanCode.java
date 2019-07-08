package com.tree.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCode {
    private final static Map<Character, String> codeMap = new HashMap<>();
    
    // 输入是一个频率数组, 字符编码索引
    //如果频率相等，会怎样？
    public static HuffmanTree buildTree(int[] charFreqs) {
        //https://www.cnblogs.com/CarpenterLee/p/5488070.html
        //堆排序
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // 最初, 都是一个叶子节点，每一个都有一个非空的字符
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
 
        assert trees.size() > 0;
        // 循环直到只剩下一棵树
        while (trees.size() > 1) {
            // 频率最小的两个节点
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
 
            // 放入新节点并重新插入队列
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }
 
    /**
     * @param tree 树的叶子结点都是HuffmanLeaf，上层节点都是HuffmanNode
     * @param prefix 节点的编码
     */
    public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
        if (tree == null) {
            return;
        }
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            codeMap.put(leaf.getValue(), prefix.toString());
            // 打印这个叶子节点的字符, 频率, 编码
            System.out.println(leaf.getValue() + "\t" + leaf.getFrequency() + "\t" + prefix);
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
 
            // 左遍历
            prefix.append('0');
            printCodes(node.getLeft(), prefix);
            prefix.deleteCharAt(prefix.length()-1);//左子树的叶子节点已经遍历，需要回退到上一层，所以需要删除一个路径
 
            // 右遍历
            prefix.append('1');
            printCodes(node.getRight(), prefix);
            prefix.deleteCharAt(prefix.length()-1);//同理
        }
    }
    
    //编码原始字符串
    static String codeTarget(String target) {
        char[] values = target.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : values) {
            String code = codeMap.get(c);
            if (code != null) {
                builder.append(code);
            }
        }
        System.out.println("编码之后："+builder.toString());
        return builder.toString();
    }
    
    //解码
    //搜索哈夫曼树，直到发现一个叶节点
    static void decode(HuffmanTree tree, String code) {
        if (!(tree instanceof HuffmanNode)) {
            return;
        }
        char[] c = code.toCharArray();
        StringBuilder builder = new StringBuilder();
        
        HuffmanTree temp = tree;
        for (int i = 0; i < c.length; i++) {
            char p = c[i];//注意下面的循环可能会无意的将i值后移了一位
            if (temp instanceof HuffmanNode) {
                HuffmanNode tempNode = (HuffmanNode) temp;
                if (p == '0') {//0对应着左子树
                    temp = tempNode.getLeft();
                }else {//1对应着右子树
                    temp = tempNode.getRight();
                }
            }
            
            if (temp instanceof HuffmanLeaf) {//叶子节点
                HuffmanLeaf leaf = (HuffmanLeaf) temp;
                builder.append(leaf.getValue());
                temp = (HuffmanNode) tree;//一直从根节点按照code的值移动
            }
        }
        System.out.println("解码后的："+ builder.toString());
    }
 
    public static void main(String[] args) {
        String test = "this is an example for huffman encoding";
 
        // 假设所有的字符都小于 256, 为简单起见
        int[] charFreqs = new int[256];
        // 读取每个字符并记录频率
        for (char c : test.toCharArray())
            charFreqs[c]++;
 
        // build tree
        HuffmanTree tree = buildTree(charFreqs);
 
        // print out results
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        printCodes(tree, new StringBuffer());
        
        codeTarget(test);
        
        //0000100011100001011111000010111100101111110110011110010100101011100011011111110101011010011100011000011011101010010010111111011011001100101000001100011101010
        String code = "0000100011100001011111000010111100101111110110011110010100101011100011011111110101011010011100011000011011101010010010111111011011001100101000001100011101010";
        //解码
        decode(tree, code);
    }
}
