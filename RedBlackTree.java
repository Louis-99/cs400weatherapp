// --== CS400 File Header Information ==--
// Name: Jiahe Jin
// Email: jjin82@wisc.edu
// Team: JB
// Role: Back End Developer
// TA: Harper
// Lecturer: Florian Heimerl
// Notes to Grader: All of our classes implements the java.io.Serializable to serialize the file into
// caches to store the data for next time of use.
import java.util.LinkedList;

/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes within a binary search tree.  You can use this class' insert
 * method to build a binary search tree, and its toString method to display
 * the level order (breadth first) traversal of values in that tree.
 *
 * @author Jiahe Jin
 */
public class RedBlackTree<T extends Comparable<T>> implements java.io.Serializable {

    protected Node<T> root; // reference to root node of tree, null when empty

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree.  After
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     *
     * @param data to be added into this binary search tree
     * @throws NullPointerException     when the provided data argument is null
     * @throws IllegalArgumentException when the tree already contains data
     */
    public void insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if (data == null)
            throw new NullPointerException("This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (root == null) {
            root = newNode;
        } // add first node to an empty tree
        else
            insertHelper(newNode, root); // recursively insert into subtree
        this.root.isBlack = true; // always set the root node of RBT to be black
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     *
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the
     *                newNode should be inserted as a descenedent beneath
     * @throws IllegalArgumentException when the newNode and subtree contain
     *                                  equal data references (as defined by Comparable.compareTo())
     */
    private void insertHelper(Node<T> newNode, Node<T> subtree) {
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if (compare == 0)
            throw new IllegalArgumentException("This RedBlackTree already contains that value.");

            // store newNode within left subtree of subtree
        else if (compare < 0) {
            if (subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                this.enforceRBTreePropertiesAfterInsert(newNode);
                // otherwise continue recursive search for location to insert
            } else
                insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if (subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                this.enforceRBTreePropertiesAfterInsert(newNode);
                // otherwise continue recursive search for location to insert
            } else {
                insertHelper(newNode, subtree.rightChild);
            }
        }
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     *
     * @return string containing the values of this tree in level order
     */
    @Override public String toString() {
        return root.toString();
    }

    /**
     * Performs the rotation operation on the provided nodes within this BST.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation (sometimes called a left-right
     * rotation).  When the provided child is a rightChild of the provided
     * parent, this method will perform a left rotation (sometimes called a
     * right-left rotation).  When the provided nodes are not related in one
     * of these ways, this method will throw an IllegalArgumentException.
     *
     * @param child  is the node being rotated from child to parent position
     *               (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *               (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *                                  node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // When child is not related to parent, throwing exception
        if (parent.leftChild != child && parent.rightChild != child) {
            throw new IllegalArgumentException(
                "Sorry, the provided child is not related to the provided parent.");
        }
        Node<T> Grandparent = parent.parent;
        if (child.isLeftChild()) { // Right Rotation
            // Set the new left child of parent
            parent.leftChild = child.rightChild;
            if (child.rightChild != null) {
                child.rightChild.parent = parent;
            }
            if (parent == root) { // When the parent is root
                root = child;                       // Child to Root
                root.leftChild = child.leftChild;   // Left Root Setting
                root.rightChild = parent;           // Right Root Setting
            }
            child.rightChild = parent;              // New Right of Child
            parent.parent = child;                  // New Parent of Parent

        } else if (!child.isLeftChild()) { // Left Rotation
            // Set the new right child of parent
            parent.rightChild = child.leftChild;
            if (child.leftChild != null) {
                child.leftChild.parent = parent;
            }
            if (parent == root) { // When the parent is root
                root = child;                         // Child to Root
                root.rightChild = child.rightChild;   // Right Root Setting
                root.leftChild = parent;              // Left Root Setting
            }
            child.leftChild = parent;               // New Left of Child
            parent.parent = child;                  // New Parent of Parent
        }
        if (Grandparent != null) {
            // Set the new parent of child
            child.parent = Grandparent;
            if (Grandparent.leftChild == parent) {
                Grandparent.leftChild = child;
            } else {
                Grandparent.rightChild = child;
            }
        }

    }

    /**
     * It takes a reference to a newly added red node as its only parameter.
     * Note that this method may also be called recursively, in which case
     * the input parameter may reference a different red node in the tree
     * that potentially has a red parent node. The job of this method is to
     * resolve red child under red parent red black tree property violations
     * that are introduced by inserting new nodes into a red black tree.
     * While doing so, all other red black tree properties must also be preserved.
     *
     * @param redNode the reference to a newly added red node which might also has a red parent node.
     */
    @SuppressWarnings("unchecked") private void enforceRBTreePropertiesAfterInsert(
        Node<T> redNode) {
        // Only allowed when the added node is red
        if (!redNode.isBlack) {
            // Only allowed when the added node is under red property violation
            if (!redNode.parent.isBlack) {
                // when the parent of added node is the left child
                if (redNode.parent.isLeftChild()) {
                    // only allowed when the added node is between Grandparent and parent
                    // and the sibling is not the red node
                    if (!redNode.isLeftChild() && (redNode.parent.parent.rightChild == null
                        || redNode.parent.parent.rightChild.isBlack)) {
                        Node parentTemplate = redNode.parent;
                        this.rotate(redNode, redNode.parent); // smaller left rotation
                        redNode = parentTemplate;
                    }
                    // Assuming the sibling as black when it is null
                    if (redNode.parent.parent.rightChild == null) {
                        this.rotate(redNode.parent, redNode.parent.parent); // right rotation
                        redNode.isBlack = false;                   // set added node as red
                        redNode.parent.isBlack = true;             // set its parent as black
                        redNode.parent.rightChild.isBlack = false; // set parent's rightChild as red
                    } else { // the color of sibling is given
                        // set the rightChild as the sibling
                        Node<T> sibling = redNode.parent.parent.rightChild;
                        // if the sibling is black
                        if (sibling.isBlack) {
                            this.rotate(redNode.parent, redNode.parent.parent); // rotation
                            redNode.isBlack = false;                   // set added node as red
                            redNode.parent.isBlack = true;             // set its parent as black
                            redNode.parent.rightChild.isBlack =
                                false; // set its parent's rightChild as red
                        } else {// if the sibling is red
                            sibling.isBlack = true;         // set sibling as black
                            redNode.parent.isBlack = true;  // set parent as black
                            redNode.parent.parent.isBlack = false; // set grandparent as red
                            // only allowed when the parent is not the root
                            if (redNode.parent.equals(root)) {
                                // recursively called, because it might cause the red property violation
                                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);
                            }
                        }
                    }
                } else { // When the parent of added node is right child
                    // only allowed when the added node is between Grandparent and parent
                    // and the sibling is not the red node
                    if (redNode.isLeftChild() && (redNode.parent.parent.leftChild == null
                        || redNode.parent.parent.leftChild.isBlack)) {
                        Node parentTemplate = redNode.parent;
                        this.rotate(redNode, redNode.parent); // smaller right rotation
                        redNode = parentTemplate;
                    }
                    // Assuming the sibling as black when it is null
                    if (redNode.parent.parent.leftChild == null) {
                        this.rotate(redNode.parent, redNode.parent.parent); // left rotation
                        redNode.isBlack = false;                   // set added node as red
                        redNode.parent.isBlack = true;             // set its parent as black
                        redNode.parent.leftChild.isBlack = false; // set parent's leftChild as red
                    } else { // the color of sibling is given
                        // set the leftChild as the sibling
                        Node<T> sibling = redNode.parent.parent.leftChild;
                        // if the sibling is black
                        if (sibling.isBlack) {
                            this.rotate(redNode.parent, redNode.parent.parent); // rotation
                            redNode.isBlack = false;                   // set added node as red
                            redNode.parent.isBlack = true;             // set its parent as black
                            redNode.parent.leftChild.isBlack =
                                false; // set its parent's leftChild as red
                        } else {// if the sibling is red
                            sibling.isBlack = true;         // set sibling as black
                            redNode.parent.isBlack = true;  // set parent as black
                            redNode.parent.parent.isBlack = false; // set grandparent as red
                            // only allowed when the parent is not the root
                            if (redNode.parent.equals(root)) {
                                // recursively called, because it might cause the red property violation
                                enforceRBTreePropertiesAfterInsert(redNode.parent.parent);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always be maintained.
     */
    protected static class Node<T> implements java.io.Serializable {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild;
        public Node<T> rightChild;
        public boolean isBlack;

        public Node(T data) {
            this.data = data;
            this.isBlack = false; // newly instantiated node is red
        }

        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }

        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node.  The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         *
         * @return string containing the values of this tree in level order
         */
        @Override public String toString() { // display subtree in order traversal
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while (!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if (next.leftChild != null)
                    q.add(next.leftChild);
                if (next.rightChild != null)
                    q.add(next.rightChild);
                output += next.data.toString();
                if (!q.isEmpty())
                    output += ", ";
            }
            return output + "]";
        }
    }

}
