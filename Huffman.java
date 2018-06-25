package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
            //System.out.print(tree.decompress(bs));
            //System.out.print("\n\n" + tree.getMap().toString());
            //----------end of testing codes------------




            byte[] buf = bs.toByteArray();
            File file = new File(filename + "_huffman");
            boolean created = file.createNewFile();
            if (created) {
                OutputStream outputStream = new FileOutputStream(filename + "_huffman");
                ObjectOutputStream treeWriter = new ObjectOutputStream(outputStream);
                try {
                    treeWriter.writeObject(tree);
                    outputStream.write(buf);
                } finally {
                    outputStream.close();
                    treeWriter.close();
                }
/*  uncomment for testing
                //testing------------
                BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(filename + "_huffman"));
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    HuffmanTree readtree = (HuffmanTree) objectInputStream.readObject();
                    File comp = new File(filename + "_huffman");
                    byte[] buff = new byte[(int)comp.length()];
                    fileInputStream.read(buff);
                    BitSet bss = BitSet.valueOf(buff);
                    String str = readtree.decompress(bss);
                    System.out.print(str);
                }finally {
                    fileInputStream.close();
                    objectInputStream.close();
                }
                //end testing-------
*/

            } else {
                System.err.println("\nFile name conflict");
            }

        } else if (args.length == 2) {
            if (args[0].equals("-d")) {
                String filename = args[1];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
                ObjectInputStream ois = new ObjectInputStream(bis);
                HuffmanTree tree;
                try {
                    tree = (HuffmanTree)ois.readObject();
                    File comp = new File(filename);
                    File decomp = new File(filename + "_d");
                    boolean created = decomp.createNewFile();
                    byte[] input = new byte[(int)comp.length()];
                    bis.read(input);
                    BitSet bs = BitSet.valueOf(input);
                    String text = tree.decompress(bs);
                    byte[] buf = text.getBytes(Charset.forName("UTF-8"));
                    if (created) {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filename + "_d"));
                        try {
                            bos.write(buf);
                        } finally {
                            bos.close();
                            System.out.println("\nDecompressed");
                        }
                    } else {
                        System.err.print("\n\nFile Name Conflict\n" + text);
                    }
                }finally {
                    bis.close();
                    ois.close();
                }
            }
        }
    }


}
