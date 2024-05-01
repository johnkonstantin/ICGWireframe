package main.ru.nsu.group21208.porotnikov;

import main.ru.nsu.group21208.porotnikov.matrix.Matrix;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class Curve {
    private final Point[] basePoints;
    private final int N;
    private final Matrix Ms = new Matrix(new double[][]{
            {-1.0 / 6, 0.5, -0.5, 1.0 / 6},
            {0.5, -1.0, 0.5, 0},
            {-0.5, 0, 0.5, 0},
            {1.0 / 6, 2.0 / 3, 1.0 / 6, 0}
    });

    public Curve(Point[] basePoints, int N) {
        if (basePoints == null || Arrays.stream(basePoints).anyMatch(Objects::isNull)) {
            throw new RuntimeException("Array or its elements must be not null!");
        }
        if (basePoints.length < 4) {
            throw new RuntimeException("At least 4 base points!");
        }
        if (N < 1) {
            throw new RuntimeException("At least 1 segment!");
        }
        this.basePoints = basePoints;
        this.N = N;
    }

    public Point[][] getCurvePoints() {
        Point[][] res = new Point[this.basePoints.length - 3][this.N + 1];
        for (int i = 0; i < res.length; ++i) {
            for (int j = 0; j < res[i].length; ++j) {
                res[i][j] = new Point();
            }
        }

        for (int i = 1; i < this.basePoints.length - 2; ++i) {
            Matrix Gx = new Matrix(new int[]{
                    this.basePoints[i - 1].x,
                    this.basePoints[i].x,
                    this.basePoints[i + 1].x,
                    this.basePoints[i + 2].x
            }, Matrix.Orientation.Vertical);
            for (int t = 0; t < N + 1; ++t) {
                Matrix T = new Matrix(new int[]{
                        t * t * t,
                        t * t * N,
                        t * N * N,
                        N * N * N
                }, Matrix.Orientation.Horizontal);
                Matrix resMatrix = Matrix.mulToNumber(Matrix.mul(Matrix.mul(T, this.Ms), Gx), 1.0 / (N * N * N));
                res[i - 1][t].x = resMatrix.getIntArray()[0][0];
            }

            Matrix Gy = new Matrix(new int[]{
                    this.basePoints[i - 1].y,
                    this.basePoints[i].y,
                    this.basePoints[i + 1].y,
                    this.basePoints[i + 2].y
            }, Matrix.Orientation.Vertical);
            for (int t = 0; t < N + 1; ++t) {
                Matrix T = new Matrix(new int[]{
                        t * t * t,
                        t * t * N,
                        t * N * N,
                        N * N * N
                }, Matrix.Orientation.Horizontal);
                Matrix resMatrix = Matrix.mulToNumber(Matrix.mul(Matrix.mul(T, this.Ms), Gy), 1.0 / (N * N * N));
                res[i - 1][t].y = resMatrix.getIntArray()[0][0];
            }
        }

        return res;
    }

    public Point[] getBasePoints() {
        return this.basePoints.clone();
    }

    public int getN() {
        return this.N;
    }

}