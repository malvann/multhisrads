package com.example.multhithradingconcurencyparfomance.wait_notify_notifyall_ex;

import lombok.AllArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

@AllArgsConstructor
public class MatricesMultiplierConsumer extends Thread {
    private static final int N = 10;
    private FileWriter fileWriter;
    private ThreadSafeQueue queue;

    private static void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
        for (int r = 0; r < N; r++) {
            StringJoiner stringJoiner = new StringJoiner(", ");
            for (int c = 0; c < N; c++) {
                stringJoiner.add(String.format("%.2f", matrix[r][c]));
            }
            fileWriter.write(stringJoiner.toString());
            fileWriter.write('\n');
        }
        fileWriter.write('\n');
    }

    @Override
    public void run() {
        while (true) {
            MatricesPair matricesPair = queue.remove();
            if (matricesPair == null) {
                System.out.println("No more matrices to read from the queue, consumer is terminating");
                break;
            }

            float[][] result = multiplyMatrices(matricesPair.getMatrix1(), matricesPair.getMatrix2());

            try {
                saveMatrixToFile(fileWriter, result);
            } catch (IOException e) {
            }
        }

        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float[][] multiplyMatrices(float[][] m1, float[][] m2) {
        float[][] result = new float[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                for (int k = 0; k < N; k++) {
                    result[r][c] += m1[r][k] * m2[k][c];
                }
            }
        }
        return result;
    }
}
