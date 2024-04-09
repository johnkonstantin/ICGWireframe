package main.ru.nsu.group21208.porotnikov.matrix;

public class Matrix {
    private final double[][] matrix;
    private final int h;
    private final int w;

    public Matrix(int h, int w) {
        if (h <= 0 || w <= 0) {
            throw new RuntimeException("Wrong matrix size, all dimensions must be greater then 0!");
        }
        this.matrix = new double[h][w];
        this.h = h;
        this.w = w;
    }

    public Matrix(double[][] arr) {
        if (arr == null || arr.length == 0 || arr[0].length == 0) {
            throw new RuntimeException("Initial array for matrix must be not null and all dimensions must be greater then 0!");
        }
        this.matrix = arr.clone();
        this.h = arr.length;
        this.w = arr[0].length;
    }

    public static Matrix sum(Matrix a, Matrix b) {
        if (a.h != b.h || a.w != b.w) {
            return null;
        }
        Matrix res = new Matrix(a.h, a.w);

        for (int i = 0; i < a.h; ++i) {
            for (int j = 0; j < a.w; ++j) {
                res.matrix[i][j] = a.matrix[i][j] + b.matrix[i][j];
            }
        }

        return res;
    }

    public static Matrix sub(Matrix a, Matrix b) {
        if (a.h != b.h || a.w != b.w) {
            return null;
        }
        Matrix res = new Matrix(a.h, a.w);

        for (int i = 0; i < a.h; ++i) {
            for (int j = 0; j < a.w; ++j) {
                res.matrix[i][j] = a.matrix[i][j] - b.matrix[i][j];
            }
        }

        return res;
    }

    public static Matrix mul(Matrix a, Matrix b) {
        if (a.w != b.h) {
            return null;
        }
        Matrix res = new Matrix(a.h, b.w);

        for (int i = 0; i < res.h; ++i) {
            for (int j = 0; j < res.w; ++j) {
                for (int k = 0; k < a.w; ++k) {
                    res.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j]; //TODO Optimize
                }
            }
        }

        return res;
    }

    public void print() {
        for (int i = 0; i < this.h; ++i) {
            for (int j = 0; j < this.w; ++j) {
                System.out.printf("%.3f ", this.matrix[i][j]);
            }

            System.out.println();
        }
    }
}
