package ru.job4j.concurrent.parse;

import java.io.*;

public class SaveContent implements Save {

    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    @Override
    public void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
