package main.ru.nsu.group21208.porotnikov.matrix;

public class PerspectiveProjectionMatrix extends TransformationMatrix {
    public PerspectiveProjectionMatrix(double nearClip, double fov, double ar) {
        super(new double[][]{
                {nearClip / (Math.tan(fov * Math.PI / 360) * nearClip * ar), 0, 0, 0},
                {0, nearClip / (Math.tan(fov * Math.PI / 360) * nearClip), 0, 0},
                {0, 0, -1, -2 * nearClip},
                {0, 0, -1, 0}
        });
    }
}
