package main.ru.nsu.group21208.porotnikov.view.curvedialog;

import javax.swing.*;
import java.awt.*;

public class CurveDialog extends JFrame {
    private CurveView curveView;
    private CurveParametersEditor parametersEditor;

    public CurveDialog() {
        super("B-Spline configure");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(640, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        curveView = new CurveView();
        add(curveView);
        parametersEditor = new CurveParametersEditor(curveView);
        add(parametersEditor, BorderLayout.SOUTH);
        pack();
    }
}
