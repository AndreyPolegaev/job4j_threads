package ru.job4j.concurrent.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * emailTo(User user) - он должен через ExecutorService отправлять почту.
 * emailTo должен брать данные пользователя и подставлять в шаблон
 * subject = Notification {username} to email {email}.
 * body = Add a new event to {username}
 * <p>
 * body = Add a new event to {username}
 * close() - он должен закрыть pool. То есть в классе EmailNotification должно быть поле pool,
 * которые используется в emailTo и close().
 * <p>
 * Через ExecutorService создайте задачу, которая будет создавать данные
 * для пользователя и передавать их в метод send.
 */

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        String subject = "Notification {username} to email {email}"
                .replace("{username}", user.getUsername()).replace("{email}", user.getEmail());
        String body = "Add a new event to {username}"
                .replace("{username}", user.getUsername());


        Runnable task = () -> {
            send(subject, body, user.getEmail());
        };
        pool.submit(task);
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
