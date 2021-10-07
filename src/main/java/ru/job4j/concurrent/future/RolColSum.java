package ru.job4j.concurrent.future;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/**
 * задача подсчитать суммы по строкам и столбцам квадратной матрицы.
 * - sums[i].rowSum - сумма элементов по i строке
 * - sums[i].colSum  - сумма элементов по i столбцу
 */

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    /**
     * Последовательная версия вычисления суммы по строкам и столбцам
     * @param matrix - входящий массив
     * @return обьект Sums[]
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < sums.length; i++) {
            sums[i] = new Sums();
            sums[i].setRowSum(rowSumCount(matrix, i));
            sums[i].setColSum(colSumCount(matrix, i));
        }
        return sums;
    }

    /**
     *сумма по i строке
     */
    public static int rowSumCount(int[][] data, int row) {
        int sum = 0;
        for (int i = 0; i < data[row].length; i++) {
            sum += data[row][i];
        }
        return sum;
    }

    /**
     *сумма по j столбцу
     */
    public static int colSumCount(int[][] data, int col) {
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i][col];
        }
        return sum;
    }

    /**
     * асинхронный метод.
     * добавляеим в мапу асинхронные задачи в цикле, на каждой итерации вызываем getTask(), считаем сумму
     * по строке и столбцу, записываем обьект Sums с полями (сумма по строке, сумма по столбцу).
     * В конце создаем массив Sums[], и итерированием по мапе записываем данные в массив.
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        Sums[] sumsRsl = new Sums[matrix.length];
        for (Integer temp : futures.keySet()) {
            sumsRsl[temp] = futures.get(temp).get();
        }
        return sumsRsl;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            Sums s = new Sums();
            s.setRowSum(rowSumCount(matrix, i));
            s.setColSum(colSumCount(matrix, i));
            return s;
        });
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * тест последовательное выполение
         * (разница по скорости ~ 2 - 3 раза)
         */
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        long start = System.currentTimeMillis();
        Sums[] data = RolColSum.sum(array);
        for (Sums temp : data) {
            System.out.println(String.format("Сумма по строке %s, Сумма по столбцу %s", temp.getRowSum(), temp.getColSum()));
        }
        System.out.println(String.format("<Время выполнения последовательно:  %d >", System.currentTimeMillis() - start));
        System.out.println("-".repeat(40));

        /**
         * тест асинхронное выполнение
         */
        long start2 = System.currentTimeMillis();
        Sums[] async = RolColSum.asyncSum(array);
        for (Sums temp : async) {
            System.out.println(String.format("Сумма по строке %s, Сумма по столбцу %s", temp.getRowSum(), temp.getColSum()));
        }
        System.out.println(String.format("<Время выполнения асинхронно: %d >", System.currentTimeMillis() - start2));

        System.out.println(System.lineSeparator().repeat(2));


        /**
         * тестирования массива большего размера
         * Создаем рандомный массив 30 Х 30
         * Передаем созданный массив в  последовательный метод и в асинхронный
         * Замеряем время выполнения по каждому варианту (разница по скорости ~ 2 - 3 раза)
         */

        int[][] bigArray = new int[30][];
        for (int i = 0; i < bigArray.length; i++) {
            bigArray[i] = Stream.generate(() -> new Random().nextInt(10)).mapToInt(x -> x).limit(30).toArray();
            System.out.println(Arrays.toString(bigArray[i]));
        }

        long s1 = System.currentTimeMillis();
        Sums[] dataBig = RolColSum.sum(bigArray);
        for (Sums temp : dataBig) {
            System.out.println(String.format("Сумма по строке %s, Сумма по столбцу %s", temp.getRowSum(), temp.getColSum()));
        }
        System.out.println(String.format("<Время выполнения последовательно: %d >", System.currentTimeMillis() - s1));

        System.out.println(System.lineSeparator());

        long s2 = System.currentTimeMillis();
        Sums[] asyncDataBig = RolColSum.asyncSum(bigArray);
        for (Sums temp : asyncDataBig) {
            System.out.println(String.format("Сумма по строке %s, Сумма по столбцу %s", temp.getRowSum(), temp.getColSum()));
        }
        System.out.println(String.format("<Время выполнения асинхронно: %d >", System.currentTimeMillis() - s2));
    }
}
