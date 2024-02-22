package com.example.multhithradingconcurencyparfomance.wait_notify_notifyall_ex;

import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class MatricesReaderProducer extends Thread {
    private static final int N = 10;
    private Scanner scanner;
    private ThreadSafeQueue queue;

    @Override
    public void run() {
        while (true) {
            float[][] matrix1 = readMatrix();
            float[][] matrix2 = readMatrix();
            if (matrix1 == null || matrix2 == null) {
                queue.terminate();
                System.out.println("No more matrices to read. Producer Thread is terminating");
                return;
            }

            queue.add(new MatricesPair(matrix1, matrix2));
        }
    }

    private float[][] readMatrix() {
        float[][] matrix = new float[N][N];
        for (int r = 0; r < N; r++) {
            if (!scanner.hasNext()) return null;
            String[] line = scanner.nextLine().split(",");
            for (int c = 0; c < N; c++) {
                matrix[r][c] = Float.parseFloat(line[c]);
            }
        }
        scanner.nextLine();
        return matrix;
    }
}
