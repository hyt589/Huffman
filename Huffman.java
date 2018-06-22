package com.company;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Set;

public class Huffman {
    public static void main(String[] args) throws Exception{
        if (args.length == 0) {
            System.out.println("Please enter a filename");
            return;
        }

        if (args.length >= 1) {
            String filename = args[0];
            HuffmanTree tree = new HuffmanTree(filename);
            tree.encode(tree.getRoot());
            Set<Character> keySet = tree.getMap().keySet();
            BitSet bs = tree.compress();

            System.out.println("\n\n");
            for (int i = 0; i < bs.size(); i++) {
                if (bs.get(i))
                    System.out.print(1);
                else
                    System.out.print(0);
            }

            System.out.print("\n\n");
            System.out.print(tree.decompress(bs));


        }
    }
}
