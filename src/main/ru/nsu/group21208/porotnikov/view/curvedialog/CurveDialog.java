package main.ru.nsu.group21208.porotnikov.view.curvedialog;

import main.ru.nsu.group21208.porotnikov.Curve;
import main.ru.nsu.group21208.porotnikov.view.scene.Scene;

import javax.swing.*;
import java.awt.*;

public class CurveDialog extends JFrame {
    private CurveView             curveView;
    private CurveParametersEditor parametersEditor;
    private Scene                 scene;

    public CurveDialog() {
        super("B-Spline configure");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(640, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        curveView = new CurveView();
        add(curveView);
        parametersEditor = new CurveParametersEditor(curveView, this);
        add(parametersEditor, BorderLayout.SOUTH);
        pack();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        parametersEditor.setScene(scene);
        scene.setCurve(curveView.getCurve());
        scene.setM(parametersEditor.getM());
        scene.setM1(parametersEditor.getM1());
    }

    public Curve getCurve() {
        return curveView.getCurve();
    }

    public void setM(int M) {
        parametersEditor.setM(M);
    }

    public void setM1(int M1) {
        parametersEditor.setM1(M1);
    }

    public int getM() {
        return parametersEditor.getM();
    }

    public int getM1() {
        return parametersEditor.getM1();
    }
}
