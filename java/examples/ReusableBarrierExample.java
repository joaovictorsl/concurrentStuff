package examples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import barrier.ReusableBarrier;

public class ReusableBarrierExample {
    public static void main(String[] args) throws InterruptedException {
        ReusableBarrier b = new ReusableBarrier(3);

        for (int i = 0; i < 3; i++) {
            int id1 = 0 + i * 10;
            int id2 = 1 + i * 10;
            int id3 = 2 + i * 10;

            Thread[] threads = new Thread[3];
            threads[0] = new Thread(() -> run(b, id1, false));
            threads[1] = new Thread(() -> run(b, id2, false));
            threads[2] = new Thread(() -> run(b, id3, true));

            for (int j = 0; j < 3; j++) {
                threads[j].start();
            }

            for (int j = 0; j < 3; j++) {
                threads[j].join();
            }
        }
    }

    public static void run(ReusableBarrier b, int id, boolean sleep) {
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
