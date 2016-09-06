package com.datastruct.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

@SuppressWarnings("hiding")
public class QueueCreatedByStack3<String> implements Queue<String> {

    private Stack<String> store = new Stack<String>();
    private Stack<String> temp = new Stack<String>();

    public int size() {
        return this.store.size() + this.temp.size();
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator<String> iterator() {
        return null;
    }

    public Object[] toArray() {
        return null;
    }

    public <T> T[] toArray(T[] a) {
        return null;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends String> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {

    }

    public boolean add(String e) {
        this.store.add(e);
        return true;
    }

    public boolean offer(String e) {
        return false;
    }

    public String remove() {
        return null;
    }

    public String poll() {
        String result = null;
        try {
            if (this.temp.empty()) {
                while (this.store.size() > 1) {
                    this.temp.add(this.store.pop());
                }
                result = this.store.pop();
            } else {
                result = this.temp.pop();
            }

        } catch (Exception e1) {
            return null;
        }
        return result;
    }

    public String element() {
        return null;
    }

    public String peek() {
        return null;
    }

}
