package com.datastruct.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

@SuppressWarnings("hiding")
public class QueueCreatedByStack<String> implements Queue<String> {

    private Stack<String> store = new Stack<String>();
    private Stack<String> temp = new Stack<String>();

    public int size() {
        return this.store.size();
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
        while (this.store.size() > 1) {
            this.temp.add(this.store.pop());
        }
        String result = this.store.pop();
        while (this.temp.size() > 0) {
            this.store.add(this.temp.pop());
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
