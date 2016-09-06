package com.datastruct.structure;

public class DoubleChain<T> {
    private Node<T> start;
    private Node<T> end = new Node<T>(null);// 该节点只是用来标志结束，初始化时即为空；
    private int length = 0;

    private class Node<T> {
        public T value;
        public Node<T> next;
        public Node<T> previous;

        public Node(T value) {
            this.value = value;
        }
    }

    public void reversePrint() {
        Node<T> temp = this.end.previous;
        while (temp != null) {
            System.out.print(temp.value + " ");
            temp = temp.previous;
        }
    }

    public void printAll() {
        Node<T> temp = this.start;
        while (!(temp == null || temp == this.end)) {
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
    }

    public void add(T value) {
        this.add(value, this.length);
    }

    public T remove() {
        return this.remove(this.length - 1);
    }

    public T remove(int index) {
        if (index >= this.length || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Node<T> beRemove = this.start;
        if (index == 0) {
            beRemove.next.previous = null;
            this.start = beRemove.next;

            beRemove.next = null;
        } else {
            while (index > 0) {
                beRemove = beRemove.next;
                index--;
            }
            beRemove.previous.next = beRemove.next;
            beRemove.next.previous = beRemove.previous;

            beRemove.next = null;
            beRemove.previous = null;
        }
        this.length--;
        return beRemove.value;
    }

    public void add(T value, int index) {
        if (index > this.length || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (this.start == null) {
            this.start = new Node<T>(value);
            this.start.next = this.end;
            this.end.previous = this.start;
        } else {
            Node<T> insert = new Node<T>(value);
            if (index == 0) {
                insert.next = this.start;
                this.start.previous = insert;
                this.start = insert;
            } else {
                Node<T> beInsert = this.start;
                while (index > 0) {
                    beInsert = beInsert.next;
                    index--;
                }

                beInsert.previous.next = insert;
                insert.previous = beInsert.previous;

                insert.next = beInsert;
                beInsert.previous = insert;
            }
        }
        this.length++;
    }

}
