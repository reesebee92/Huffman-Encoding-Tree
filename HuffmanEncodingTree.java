
/**
 * HuffmanEncodingTree
 * 
 * This class starts off by reading in a frequency table and using those
 * frequency values to create Nodes. Each Node will be used to build a
 * Huffman encoding tree or in other words a min-heap using a priority 
 * queue. Once the tree is complete it will be used to decode and encode
 * inputed text files and output the results to a file.
 * @version 1.0
 * @author sDantzler 
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class HuffmanEncodingTree {

   public static void main(String[] args) {

      // Check for command line arguments
      if (args.length != 4) {
         System.out.println(
               "Usage: java HuffmanEncodingTree [frequency input file pathname] [clear input file pathname] "
                     + "[encoded input file pathname][output file pathname]");
         System.exit(1);
      }

      // try with resources
      try (BufferedReader freqtbl = new BufferedReader(new FileReader(args[0]));
            BufferedReader cleartxt = new BufferedReader(
                  new FileReader(args[1]));
            BufferedReader encodedtxt = new BufferedReader(
                  new FileReader(args[2]));
            PrintWriter output = new PrintWriter(
                  new BufferedWriter(new FileWriter(args[3])))) {

         String line;
         int index = 0;
         int i = 0;
         int alphabet = 26;
         String[] charInput = new String[alphabet];
         int[] freqInput = new int[alphabet];
         PriorityQueue pq = new PriorityQueue((alphabet * 2) - 1);

         // assign the frequency table to the arrays
         while ((line = freqtbl.readLine()) != null) {

            String freqstr = "";
            String chStr = "";

            for(index = 0; index < line.length(); index++) {
               if (line.charAt(index) >= '0' && line.charAt(index) <= '9') {
                  freqstr = freqstr + line.charAt(index);
               }
               else if(line.charAt(index) >= 'A' && line.charAt(index) <= 'Z' ||
                     line.charAt(index) >= 'a' && line.charAt(index) <= 'z') {
                  chStr = chStr + line.charAt(index);
               }
            } // end for loop

            charInput[i] = chStr;
            freqInput[i] = Integer.parseInt(freqstr);
            i++;
         } // end while
         
         // create 26 Tree Nodes with frequency type assignments and
         // add to the PriorityQueue pq
         for (i = 0; i < alphabet; i++) {
            Node n = new Node(charInput[i].toUpperCase(), freqInput[i]);
            pq.insert(n);
         } // end for loop

         // Build the Huffman Tree
         Node root = null;

         while (pq.getSize() > 1) {
            // Remove the first two smallest Nodes
            Node x = pq.peek();
            pq.remove();

            Node y = pq.peek();
            pq.remove();

            int zFreq = x.frequency + y.frequency;
            String zStr = reorder(x.character + y.character);

            // Assign the combined values to a new Node z
            Node z = new Node(zStr, zFreq, x, y);

            root = z;
            pq.insert(z);
         }

         // Output the preorder traversal
         output.println(
               "This is the preorder traversal of the Huffman Encoding Tree:\n");
         preorder(pq.peek(), output);

         // Decode the Encoded file
         output.println("\n");
         output.println("This is the encoded text file decoded:\n");
         int[] count = { 1 }; // Format the output file
         while ((line = encodedtxt.readLine()) != null) {
            output.print(count[0]++ + ". "); // Formatting
            decode(line, pq.peek(), output);
         } // end while loop

         // Encode the Clear file
         output.println("\n");
         output.println("This is the clear text file encoded:\n");
         count[0] = 1; // Format the output file
         while ((line = cleartxt.readLine()) != null) {
            String results = "";
            output.print(count[0]++ + ". "); // Formatting
            char[] ch = line.toCharArray();
            for (i = 0; i < line.length(); i++) {
               char c = Character.toUpperCase(ch[i]); // Make all characters
                                                      // UpperCase
               String chStr = Character.toString(c); // Turn all characters into
                                                     // a String
               encode(chStr, pq.peek(), results, output);
            }
            output.println("\n");
         } // end while loop

         output.println(
               "Note: If your output is blank or incorrect please check your input again!");

      } catch (IOException ioe) {
         System.out.println("I/O Error: " + ioe);
      }
   }// end main method

   /**
    * reorder method
    * 
    * This method rearranges the merged characters from two Nodes to make the
    * Node character attribute more readable
    * 
    * @param str
    * @return retString
    */
   public static String reorder(String str) {
      String retString;

      char[] charArray = str.toCharArray();

      for (int i = 0; i < str.length(); i++) {
         for (int j = i + 1; j < str.length(); j++) {
            if (charArray[j] < charArray[i]) {
               char temp = charArray[i];
               charArray[i] = charArray[j];
               charArray[j] = temp;
            }
         }
      } // end for loop

      retString = String.valueOf(charArray);
      return retString;
   }// end reorder method

   /**
    * preorder method
    * 
    * This method traverses a specified Node in a preorder fashion
    * 
    * @param root, output
    */
   public static void preorder(Node root, PrintWriter output) {
      if (root == null)
         return;

      // print value of Node
      output.print(root.value + " ");

      // traverse the left subtree in preorder
      preorder(root.left, output);

      // traverse the right subtree in preorder
      preorder(root.right, output);
   }// end preorder method

   /**
    * decode method
    * 
    * This method decodes an encrypted file of 0's and 1's
    * 
    * @param s, root, output
    */
   public static void decode(String s, Node root, PrintWriter output) {

      if (root == null) {
         return;
      }

      String results = "";
      char[] encodedArray = s.toCharArray();
      char ch;
      Node curr = root;

      // loop through the encoded String array
      for (int i = 0; i < encodedArray.length; i++) {
         ch = encodedArray[i];
         if (ch == '0' && curr.left != null) {
            curr = curr.left;
         } else if (ch == '1' && curr.right != null) {
            curr = curr.right;
         }
         if (curr.left == null && curr.right == null) {
            results = results + curr.character;
            curr = root;
         }
      } // end for loop

      output.println(results);
   }// end decode method

   /**
    * encode method
    * 
    * This method encodes a clear file into 0's and 1's
    * 
    * @param ch, root, results, output
    */
   public static void encode(String ch, Node root, String results,
         PrintWriter output) {
      if (root == null) {
         return;
      }

      // if the root is a leaf node
      if (root.left == null && root.right == null) {
         output.print(results);
      }
      // if root is not a leaf do recursion
      else {
         if (root.left != null && root.left.character.contains(ch)) {
            encode(ch, root.left, results + "0", output);
         }
         if (root.right != null && root.right.character.contains(ch)) {
            encode(ch, root.right, results + "1", output);
         }
      }
   }// end encode method

}// end class HuffmanEncodingTree
