package main.ru.nsu.group21208.porotnikov;

import main.ru.nsu.group21208.porotnikov.matrix.TransformationMatrix;

import java.io.Serializable;

public class StateObject implements Serializable {
    private static final long                 serialVersionUID = 1111111111L;
    public               TransformationMatrix rotation;
    public               Curve                curve;
    public               double               fov;
    public               int                  M;
    public               int                  M1;
    public               int                  secret           = 1883775482;
}
