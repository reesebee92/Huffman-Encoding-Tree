/**
 * A Priority Queue for Nodes
 * 
 * @version 1.0
 * @author sDantzler
 */

public class PriorityQueue {

   private int pqMax;
   private Node[] pqArray;
   private int size;

   /**
    * PriorityQueue constructor will create a new pq of Nodes
    * 
    * @param the arraySize used to determine the max number of elements the
    *            stack can hold
    */
   public PriorityQueue(int arraySize) {
      pqMax = arraySize;
      this.pqArray = new Node[pqMax];
      this.size = 0;
   }// end constructor

   /**
    * Checks to see if the pq is empty
    * 
    * @return true or false
    */
   public boolean isEmpty() {
      if (size == 0) {
         return true;
      } else {
         return false;
      }
   }// end isEmpty method

   /**
    * Checks to see if the pq is full
    * 
    * @return true or false
    */
   public boolean isFull() {
      if (size == pqMax - 1) {
         return true;
      } else {
         return false;
      }
   }// end isFull method

   /**
    * @return the current size of the pq
    */
   public int getSize() {
      return size;
   }// end getSize method

   /**
    * Inserts a Node into the pq according to Nodes within the pq if any
    * 
    * @param Node n
    */
   public void insert(Node n) throws ArrayIndexOutOfBoundsException {

      if (isFull()) {
         throw new ArrayIndexOutOfBoundsException(
               "The priority queue is full!");
      }

      // if pq is empty
      if (size == 0) {
         size++;
         pqArray[0] = n;
         return;
      }

      Node temp = n;
      Node next;
      // Compare all elements in pq with new Node n
      for (int i = 0; i < size; i++) {
         // if new Node is equal to current Node
         if ((n.frequency - pqArray[i].frequency) == 0) {
            // if new Node or current Node has more than one character
            if (count(n, pqArray[i])) {
               // simplicity tie breaker
               if (simpleTie(n, pqArray[i]) == n) {
                  temp = pqArray[i];
                  pqArray[i] = n;

                  while (i < size - 1) {
                     next = pqArray[i + 1];
                     pqArray[i + 1] = temp;
                     temp = next;
                     i++;
                  }
               }
            } // end if

            // if new Node's character is less than current Node shift
            if (stringValue(n, pqArray[i])) {
               temp = pqArray[i];
               pqArray[i] = n;

               while (i < size - 1) {
                  next = pqArray[i + 1];
                  pqArray[i + 1] = temp;
                  temp = next;
                  i++;
               }
            } // end if
         } // end if
           // otherwise if new Node is less than current Node shift
         else if ((n.frequency - pqArray[i].frequency) < 0) {
            temp = pqArray[i];
            pqArray[i] = n;

            while (i < size - 1) {
               next = pqArray[i + 1];
               pqArray[i + 1] = temp;
               temp = next;
               i++;
            }
            break;
         } // end else if
      } // end for loop

      // if new Node is greater than all elements place at the end
      pqArray[size] = temp;
      size++;

   }// end insert method

   /**
    * This method determines if a Node has more than one character.
    * 
    * @param Node x, y
    * @return true or false
    */
   public boolean count(Node x, Node y) {
      String xStr = x.character;
      String yStr = y.character;

      int cX = 0, cY = 0;

      for (int i = 0; i < xStr.length(); i++) {
         cX++;
      } // end for loop

      for (int i = 0; i < yStr.length(); i++) {
         cY++;
      } // end for loop

      if ((cX > 1) || (cY > 1)) {
         return true;
      } else {
         return false;
      }
   }// end count method

   /**
    * This method determines a simple tie breaker for any Nodes with more than
    * one character and the same frequency
    * 
    * @param Node x, y
    * @return smallest Node (highest priority)
    */
   public Node simpleTie(Node x, Node y) {
      String xStr = x.character;
      String yStr = y.character;

      int cX = 0, cY = 0;

      for (int i = 0; i < xStr.length(); i++) {
         cX++;
      } // end for loop

      for (int i = 0; i < yStr.length(); i++) {
         cY++;
      } // end for loop

      if (cX < cY) {
         return x;
      } else {
         return y;
      }
   }// end simpleTie method

   /**
    * This method determines if one Node's character String is less than the
    * other Node
    * 
    * @param Node x, y
    * @return true or false
    */
   public boolean stringValue(Node x, Node y) {

      int xValue = 0;
      int yValue = 0;
      char[] xChar = x.character.toCharArray();
      char[] yChar = y.character.toCharArray();

      for (int i = 0; i < xChar.length; i++) {
         xValue = xValue + (int) xChar[i];
      } // end for loop

      for (int i = 0; i < yChar.length; i++) {
         yValue = yValue + (int) xChar[i];
      } // end for loop

      if (xValue < yValue) {
         return true;
      } else {
         return false;
      }

   }// end stringValue method

   /**
    * Removes a Node from the pq
    * 
    * @return retNode
    */
   public Node remove() throws ArrayIndexOutOfBoundsException {
      if (isEmpty()) {
         throw new ArrayIndexOutOfBoundsException(
               "The priority queue is empty!");
      }
      // Positioning the Node w the highest priority first
      Node retNode = pqArray[0];

      // Shift the priority values down
      for (int i = 1; i < size; i++) {
         pqArray[i - 1] = pqArray[i];
      }

      // Free up the slot in the pqArray
      pqArray[size - 1] = null;
      size--;
      return retNode;
   }// end remove method

   /**
    * Checks if a Node is in the pq
    * 
    * @param Node n
    * @return true or false
    */
   public boolean contains(Node n) {

      if (isEmpty()) {
         return false;
      }
      // Check if the Node matches any Nodes in the array
      for (int i = 0; i < size; i++) {
         if (pqArray[i] == n) {
            return true;
         }
      }
      // Else no Node is found
      return false;
   }// end contains method

   /**
    * Shows highest priority Node in the pq
    * 
    * @return top Node
    */
   public Node peek() throws ArrayIndexOutOfBoundsException {
      if (isEmpty()) {
         throw new ArrayIndexOutOfBoundsException(
               "The priority queue is empty!");
      }
      return pqArray[0];

   }// end peek method

   /**
    * Displays all the Nodes in the pq
    */
   public void display() {

      for (int i = 0; i < size; i++) {
         System.out.print(pqArray[i].value + " ");
      }
      System.out.println();
   }// end display method

}// end class PriorityQueue
