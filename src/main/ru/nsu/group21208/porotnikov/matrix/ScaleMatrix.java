package main.ru.nsu.group21208.porotnikov.matrix;

public class ScaleMatrix extends TransformationMatrix {
    public ScaleMatrix(double sx, double sy, double sz) {
        super();
        this.matrix[0][0] = sx;
        this.matrix[1][1] = sy;
        this.matrix[2][2] = sz;
        this.matrix[3][3] = 1;
    }
}
