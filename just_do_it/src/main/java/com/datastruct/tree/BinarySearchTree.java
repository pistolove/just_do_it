package com.datastruct.tree;

import java.util.LinkedList;
import java.util.List;

public class BinarySearchTree<T> {

    protected Node<T> root;

    public static class Node<T> {
        public Node<T> leftChild = null;
        public Node<T> rightChild = null;
        public Node<T> parent = null;
        public int key;
        public T value;
        public int height;

        public Node(int key, T value) {
            this.key = key;
            this.value = value;
            this.height = 0;
        }

        /**
         * 重新设置节点的高度
         * @return
         */
        public void resetHeight() {
            this.height = getNewHeight(this);
        }

        /**
         * 获取平衡因子 -1 代表左子树比右子树深1层 0代表相同 1代表左子树比右子树浅1层
         * @return
         */
        public int getBalanceFactor() {
            return this.getHeight(this.rightChild) - this.getHeight(this.leftChild);
        }

        /**
         * 计算树的高度
         * @return
         */
        private int getHeight(Node<T> node) {
            if (null == node) {// 节点为空时，高度为-1
                return -1;
            }
            return node.height;
        }

        /**
         * 重新计算一个节点的新高度
         * @param node
         * @return
         */
        public static <T> int getNewHeight(Node<T> node) {
            if (node != null) {
                int height = -1;
                if (node.leftChild == null && node.rightChild == null) {
                    height = -1;
                } else if (node.leftChild == null && node.rightChild != null) {
                    height = node.rightChild.height;
                } else if (node.leftChild != null && node.rightChild == null) {
                    height = node.leftChild.height;
                } else {
                    height = node.leftChild.height > node.rightChild.height ? node.leftChild.height
                            : node.rightChild.height;
                }
                height++;// 将子树的高度+1
                return height;
            }
            return -1;
        }
    }

    public void system() {
        List<Node<T>> temp = new LinkedList<Node<T>>();
        temp.add(this.root);
        this.system(temp);
    }

    private void system(List<Node<T>> list) {
        if (list != null && list.size() > 0) {
            List<Node<T>> temp = new LinkedList<Node<T>>();
            for (Node<T> node : list) {
                if (node != null) {
                    System.out.print(node.key + " ");
                    if (node.leftChild != null) {
                        temp.add(node.leftChild);
                    }
                    if (node.rightChild != null) {
                        temp.add(node.rightChild);
                    }
                }
            }
            System.out.println();
            this.system(temp);
        }

    }

    /**
     * 记录被删除节点的值，调用递归方法即可
     * @param key
     * @return
     */
    public T delete(int key) {
        Node<T> deleted = this.search(key, this.root);
        if (deleted == null) {
            return null;
        }
        T result = deleted.value;
        this.delete(deleted);
        return result;
    }

    /**
     * 删除思想 deleted为要删除的节点
     * 1.当为叶结点时，将deleted的父节点的指针 与 deleted指向父节点的指针 置空
     * 2.当左结点不为空，右结点为空时，将deleted的父节点的指针指向左结点，并将左结点的父结点指向deleted的父节点
     * 3.当右结点不为空，左结点为空时，将deleted的父节点的指针指向右结点，并将右结点的父结点指向deleted的父节点
     * 4.左右结点均不为空，将右结点的key与值上移到deleted位置，并递归调用将该右结点删除
     * @param deleted
     */
    protected void delete(Node<T> deleted) {
        if (deleted.leftChild == null && deleted.rightChild == null) {
            if (this.root != deleted) {
                if (deleted.parent.leftChild == deleted) {
                    deleted.parent.leftChild = null;
                } else {
                    deleted.parent.rightChild = null;
                }
            } else {
                this.root = null;
            }
            deleted.parent = null;
        } else if (deleted.leftChild != null && deleted.rightChild == null) {
            if (this.root != deleted) {
                if (deleted.parent.leftChild == deleted) {
                    deleted.parent.leftChild = deleted.leftChild;
                } else {
                    deleted.parent.rightChild = deleted.leftChild;
                }
            } else {
                this.root = deleted.leftChild;
            }
            deleted.leftChild.parent = deleted.parent;
            deleted.leftChild = null;
            deleted.parent = null;
        } else if (deleted.leftChild == null && deleted.rightChild != null) {
            if (this.root != deleted) {
                if (deleted.parent.leftChild == deleted) {
                    deleted.parent.leftChild = deleted.rightChild;
                } else {
                    deleted.parent.rightChild = deleted.rightChild;
                }
            } else {
                this.root = deleted.rightChild;
            }

            deleted.rightChild.parent = deleted.parent;
            deleted.rightChild = null;
            deleted.parent = null;
        } else {
            Node<T> left = deleted.leftChild;
            Node<T> exchange = left;
            while (exchange != null && exchange.rightChild != null) {
                exchange = exchange.rightChild;
            }
            deleted.key = exchange.key;
            deleted.value = exchange.value;
            this.delete(exchange);
        }
    }

    public T search(int key) {
        Node<T> result = this.search(key, this.root);
        if (result == null) {
            return null;
        }
        return result.value;
    }

    /**
     * 递归搜索
     * @param key
     * @param root
     * @return
     */
    protected Node<T> search(int key, Node<T> root) {
        if (root == null) {
            return null;
        }
        if (root.key > key) {
            return this.search(key, root.leftChild);
        } else if (root.key < key) {
            return this.search(key, root.rightChild);
        } else {
            return root;
        }
    }

    public void insert(int key, T value) {
        Node<T> node = new Node<T>(key, value);
        if (this.root == null) {
            this.root = node;
        } else {
            this.insert(node, this.root);
        }
    }

    /**
     * 递归查找位置插入元素
     * @param node
     * @param root
     */
    protected void insert(Node<T> node, Node<T> root) {
        if (root.key > node.key) {
            if (root.leftChild == null) {
                root.leftChild = node;
                node.parent = root;
            } else {
                this.insert(node, root.leftChild);
            }
        } else {
            if (root.rightChild == null) {
                root.rightChild = node;
                node.parent = root;
            } else {
                this.insert(node, root.rightChild);
            }
        }
    }

}
