package ru.job4j.concurrent.parse;

import java.io.File;

public class Client {
    public static void main(String[] args) {
        ParseFile parseFile = new ParseFile(new File("./test.txt"));
        SaveContent save = new SaveContent(new File("./rsl.txt"));
        String content = parseFile.getContent(el -> el < 0x80);
        save.saveContent(content);
    }
}
