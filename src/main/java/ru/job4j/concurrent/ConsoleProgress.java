package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] symbols = new String[]{"\\", "|", "/" };
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    System.out.print("\r Loading..." + symbols[i]);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage() + "в ConsoleProgress");
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
