package ru.job4j.concurrent.parse;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile implements Parse {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        StringBuilder sb = new StringBuilder();
        int data;
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = in.read()) > 0) {
                if (filter.test((char) data)) {
                    sb.append((char) data);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
}
