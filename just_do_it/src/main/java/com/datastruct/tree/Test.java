package com.datastruct.tree;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        // test.BinarySearchTree();
        test.AVLTree();
    }

    public void AVLTree() {
        AVLTree<String> avl = new AVLTree<String>();
        avl.insert(3, "a");
        avl.insert(2, "a");
        avl.insert(4, "a");
        avl.insert(5, "a");
        avl.insert(1, "a");
        avl.insert(6, "a");
        avl.insert(7, "a");
        avl.system();
        avl.delete(3);
        /*avl.insert(3, "a");
        avl.insert(2, "a");
        avl.insert(1, "a");
        avl.insert(4, "a");
        avl.insert(5, "a");
        avl.insert(6, "a");
        avl.insert(7, "a");
        avl.insert(10, "a");
        avl.insert(9, "a");
        avl.insert(8, "a");*/

        avl.system();
    }

    public void BinarySearchTree() {
        BinarySearchTree<String> b = new BinarySearchTree<String>();
        b.insert(62, "a");
        // b.insert(88, "d");
        // System.out.println(b.search(47));
        System.out.println(b.delete(62));
        // System.out.println(b.search(7));

        b.system();
    }

}
