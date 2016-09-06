package com.datastruct.structure;

public class SingleChain<T> {
    private Node<T> start;
    private Node<T> end;
    private int length = 0;

    public SingleChain() {
        this.start = this.end = new Node<T>(null);
    }

    private class Node<T> {
        public T value;
        public Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            if (this.value != null) {
                return this.value.toString();
            }
            return null;
        }
    }

    public int search(T value) {
        Node<T> temp = this.start;
        int index = 0;
        while (this.end != temp) {
            if (temp.value == value) {
                return index;
            }
            index++;
            temp = temp.next;
        }
        return -1;
    }

    /**
     * 链表是否有环，
     * @return
     */
    public boolean ifCircle() {
        int length = this.length;
        Node<T> temp = this.start;
        while (length > 0) {
            temp = temp.next;
        }
        if (temp == this.end) {
            return false;// 执行到最后是尾结点，证明无环
        }
        return true;
    }

    /**
     * 链表逆序
     * @return
     */
    public T reverse() {
        if (this.start == this.end) {
            return null;
        }
        Node<T> pre = null;
        Node<T> nex = null;
        Node<T> current = this.start;
        while (this.end != current) {
            nex = current.next;
            current.next = pre;// 修改指针方向
            pre = current;
            current = nex;
        }
        // 将end节点换过去
        this.start.next = this.end;
        this.start = pre;

        return this.start.value;
    }

    public void printAll() {
        Node<T> temp = this.start;
        while (this.end != temp) {
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
    }

    public T remove() {
        return this.remove(this.length - 1);
    }

    public T remove(int index) {
        if (index >= this.length || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<T> beRemove = this.start;
        T result = null;
        if (index == 0) {
            this.start = beRemove.next;
            beRemove.next = null;
            result = beRemove.value;
        } else {
            while (index - 1 > 0) {// 这里必须减1，因为 没法回头建链接
                beRemove = beRemove.next;
                index--;
            }
            Node<T> beRemoveNode = beRemove.next;
            beRemove.next = beRemove.next.next;// 移除 该节点
            beRemoveNode.next = null;
            result = beRemoveNode.value;
        }
        this.length--;
        return result;
    }

    public void add(T value) {
        this.add(value, this.length);
    }

    public void add(T value, int index) {
        if (index > this.length || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<T> insert = new Node<T>(value);
        if (index == 0) { // 插入0位置时特殊处理
            insert.next = this.start;
            this.start = insert;
        } else {
            Node<T> insertPre = this.start;
            while (index - 1 > 0) { // 这里必须减1，因为 没法回头建链接
                insertPre = insertPre.next;
                index--;
            }
            insert.next = insertPre.next;
            insertPre.next = insert;
        }
        this.length++;
    }
}
