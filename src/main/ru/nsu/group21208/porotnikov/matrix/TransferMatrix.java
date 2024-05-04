package main.ru.nsu.group21208.porotnikov.matrix;

public class TransferMatrix extends TransformationMatrix {
    public TransferMatrix(double dx, double dy, double dz) {
        super();
        this.matrix[0][0] = 1;
        this.matrix[1][1] = 1;
        this.matrix[2][2] = 1;
        this.matrix[3][3] = 1;
        this.matrix[0][3] = dx;
        this.matrix[1][3] = dy;
        this.matrix[2][3] = dz;
    }
}
