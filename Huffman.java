package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Set;

public class Huffman {
    public static void main(String[] args) throws Exception{
        if (args.length == 0) {
            System.out.println("Please enter a filename");
            return;
        }

        if (args.length == 1) {
            String filename = args[0];
            HuffmanTree tree = new HuffmanTree(filename);
            tree.encode(tree.getRoot());
            BitSet bs = tree.compress();

            //-------------testing codes----------------
            System.out.println("\n\n");
            for (int i = 0; i < bs.size(); i++) {
                if (bs.get(i))
                    System.out.print(1);
                else
                    System.out.print(0);
            }

            System.out.print("\n\n");
            System.out.print(tree.decompress(bs));
            System.out.print("\n\n" + tree.getMap().toString());
            //----------end of testing codes------------

            BitSet bsTree = new BitSet();
            writeTree(tree.getRoot(), bsTree);
            bsTree = bsTree.get(0, bsTree.length());
            byte[] binTree = bsTree.toByteArray();


            byte[] buf = bs.toByteArray();
            File file = new File(filename + "_huffman");
            boolean created = file.createNewFile();
            if (created) {
                OutputStream outputStream = new FileOutputStream(filename + "_huffman");
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                try {
                    //outputStream.write(binTree);
                    outputStream.write(buf);
                } finally {
                    outputStream.close();
                }
            } else {
                System.err.println("File name conflict");
            }

        } else if (args.length == 2) {
            if (args[0] == "-d") {
                String filename = args[2];
                BitSet content = BitSet.valueOf(Files.readAllBytes(Paths.get(filename)));
                BitSet binTree = BitSet.valueOf(Files.readAllBytes(Paths.get(filename + "-tree")));
            }
        }
    }

    private static void writeTree(HuffmanNode node, BitSet bs){
        if (node.isLeaf()) {
            bs.set(bs.length(), false);
        } else {
            bs.set(bs.length(), true);
            writeTree(node.getLeft(), bs);
            writeTree(node.getRight(), bs);
        }
    }
}
