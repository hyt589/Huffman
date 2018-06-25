package com.company;

import java.io.Serializable;

public class HuffmanNode implements Serializable {

    private char ch;
    private int freq;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }

    public HuffmanNode() {}

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }


    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public boolean isLeaf() {
        if (this.left == null && this.right == null) {
            return true;
        }
        return false;
    }
}
