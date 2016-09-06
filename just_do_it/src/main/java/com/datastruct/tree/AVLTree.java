package com.datastruct.tree;

public class AVLTree<T> extends BinarySearchTree<T> {

    /**
     * 删除思想 deleted为要删除的节点
     * 1.当为叶结点时，将deleted的父节点的指针 与 deleted指向父节点的指针 置空
     * 2.当左结点不为空，右结点为空时，将deleted的父节点的指针指向左结点，并将左结点的父结点指向deleted的父节点
     * 3.当右结点不为空，左结点为空时，将deleted的父节点的指针指向右结点，并将右结点的父结点指向deleted的父节点
     * 4.左右结点均不为空，将右结点的key与值上移到deleted位置，并递归调用将该右结点删除
     * @param deleted
     */
    @Override
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
            // 平衡父结点
            this.resetBalance(deleted.parent);
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
            // 平衡父结点
            this.resetBalance(deleted.parent);
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
            // 平衡父结点
            this.resetBalance(deleted.parent);
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

    private void resetBalance(Node<T> parentNode) {
        // 直到根结点或某一祖先的高度未改变、平衡
        while (parentNode != null
                && (parentNode.height != Node.getNewHeight(parentNode) || parentNode.getBalanceFactor() == -2 || parentNode
                        .getBalanceFactor() == 2)) {
            // 右子树深度高，说明从左子树中删除
            if (parentNode.getBalanceFactor() == 2) {
                Node<T> child = parentNode.rightChild;
                if (child.getBalanceFactor() >= 0) {
                    // 左子树不高于右子树
                    this.singleLeftRotate(parentNode);
                } else {
                    // 左子树高于右子树
                    this.singleRightRotate(child);
                    this.singleLeftRotate(parentNode);
                }
            }

            // 左子树深度高，说明从右子树删除了
            if (parentNode.getBalanceFactor() == -2) {
                Node<T> child = parentNode.leftChild;
                if (child.getBalanceFactor() > 0) {
                    // 左子树低于右子树
                    this.singleLeftRotate(child);
                    this.singleRightRotate(parentNode);
                } else {
                    // 左子树不低于右子树
                    this.singleRightRotate(parentNode);
                }
            }
            // 将父节点的高度重置
            parentNode.resetHeight();
            parentNode = parentNode.parent;
        }
    }

    /**
     * 递归查找位置插入元素
     * @param node
     * @param root
     */
    @Override
    protected void insert(Node<T> node, Node<T> root) {
        if (node.key < root.key) {// 插入左子树
            if (root.leftChild == null) {
                root.leftChild = node;
                node.parent = root;
                root.resetHeight();
            } else {
                this.insert(node, root.leftChild);
                root.resetHeight();
                if (root.getBalanceFactor() == -2) {
                    if (node.key < root.leftChild.key) {// LL型,左侧右旋转
                        this.singleRightRotate(root);
                    } else {// LR型，先左旋转 后右旋转
                        this.singleLeftRotate(root.leftChild);
                        this.singleRightRotate(root);
                    }
                }
            }
        } else if (root.key < node.key) {// 插入右子树
            if (root.rightChild == null) {
                root.rightChild = node;
                node.parent = root;
                root.resetHeight();
            } else {
                this.insert(node, root.rightChild);
                root.resetHeight();
                if (root.getBalanceFactor() == 2) {
                    if (root.rightChild.key < node.key) {// RR型 右侧左旋转
                        this.singleLeftRotate(root);
                    } else { // RL型 ，先右旋转 后左旋转
                        this.singleRightRotate(root.rightChild);
                        this.singleLeftRotate(root);
                    }
                }
            }
        } else {
            // 相等不操作
        }

    }

    /**
     * 右旋转
     * @param node
     */
    private void singleRightRotate(Node<T> node) {
        Node<T> newNode = node.leftChild;
        // 建立 交换节点与父结点关系
        if (this.root != node) {
            if (node == node.parent.leftChild) {
                node.parent.leftChild = newNode;
            } else if (node == node.parent.rightChild) {
                node.parent.rightChild = newNode;
            }
        } else {
            this.root = node.leftChild;
        }
        newNode.parent = node.parent;

        // 交换右结点
        if (newNode.rightChild != null) {
            node.leftChild = newNode.rightChild;
            node.leftChild.parent = node;
        } else {
            node.leftChild = null;
        }

        // 建立node newNode关系
        newNode.rightChild = node;
        node.parent = newNode;
        // 重新设置高度，必须先设置node节点，因其为子结点
        node.resetHeight();
        newNode.resetHeight();
    }

    /**
     * 左旋转
     * @param node
     */
    private void singleLeftRotate(Node<T> node) {
        Node<T> newNode = node.rightChild;
        // 建立 交换节点与父结点关系
        if (this.root != node) {
            if (node == node.parent.leftChild) {
                node.parent.leftChild = newNode;
            } else if (node == node.parent.rightChild) {
                node.parent.rightChild = newNode;
            }
        } else {
            this.root = node.rightChild;
        }
        newNode.parent = node.parent;

        // 交换左结点
        if (newNode.leftChild != null) {
            node.rightChild = newNode.leftChild;
            node.rightChild.parent = node;
        } else {
            node.rightChild = null;
        }

        // 建立node newNode关系
        newNode.leftChild = node;
        node.parent = newNode;
        // 重新设置高度，必须先设置node节点，因其为子结点
        node.resetHeight();
        newNode.resetHeight();
    }

}
