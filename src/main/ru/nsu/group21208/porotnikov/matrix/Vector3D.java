package main.ru.nsu.group21208.porotnikov.matrix;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Vector3D extends Matrix {
    public Vector3D() {
        super(3, 1);
    }

    public Vector3D(double x, double y, double z) {
        super(new double[]{x, y, z}, Orientation.Vertical);
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

    public double getModule() {
        return Math.sqrt(
                getX() * getX() +
                getY() * getY() +
                getZ() * getZ()
                        );
    }

    public static @NotNull Vector3D normalize(@NotNull Vector3D vector) {
        double[][] res = Matrix.mulToNumber(vector, 1.0 / vector.getModule()).getDoubleArray();
        return new Vector3D(res[0][0], res[1][0], res[2][0]);
    }

    public static double dotProduct(@NotNull Vector3D vector1, @NotNull Vector3D vector2) {
        return vector1.getX() * vector2.getX() +
               vector1.getY() * vector2.getY() +
               vector1.getZ() * vector2.getZ();
    }

    @Contract("_, _ -> new")
    public static @NotNull Vector3D crossProduct(@NotNull Vector3D vector1, @NotNull Vector3D vector2) {
        return new Vector3D(
                vector1.getY() * vector2.getZ() - vector1.getZ() * vector2.getY(),
                vector1.getZ() * vector2.getX() - vector1.getX() * vector2.getZ(),
                vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX()
        );
    }

    public static @NotNull Vector3DHomo toHomogeneous(@NotNull Vector3D vector) {
        return new Vector3DHomo(vector.getX(), vector.getY(), vector.getZ());
    }
}
