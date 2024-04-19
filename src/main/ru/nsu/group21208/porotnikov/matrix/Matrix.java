package main.ru.nsu.group21208.porotnikov.matrix;

import java.util.Arrays;

public class Matrix {
    private final int[][] matrix;
    private int divider;
    private final int h;
    private final int w;

    public Matrix(int h, int w) {
        if (h <= 0 || w <= 0) {
            throw new RuntimeException("Wrong matrix size, all dimensions must be greater then 0!");
        }
        this.matrix = new int[h][w];
        this.divider = 1;
        this.h = h;
        this.w = w;
    }

    public Matrix(int[][] arr) {
        if (arr == null || arr.length == 0 || arr[0].length == 0) {
            throw new RuntimeException("Initial array for matrix must be not null and all dimensions must be greater then 0!");
        }
        this.matrix = arr.clone();
        this.h = arr.length;
        this.w = arr[0].length;
    }

    public void setDivider(int newDivider) {
        if (newDivider == 0) {
            throw new RuntimeException("Divider must be greater then 0!");
        }
        this.divider = newDivider;
    }

    public static Matrix sum(Matrix a, Matrix b) {
        if (a == null || b == null || a.h != b.h || a.w != b.w) {
            return null;
        }
        Matrix res = new Matrix(a.h, a.w);
        int aM = b.divider;
        int bM = a.divider;
        if (a.divider % b.divider == 0) {
            res.divider = a.divider;
            aM = 1;
            bM = a.divider / b.divider;
        }
        else if (b.divider % a.divider == 0) {
            res.divider = b.divider;
            aM = b.divider / a.divider;
            bM = 1;
        }
        else {
            res.divider = a.divider * b.divider;
        }

        for (int i = 0; i < a.h; ++i) {
            for (int j = 0; j < a.w; ++j) {
                res.matrix[i][j] = aM * a.matrix[i][j] + bM * b.matrix[i][j];
            }
        }

        return res;
    }

    public static Matrix sub(Matrix a, Matrix b) {
        if (a == null || b == null || a.h != b.h || a.w != b.w) {
            return null;
        }
        Matrix res = new Matrix(a.h, a.w);
        int aM = b.divider;
        int bM = a.divider;
        if (a.divider % b.divider == 0) {
            res.divider = a.divider;
            aM = 1;
            bM = a.divider / b.divider;
        }
        else if (b.divider % a.divider == 0) {
            res.divider = b.divider;
            aM = b.divider / a.divider;
            bM = 1;
        }
        else {
            res.divider = a.divider * b.divider;
        }

        for (int i = 0; i < a.h; ++i) {
            for (int j = 0; j < a.w; ++j) {
                res.matrix[i][j] = aM * a.matrix[i][j] - bM * b.matrix[i][j];
            }
        }

        return res;
    }

    public static Matrix mul(Matrix a, Matrix b) {
        if (a == null || b == null || a.w != b.h) {
            return null;
        }
        Matrix res = new Matrix(a.h, b.w);
        res.divider = a.divider * b.divider;

        for (int i = 0; i < res.h; ++i) {
            for (int k = 0; k < a.w; ++k) {
                for (int j = 0; j < res.w; ++j) {
                    res.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
                }
            }
        }

        return res;
    }


    public static Matrix mulToNumber(Matrix a, int b) {
        if (a == null) {
            return null;
        }
        Matrix res = new Matrix(a.h, a.w);
        if (b % a.divider == 0) {
            b /= a.divider;
            a.divider = 1;
        }

        for (int i = 0; i < res.h; ++i) {
            for (int j = 0; j < res.w; ++j) {
                res.matrix[i][j] = a.matrix[i][j] * b;
            }
        }

        return res;
    }

    public int[][] getIntArray() {
        int[][] res = new int[this.h][this.w];

        for (int i = 0; i < this.h; ++i) {
            for (int j = 0; j < this.w; ++j) {
                res[i][j] = this.matrix[i][j] / this.divider;
            }
        }

        return res;
    }

    public double[][] getDoubleArray() {
        double[][] res = new double[this.h][this.w];

        for (int i = 0; i < this.h; ++i) {
            for (int j = 0; j < this.w; ++j) {
                res[i][j] = (double)this.matrix[i][j] / this.divider;
            }
        }

        return res;
    }

    public void print() {
        for (int i = 0; i < this.h; ++i) {
            for (int j = 0; j < this.w; ++j) {
                System.out.printf("%.2f ", (double)this.matrix[i][j] / this.divider);
            }
            System.out.println();
        }
    }
}
