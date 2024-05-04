package main.ru.nsu.group21208.porotnikov.matrix;

import org.jetbrains.annotations.NotNull;

public class Vector3DHomo extends Matrix {
    public Vector3DHomo(double x, double y, double z) {
        super(new double[]{x, y, z, 1}, Orientation.Vertical);
    }

    public Vector3DHomo(double x, double y, double z, double w) {
        super(new double[]{x, y, z, w}, Orientation.Vertical);
    }

    public double getX() {
        return this.matrix[0][0];
    }

    public double getY() {
        return this.matrix[1][0];
    }

    public double getZ() {
        return this.matrix[2][0];
    }

    public double getW() {
        return this.matrix[3][0];
    }

    public static @NotNull Vector3D toCartesian(@NotNull Vector3DHomo vector) {
        double w = vector.getW();
        double x = vector.getX() / w;
        double y = vector.getY() / w;
        double z = vector.getZ() / w;
        return new Vector3D(x, y, z);
    }
}
