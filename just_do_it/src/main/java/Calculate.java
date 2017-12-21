import java.util.concurrent.locks.LockSupport;

public class Calculate {

    public static void main(String[] args) {
        Value value = new Value();
        value.setValue(100);
        Calculate4 calculate4 = new Calculate4(value);
        calculate4.start();
        Calculate3 calculate3 = new Calculate3(value, calculate4);
        calculate3.start();
        Calculate2 calculate2 = new Calculate2(value, calculate3);
        calculate2.start();
        Calculate1 calculate1 = new Calculate1(value, calculate2);
        calculate1.start();
    }

    static class Value {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    static class Calculate1 extends Thread {

        private Value value;
        private Thread thread;

        public Calculate1(Value value, Thread thread) {
            this.value = value;
            this.thread = thread;
        }

        public void run() {
            int result = this.value.getValue() * 10;
            this.value.setValue(result);
            System.out.println("第一步计算结果为:" + result);
            LockSupport.unpark(thread);
        }
    }

    static class Calculate2 extends Thread {

        private Value value;
        private Thread thread;

        public Calculate2(Value value, Thread thread) {
            this.value = value;
            this.thread = thread;
        }

        public void run() {
            LockSupport.park();
            int result = this.value.getValue() * 20;
            this.value.setValue(result);
            System.out.println("第二步计算结果为:" + result);
            LockSupport.unpark(thread);
        }
    }

    static class Calculate3 extends Thread {

        private Value value;
        private Thread thread;

        public Calculate3(Value value, Thread thread) {
            this.value = value;
            this.thread = thread;
        }

        public void run() {
            LockSupport.park();
            int result = this.value.getValue() + 30;
            this.value.setValue(result);
            System.out.println("第三步计算结果为:" + result);
            LockSupport.unpark(thread);
        }
    }

    static class Calculate4 extends Thread {

        private Value value;
        private Thread thread;

        public Calculate4(Value value) {
            this.value = value;
        }

        public void run() {
            LockSupport.park();
            int result = this.value.getValue() + 40;
            this.value.setValue(result);
            System.out.println("第四步计算结果为:" + result);
        }
    }

}
