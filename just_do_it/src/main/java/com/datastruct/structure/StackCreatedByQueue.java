package com.datastruct.structure;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("hiding")
public class StackCreatedByQueue<String> extends Stack<String> {

    private static final long serialVersionUID = 1L;
    private Queue<String> store = new ConcurrentLinkedQueue<String>();
    private Queue<String> temp = new ConcurrentLinkedQueue<String>();

    @Override
    public String push(String item) {
        if (this.temp.isEmpty()) {
            this.store.add(item);
        } else {
            this.temp.add(item);
        }
        return item;
    }

    @Override
    public String pop() {
        if (this.temp.isEmpty()) {
            while (this.store.size() > 1) {
                this.temp.add(this.store.poll());
            }
            return this.store.poll();
        } else {
            while (this.temp.size() > 1) {
                this.store.add(this.temp.poll());
            }
            return this.temp.poll();

        }
    }

    @Override
    public int size() {
        int size = 0;
        if (!this.store.isEmpty()) {
            size = this.store.size();
        }
        if (!this.temp.isEmpty()) {
            size = this.temp.size();
        }
        return size;
    }
}
