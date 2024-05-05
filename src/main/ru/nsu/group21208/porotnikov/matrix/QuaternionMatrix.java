package main.ru.nsu.group21208.porotnikov.matrix;

public class QuaternionMatrix extends TransformationMatrix {
    public QuaternionMatrix(Vector3D axis, double angle) {
        super();
        double cos = Math.cos(angle * Math.PI / 360);
        double sin = Math.sin(angle * Math.PI / 360);
        double[] q = new double[]{
                cos,
                sin * axis.getX(),
                sin * axis.getY(),
                sin * axis.getZ()
        };
        double m = Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
        q[0] /= m;
        q[1] /= m;
        q[2] /= m;
        q[3] /= m;
        double s = q[0];
        double x = q[1];
        double y = q[2];
        double z = q[3];

        this.matrix = new double[][]{
                {1 - 2 * y * y - 2 * z * z, 2 * x * y - 2 * s * z, 2 * x * z + 2 * s * y, 0},
                {2 * x * y + 2 * s * z, 1 - 2 * x * x - 2 * z * z, 2 * y * z - 2 * s * x, 0},
                {2 * x * z - 2 * s * y, 2 * y * z + 2 * s * x, 1 - 2 * x * x - 2 * y * y, 0},
                {0, 0, 0, 1}
        };
    }
}
