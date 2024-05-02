package main.ru.nsu.group21208.porotnikov.matrix;


public class Matrix {
    private double[][] matrix;
    private int h;
    private int w;

    public enum Orientation {
        Horizontal,
        Vertical
    }

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
        this.h = arr.length;
        this.w = arr[0].length;
        this.matrix = arr.clone();
    }

    public Matrix(long[][] arr) {
        if (arr == null || arr.length == 0 || arr[0].length == 0) {
            throw new RuntimeException("Initial array for matrix must be not null and all dimensions must be greater then 0!");
        }
        this.h = arr.length;
        this.w = arr[0].length;
        this.matrix = new double[h][w];
        for (int i = 0; i < this.h; ++i) {
            for (int j = 0; j < w; ++j) {
                this.matrix[i][j] = arr[i][j];
            }
        }
    }

    public Matrix(int[][] arr) {
        if (arr == null || arr.length == 0 || arr[0].length == 0) {
            throw new RuntimeException("Initial array for matrix must be not null and all dimensions must be greater then 0!");
        }
        this.h = arr.length;
        this.w = arr[0].length;
        this.matrix = new double[h][w];
        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr[i].length; ++j) {
                this.matrix[i][j] = arr[i][j];
            }
        }
    }

    public Matrix(double[] arr, Orientation orientation) {
        if (arr == null || orientation == null) {
            throw new RuntimeException("Initial array for matrix and orientation must be not null!");
        }
        switch (orientation) {
            case Horizontal -> {
                this.h = 1;
                this.w = arr.length;
                this.matrix = new double[this.h][this.w];
                System.arraycopy(arr, 0, this.matrix[0], 0, this.w);
            }
            case Vertical -> {
                this.h = arr.length;
                this.w = 1;
                this.matrix = new double[this.h][this.w];
                for (int i = 0; i < this.h; ++i) {
                    this.matrix[i][0] = arr[i];
                }
            }
        }
    }

    public Matrix(long[] arr, Orientation orientation) {
        if (arr == null || orientation == null) {
            throw new RuntimeException("Initial array for matrix and orientation must be not null!");
        }
        switch (orientation) {
            case Horizontal -> {
                this.h = 1;
                this.w = arr.length;
                this.matrix = new double[this.h][this.w];
                for (int i = 0; i < this.w; ++i) {
                    this.matrix[0][i] = arr[i];
                }
            }
            case Vertical -> {
                this.h = arr.length;
                this.w = 1;
                this.matrix = new double[this.h][this.w];
                for (int i = 0; i < this.h; ++i) {
                    this.matrix[i][0] = arr[i];
                }
            }
        }
    }

    public Matrix(int[] arr, Orientation orientation) {
        if (arr == null || orientation == null) {
            throw new RuntimeException("Initial array for matrix and orientation must be not null!");
        }
        switch (orientation) {
            case Horizontal -> {
                this.h = 1;
                this.w = arr.length;
                this.matrix = new double[this.h][this.w];
                for (int i = 0; i < arr.length; ++i) {
                    this.matrix[0][i] = arr[i];
                }
            }
            case Vertical -> {
                this.h = arr.length;
                this.w = 1;
                this.matrix = new double[this.h][this.w];
                for (int i = 0; i < this.h; ++i) {
                    this.matrix[i][0] = arr[i];
                }
            }
        }
    }

    public static Matrix sum(Matrix a, Matrix b) {
        if (a == null || b == null || a.h != b.h || a.w != b.w) {
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
        if (a == null || b == null || a.h != b.h || a.w != b.w) {
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
        if (a == null || b == null || a.w != b.h) {
            return null;
        }
        Matrix res = new Matrix(a.h, b.w);

        for (int i = 0; i < res.h; ++i) {
            for (int k = 0; k < a.w; ++k) {
                for (int j = 0; j < res.w; ++j) {
                    res.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
                }
            }
        }

        return res;
    }


    public static Matrix mulToNumber(Matrix a, double b) {
        if (a == null) {
            return null;
        }
        Matrix res = new Matrix(a.h, a.w);

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
                res[i][j] = (int) this.matrix[i][j];
            }
        }

        return res;
    }

    public long[][] getLongArray() {
        long[][] res = new long[this.h][this.w];

        for (int i = 0; i < this.h; ++i) {
            for (int j = 0; j < this.w; ++j) {
                res[i][j] = (long)this.matrix[i][j];
            }
        }

        return res;
    }

    public double[][] getDoubleArray() {
        double[][] res = new double[this.h][this.w];

        for (int i = 0; i < this.h; ++i) {
            System.arraycopy(this.matrix[i], 0, res[i], 0, this.w);
        }

        return res;
    }

    public void print() {
        for (int i = 0; i < this.h; ++i) {
            for (int j = 0; j < this.w; ++j) {
                System.out.printf("%.2f ", this.matrix[i][j]);
            }
            System.out.println();
        }
    }
}
