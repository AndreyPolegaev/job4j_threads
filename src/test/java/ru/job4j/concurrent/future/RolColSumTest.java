package ru.job4j.concurrent.future;

import org.junit.Test;
import java.util.concurrent.ExecutionException;
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
        assertThat(data[0].getRowSum(), is(6));
        assertThat(data[0].getColSum(), is(12));
        assertThat(data[1].getRowSum(), is(15));
        assertThat(data[1].getColSum(), is(15));
        assertThat(data[2].getRowSum(), is(24));
        assertThat(data[2].getColSum(), is(18));
    }

    @Test
    public void whenSmallArrayAsync() throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] data = RolColSum.asyncSum(array);
        assertThat(data[0].getRowSum(), is(6));
        assertThat(data[0].getColSum(), is(12));
        assertThat(data[1].getRowSum(), is(15));
        assertThat(data[1].getColSum(), is(15));
        assertThat(data[2].getRowSum(), is(24));
        assertThat(data[2].getColSum(), is(18));
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