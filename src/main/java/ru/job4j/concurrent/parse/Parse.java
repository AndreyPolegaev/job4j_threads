package ru.job4j.concurrent.parse;

import java.util.function.Predicate;

public interface Parse {

    String getContent(Predicate<Character> filter);

}
