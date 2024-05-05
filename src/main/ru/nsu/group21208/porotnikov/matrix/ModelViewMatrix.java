package main.ru.nsu.group21208.porotnikov.matrix;

import org.jetbrains.annotations.NotNull;

public class ModelViewMatrix extends TransformationMatrix {
    public ModelViewMatrix(@NotNull Vector3D modelCenter, double @NotNull [] scale, @NotNull RotationMatrix modelRot,
                           @NotNull Vector3D cameraPos,
                           @NotNull Vector3D cameraUp
                          ) {
        super();
        Vector3D cameraForward = new Vector3D(-cameraPos.getX(), -cameraPos.getY(), -cameraPos.getZ());
        cameraForward = Vector3D.normalize(cameraForward);
        Vector3D cameraLeft = Vector3D.crossProduct(cameraUp, cameraForward);
        cameraLeft = Vector3D.normalize(cameraLeft);
        cameraUp   = Vector3D.crossProduct(cameraForward, cameraLeft);

        TransformationMatrix view = new TransformationMatrix(new double[][]{
                {cameraLeft.getX(), cameraUp.getX(), cameraForward.getX(), -cameraPos.getZ()},
                {cameraLeft.getY(), cameraUp.getY(), cameraForward.getY(), -cameraPos.getY()},
                {cameraLeft.getZ(), cameraUp.getZ(), cameraForward.getZ(), cameraPos.getX()},
                {0, 0, 0, 1}
        });

        TransferMatrix transferMatrix = new TransferMatrix(
                -modelCenter.getX(), -modelCenter.getY(), -modelCenter.getZ());
        ScaleMatrix          scaleMatrix = new ScaleMatrix(scale[0], scale[1], scale[2]);
        TransformationMatrix model       = TransformationMatrix.composeTransformation(transferMatrix, scaleMatrix);
        model = TransformationMatrix.composeTransformation(model, modelRot);

        TransformationMatrix modelView = TransformationMatrix.composeTransformation(model, view);
        this.matrix = modelView.getDoubleArray();
    }
}
