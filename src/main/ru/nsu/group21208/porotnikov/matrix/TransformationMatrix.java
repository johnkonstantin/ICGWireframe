package main.ru.nsu.group21208.porotnikov.matrix;

import org.jetbrains.annotations.NotNull;

public class TransformationMatrix extends Matrix {
    public TransformationMatrix() {
        super(4, 4);
    }

    public static @NotNull Vector3DHomo transform(TransformationMatrix matrix, Vector3DHomo vector) {
        return (Vector3DHomo) Matrix.mul(matrix, vector);
    }
}
