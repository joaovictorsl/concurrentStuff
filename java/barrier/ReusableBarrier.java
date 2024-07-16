package barrier;

import java.util.concurrent.Semaphore;

public class ReusableBarrier {
    private int n;
    private int counter;
    private Semaphore sem1;
    private Semaphore sem2;
    private Semaphore mutex;

    public ReusableBarrier(int n) {
        this.n = n;
        this.counter = 0;
        this.sem1 = new Semaphore(0);
        this.sem2 = new Semaphore(1);
        this.mutex = new Semaphore(1);
    }

    public void Wait() throws InterruptedException {
        mutex.acquire();
        this.counter++;
        int count = counter;
        mutex.release();

        if (count == this.n) {
            sem1.release();
            sem2.acquire();
        }

        sem1.acquire();
        sem1.release();

        mutex.acquire();
        this.counter--;
        count = counter;
        mutex.release();

        if (count == 0) {
            sem2.release();
            sem1.acquire();
        }

        sem2.acquire();
        sem2.release();
    }
}