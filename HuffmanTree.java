package com.company;

import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HuffmanTree {

    private HuffmanNode root;
    private HashMap<Character, String> keyMap = new HashMap<>();
    String text;

    private static Comparator<HuffmanNode>  nodeComparator = new Comparator<HuffmanNode>() {
        @Override
        public int compare(HuffmanNode o1, HuffmanNode o2) {
            if (o1.getFreq() > o2.getFreq()) {
                return 1;
            } else if (o1.getFreq() < o2.getFreq()) {
                return -1;
            }
            return 0;
        }
    };



    public HuffmanNode getRoot() {
        return root;
    }

    public HuffmanTree(String filename) throws Exception {

        text = new String(Files.readAllBytes(Paths.get(filename)));
        Queue<HuffmanNode> nodes = new PriorityQueue<>(30, nodeComparator);

        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            if (characters.contains(new Character(text.charAt(i)))) {
                continue;
            } else {
                characters.add(new Character(text.charAt(i)));
            }
        }


        for (Character c : characters
                ) {
            int freq = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == c) {
                    freq++;
                }
            }
            nodes.add(new HuffmanNode(c, freq));
            //System.out.println(c + " " + freq);
        }

        //System.out.println(nodes.peek().getCh());

        if (nodes.size() == 0) {
            root = null;
        }
        else if (nodes.size() == 1) {
            root = nodes.poll();
        }
        else{

            while (nodes.size() > 1) {

                HuffmanNode leftChild = nodes.poll();
                HuffmanNode rightChild = nodes.poll();
                HuffmanNode parent = new HuffmanNode();
                parent.setLeft(leftChild);
                parent.setRight(rightChild);
                parent.setFreq(leftChild.getFreq() + rightChild.getFreq());
                nodes.add(parent);

            }
            root = nodes.poll();
            //System.out.println(root.getRight().isLeaf());
        }
    }

    private void encodeHelper(HuffmanNode node, String aux) {

        if (!node.isLeaf()) {
            boolean goLeft = false;
            if (node.getLeft() != null) {
                aux = aux + "0";
                encodeHelper(node.getLeft(), aux);
                goLeft = true;
            }
            if (node.getRight() != null){
                if (goLeft) {
                    aux = aux.substring(0, aux.length() - 1);
                }
                aux = aux + "1";
                encodeHelper(node.getRight(), aux);
            }
        } else {
            //building a character-code pair and add to keyMap
            keyMap.put(new Character(node.getCh()), aux);
            System.out.println(node.getCh() + " " + aux);
        }
    }

    public void encode(HuffmanNode node) {
        encodeHelper(node, "");
    }

    public BitSet compress() {
        String textOutput = "";
        for (int i = 0; i < text.length(); i++) {
            textOutput = textOutput + keyMap.get(text.charAt(i));
        }
        BitSet output = new BitSet();
        for (int i = 0; i < textOutput.length(); i++) {
            if (textOutput.charAt(i) == '1')
                output.set(i, true);
            else
                output.set(i, false);
        }
        return output;
    }

    public String decompress(BitSet bin){
        String o = "";
        HuffmanNode ptr = root;
        for (int i = 0; i < bin.size(); i++) {
            if (!ptr.isLeaf()) {
                if (bin.get(i)) {
                    ptr = ptr.getRight();
                } else {
                    ptr = ptr.getLeft();
                }
            } else {
                o = o + ptr.getCh();
                ptr = root;
                if (bin.get(i)) {
                    ptr = ptr.getRight();
                } else {
                    ptr = ptr.getLeft();
                }
            }
        }
        return o;
    }

    public Map getMap() {
        return keyMap;
    }




}
