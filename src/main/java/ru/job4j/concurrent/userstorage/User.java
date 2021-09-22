package ru.job4j.concurrent.userstorage;

import java.util.Objects;

public class User {

    private int id;

    private int amount;

    public User(final int id, final int amount) {
        this.id = id;
        this.amount = amount;
    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  int getAmount() {
        return amount;
    }

    public  void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && amount == user.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
