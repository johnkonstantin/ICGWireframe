package main.ru.nsu.group21208.porotnikov;

import main.ru.nsu.group21208.porotnikov.matrix.Matrix;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class Curve {
    private final Point[] basePoints;
    private final int N;
    private final Matrix Ms = new Matrix(new int[][] {
            {-1, 3, -3, 1},
            {3, -6, 3, 0},
            {-3, 0, 3, 0},
            {1, 4, 1, 0}
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
        Ms.setDivider(6);
    }

    public Point[][] getCurvePoints() {
        Point[][] res = new Point[this.basePoints.length - 3][N + 1];

        for (int i = 1; i < this.basePoints.length - 2; ++i) {
            Matrix Gx = new Matrix(new int[] {
                    this.basePoints[i - 1].x,
                    this.basePoints[i].x,
                    this.basePoints[i+1].x,
                    this.basePoints[i+2].x
            }, Matrix.Orientation.Vertical);
            for (int t = 0; t < N + 1; ++t) {
                Matrix T = new Matrix(new int[] {
                        t * t * t,
                        t * t,
                        t,
                        1
                }, Matrix.Orientation.Horizontal);
                T.setDivider(N);
                Matrix resMatrix = Matrix.mul(Matrix.mul(T, this.Ms), Gx);
                res[i - 1][t].x = resMatrix.getIntArray()[0][0];
            }

            Matrix Gy = new Matrix(new int[] {
                    this.basePoints[i - 1].y,
                    this.basePoints[i].y,
                    this.basePoints[i+1].y,
                    this.basePoints[i+2].y
            }, Matrix.Orientation.Vertical);
            for (int t = 0; t < N + 1; ++t) {
                Matrix T = new Matrix(new int[] {
                        t * t * t,
                        t * t,
                        t,
                        1
                }, Matrix.Orientation.Horizontal);
                T.setDivider(N);
                Matrix resMatrix = Matrix.mul(Matrix.mul(T, this.Ms), Gy);
                res[i - 1][t].y = resMatrix.getIntArray()[0][0];
            }
        }

        return res;
    }

    public Point[] getBasePoints() {
        return this.basePoints;
    }

}