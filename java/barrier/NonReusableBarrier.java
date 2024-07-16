package barrier;

import java.util.concurrent.Semaphore;

public class NonReusableBarrier {
    private int n;
    private int counter;
    private Semaphore sem;
    private Semaphore mutex;

    public NonReusableBarrier(int n) {
        this.n = n;
        this.counter = 0;
        this.sem = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public void Wait() throws InterruptedException {
        mutex.acquire();
        this.counter++;
        int count = counter;
        mutex.release();

        if (count == this.n) {
            sem.release();
        }

        sem.acquire();
        sem.release();
    }
}