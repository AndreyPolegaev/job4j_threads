package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long delay = 0;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("Wget.txt")) {
            byte[] dataBuffer = new byte[1024];
            Long start = System.currentTimeMillis();
            int bytesRead = in.read(dataBuffer, 0, 1024);
            Long finish = System.currentTimeMillis();
            if ((finish - start) < speed) {
                delay = (finish - start) + 1000;
            }
            while (bytesRead != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                bytesRead = in.read(dataBuffer, 0, 1024);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
