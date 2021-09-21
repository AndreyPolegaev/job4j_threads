package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;
    long delay = 0;


    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            Long start = System.currentTimeMillis();
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                Long finish = System.currentTimeMillis();
                if ((finish - start) < speed) {
                    this.delay++;
                    run();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                fileOutputStream.flush();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url;
        int speed;
        String fileName;
        try {
            url = args[0];
            speed = Integer.parseInt(args[1]);
            fileName = args[2];
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("некорректные данные");
        }
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}
