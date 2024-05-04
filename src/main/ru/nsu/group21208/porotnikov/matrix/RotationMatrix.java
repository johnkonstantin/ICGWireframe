package main.ru.nsu.group21208.porotnikov.matrix;

import org.jetbrains.annotations.NotNull;

public class RotationMatrix extends TransformationMatrix {
    public enum RotationAxis {
        AxisX,
        AxisY,
        AxisZ
    }
    public RotationMatrix(double angle, @NotNull RotationAxis axis) {
        super();
        this.matrix[3][3] = 1;
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        switch (axis) {
            case AxisX -> {
                this.matrix[0][0] = 1;
                this.matrix[1][1] = c;
                this.matrix[1][2] = -s;
                this.matrix[2][1] = s;
                this.matrix[2][2] = c;
            }
            case AxisY -> {
                this.matrix[0][0] = c;
                this.matrix[0][2] = s;
                this.matrix[1][1] = 1;
                this.matrix[2][0] = -s;
                this.matrix[2][2] = c;
            }
            case AxisZ -> {
                this.matrix[0][0] = c;
                this.matrix[0][1] = -s;
                this.matrix[1][0] = s;
                this.matrix[1][1] = c;
                this.matrix[2][2] = 1;
            }
        }
    }
}
