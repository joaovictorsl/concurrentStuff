package examples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import barrier.NonReusableBarrier;

public class NonReusableBarrierExample {
    public static void main(String[] args) throws InterruptedException {
        NonReusableBarrier b = new NonReusableBarrier(3);

        Thread[] threads = new Thread[3];
        threads[0] = new Thread(() -> run(b, 0, false));
        threads[1] = new Thread(() -> run(b, 1, false));
        threads[2] = new Thread(() -> run(b, 2, true));

        for (int i = 0; i < 3; i++) {
            threads[i].start();
        }

        for (int i = 0; i < 3; i++) {
            threads[i].join();
        }
    }

    public static void run(NonReusableBarrier b, int id, boolean sleep) {
        try {
            if (sleep) {
                Thread.sleep(2000);
            }
            b.Wait();
            createFile(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(int i) {
        Path path = Paths.get(i + ".aiai");
        try {
            Files.createFile(path);
            System.out.println("File created: " + path.getFileName());
        } catch (IOException e) {
            if (Files.exists(path)) {
                System.out.println("File already exists.");
            } else {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
