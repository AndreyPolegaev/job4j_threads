package ru.job4j.concurrent.future;

import org.junit.Test;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class RolColSumTest {

    @Test
    public void whenSmallArraysStepByStep() throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] data = RolColSum.sum(array);
        int[] rsl = Arrays.stream(data).flatMapToInt(el -> Stream.of(el.getRowSum(), el.getColSum()).mapToInt(x -> x)).toArray();
        int[] expected = new int[]{6, 12, 15, 15, 24, 18};
        assertArrayEquals(rsl, expected);
    }

    @Test
    public void whenSmallArrayAsync() throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] data = RolColSum.asyncSum(array);
        int[] rsl = Arrays.stream(data).flatMapToInt(el -> Stream.of(el.getRowSum(), el.getColSum()).mapToInt(x -> x)).toArray();
        int[] expected = new int[]{6, 12, 15, 15, 24, 18};
        assertArrayEquals(rsl, expected);
    }

    @Test
    public void whenCheckRow() throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int rowSum = RolColSum.rowSumCount(array, 1);
        int colSum = RolColSum.colSumCount(array, 2);
        assertThat(rowSum, is(15));
        assertThat(colSum, is(18));
    }

    @Test
    public void whenCheckRow2() throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        int rowSum = RolColSum.rowSumCount(array, 4);
        int colSum = RolColSum.colSumCount(array, 2);
        assertThat(rowSum, is(115));
        assertThat(colSum, is(65));
    }
}