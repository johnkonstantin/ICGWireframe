package main.ru.nsu.group21208.porotnikov.matrix;

import org.jetbrains.annotations.NotNull;

public class TransformationMatrix extends Matrix {
    public TransformationMatrix() {
        super(4, 4);
    }

    public TransformationMatrix(double[][] arr) {
        super(arr);
        if (arr.length != 4 || arr[0].length != 4) {
            throw new IllegalArgumentException("Transformation matrix must be 4x4!");
        }
    }

    public static @NotNull Vector3DHomo transform(TransformationMatrix matrix, Vector3DHomo vector) {
        double[][] res = Matrix.mul(matrix, vector).getDoubleArray();
        return new Vector3DHomo(res[0][0], res[1][0], res[2][0], res[3][0]);
    }
}
