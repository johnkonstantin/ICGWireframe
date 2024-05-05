package main.ru.nsu.group21208.porotnikov.matrix;

public class ViewPortMatrix extends TransformationMatrix {
    public ViewPortMatrix(int w, int h, int leftClip, int bottomClip, double nearClip, double farClip) {
        super(new double[][]{
                {w / 2.0, 0, 0, leftClip + w / 2.0},
                {0, -h / 2.0, 0, bottomClip + h / 2.0},
                {0, 0, (farClip - nearClip) / 2, (farClip + nearClip) / 2},
                {0, 0, 0, 1}
        });
    }
}
