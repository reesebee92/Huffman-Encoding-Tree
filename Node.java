
/**
 * Tree Node
 * @version 1.0
 * @author sDantzler 
 * */

import java.util.Comparator;

public class Node implements Comparator<Node> {

   // instance variables
   String character, value;
   int frequency;
   Node left;
   Node right;

   Node() {
      this.character = "";
      this.frequency = -1;
      this.left = null;
      this.right = null;
      this.value = this.character + " " + this.frequency;
   }// empty constructor

   Node(String ch, int freq) {
      this.character = ch;
      this.frequency = freq;
      this.left = null;
      this.right = null;
      this.value = ch + " " + freq;
   }// parameterized constructor

   Node(String ch, int freq, Node x, Node y) {
      this.character = ch;
      this.frequency = freq;
      this.left = x;
      this.right = y;
      this.value = ch + ": " + freq;
   }// parameterized constructor

   /**
    * compare method
    * 
    * This method compares 2 nodes and returns the difference in frequency
    * @param x, y
    */
   public int compare(Node x, Node y) {
      return x.frequency - y.frequency;
   }// end compare method

}// end Node class
