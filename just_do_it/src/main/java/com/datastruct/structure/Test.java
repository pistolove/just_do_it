package com.datastruct.structure;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        // test.DoubleChain();
        test.SingleChain();
    }

    private void SingleChain() {
        SingleChain<String> chain = new SingleChain<String>();

        chain.add("a");
        chain.add("b");
        chain.add("c");
        chain.add("e");
        chain.add("d", 3);
        chain.printAll();
        chain.reverse();
        chain.printAll();

        // System.out.print(chain.search("c"));
        System.out.print("   " + chain.remove(0));
        System.out.print("   " + chain.remove(0));
        System.out.print("   " + chain.remove(0));
        System.out.print("   " + chain.remove(0));
        System.out.print("   " + chain.remove(0));

        chain.add("a");
        chain.add("b");
        chain.add("c");
        chain.add("e");
        chain.add("d", 3);
        chain.printAll();
        chain.reverse();
        chain.reverse();
        chain.printAll();

    }

    private void DoubleChain() {
        DoubleChain<String> chain = new DoubleChain<String>();
        chain.add("a");
        chain.add("b");
        chain.add("c");
        chain.add("e");
        chain.add("d", 3);

        System.out.println(chain.remove());
        System.out.println(chain.remove());
        System.out.println(chain.remove());
        System.out.println(chain.remove());
        System.out.println(chain.remove());

        chain.add("a");
        chain.add("b");
        chain.add("c");
        chain.add("e");
        chain.add("d", 3);

        chain.printAll();
        chain.reversePrint();
    }

    private void QueueCreatedByStack() {
        QueueCreatedByStack<String> q = new QueueCreatedByStack<String>();
        q.add("a");
        q.add("b");
        q.add("c");
        q.add("d");
        q.add("e");
        q.add("f");
        while (q.size() > 0) {
            System.out.print(q.poll() + " ");
        }
        System.out.println();
    }

    private void QueueCreatedByStack2() {
        QueueCreatedByStack2<String> q2 = new QueueCreatedByStack2<String>();
        q2.add("a");
        q2.add("b");
        q2.add("c");
        q2.add("d");
        q2.add("e");
        q2.add("f");
        while (q2.size() > 0) {
            System.out.print(q2.poll() + " ");
        }
        System.out.println();
    }

    private void QueueCreatedByStack3() {
        QueueCreatedByStack3<String> q3 = new QueueCreatedByStack3<String>();
        q3.add("a");
        q3.add("b");
        q3.add("c");
        q3.add("d");
        q3.add("e");
        q3.add("f");
        while (q3.size() > 0) {
            System.out.print(q3.poll() + " ");
        }
        System.out.println();
    }

    private void StackCreatedByQueue() {
        StackCreatedByQueue<String> s = new StackCreatedByQueue<String>();
        s.push("a");
        s.push("b");
        s.push("c");
        s.push("d");
        s.push("e");
        while (s.size() > 0) {
            System.out.print(s.pop() + " ");
        }
    }
}
