package ru.job4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {

    public static void test() {
        System.out.println("hello");
    }



    public static void main(String[] args) {

//
//
//        String s = "hello";
//        String rsl = new StringBuilder(s).reverse().toString();
//        System.out.println(rsl);


        Function<String, String> function = (s) -> new StringBuilder(s).reverse().toString();
        System.out.println(function.apply("hello"));

        UnaryOperator<String> unaryOperator = (s) -> new StringBuilder(s).reverse().toString();
        System.out.println(unaryOperator.apply("hello"));



    }

}
