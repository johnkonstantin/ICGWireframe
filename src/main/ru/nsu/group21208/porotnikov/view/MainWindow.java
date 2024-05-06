package main.ru.nsu.group21208.porotnikov.view;

import main.ru.nsu.group21208.porotnikov.view.curvedialog.CurveDialog;
import main.ru.nsu.group21208.porotnikov.view.scene.Scene;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private CurveDialog curveDialog;
    private Scene       scene;
    private MenuPanel   menu;

    public MainWindow() {
        super("ICGWireframe");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(640, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        curveDialog = new CurveDialog();
        scene       = new Scene();
        curveDialog.setScene(scene);
        menu = new MenuPanel(curveDialog, scene);
        add(menu, BorderLayout.NORTH);
        add(scene);
        pack();
        curveDialog.setVisible(false);
    }
}
