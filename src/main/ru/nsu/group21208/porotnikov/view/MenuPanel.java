package main.ru.nsu.group21208.porotnikov.view;

import main.ru.nsu.group21208.porotnikov.Curve;
import main.ru.nsu.group21208.porotnikov.StateObject;
import main.ru.nsu.group21208.porotnikov.matrix.TransformationMatrix;
import main.ru.nsu.group21208.porotnikov.view.curvedialog.CurveDialog;
import main.ru.nsu.group21208.porotnikov.view.scene.Scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class MenuPanel extends JMenuBar {
    CurveDialog curveDialog;
    Scene       scene;
    private final String[][][] _menus = {
            {
                    {"File", "F", ""},
                    {"Open", "O", "O"},
                    {"Save", "S", "S"},
                    {"Exit", "E", "X"}
            },
            {
                    {"Tools", "T", ""},
                    {"Curve Editor", "", ""},
                    {"Reset rotations", "", ""}
            },
            {
                    {"Help", "H", ""},
                    {"About", "A", "A"}
            }
    };

    public MenuPanel(CurveDialog curveDialog, Scene scene) {
        super();
        this.curveDialog = curveDialog;
        this.scene       = scene;
        for (String[][] menuConf : _menus) {
            JMenu menu = new JMenu(menuConf[0][0]);
            if (menuConf[0][1].length() != 0) {
                menu.setMnemonic(menuConf[0][1].charAt(0));
            }
            for (int i = 1; i < menuConf.length; ++i) {
                JMenuItem item = new JMenuItem(menuConf[i][0]);
                if (menuConf[i][1].length() != 0) {
                    item.setMnemonic(menuConf[i][1].charAt(0));
                }
                if (menuConf[i][2].length() != 0) {
                    item.setAccelerator(KeyStroke.getKeyStroke(menuConf[i][2].charAt(0), KeyEvent.CTRL_MASK));
                }
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switch (((JMenuItem) e.getSource()).getText()) {
                            case "Curve Editor" -> {
                                curveDialog.setVisible(true);
                            }
                            case "Reset rotations" -> {
                                scene.resetRotations();
                            }
                            case "Exit" -> {
                                System.exit(0);
                            }
                            case "Open" -> {
                                JFileChooser fileChooser = new JFileChooser();
                                int          res         = fileChooser.showOpenDialog(null);
                                if (res == JFileChooser.APPROVE_OPTION) {
                                    File in = fileChooser.getSelectedFile();
                                    try {
                                        FileInputStream   inputStream       = new FileInputStream(in);
                                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                                        StateObject stateObject
                                                = (StateObject) objectInputStream.readObject();
                                        TransformationMatrix rot   = stateObject.rotation;
                                        double               fov   = stateObject.fov;
                                        Curve                curve = stateObject.curve;
                                        int                  M     = stateObject.M;
                                        int                  M1    = stateObject.M1;

                                        scene.setCurve(curve);
                                        scene.setRotation(rot);
                                        scene.setFOV(fov);
                                        curveDialog.setM(M);
                                        curveDialog.setM1(M1);
                                    }
                                    catch (Exception ex) {
                                        JOptionPane.showMessageDialog(null, "Wrong file!");
                                    }
                                }
                            }
                            case "Save" -> {
                                JFileChooser fileChooser = new JFileChooser();
                                int          res         = fileChooser.showSaveDialog(null);
                                if (res == JFileChooser.APPROVE_OPTION) {
                                    File out = new File(fileChooser.getSelectedFile().getAbsolutePath());
                                    try {
                                        FileOutputStream   outputStream       = new FileOutputStream(out);
                                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                                        StateObject        stateObject        = new StateObject();
                                        stateObject.rotation = scene.getRotations();
                                        stateObject.curve    = curveDialog.getCurve();
                                        stateObject.fov      = scene.getFOV();
                                        stateObject.M        = curveDialog.getM();
                                        stateObject.M1       = curveDialog.getM1();
                                        objectOutputStream.writeObject(stateObject);
                                        objectOutputStream.close();

                                    }
                                    catch (Exception ex) {
                                        JOptionPane.showMessageDialog(null, "Wrong file!");
                                    }
                                }
                            }
                            case "About" -> {
                                String helpStr = """
                                                 <html>ICGWireframe App.<br>
                                                 Designed by Porotnikov Danil, NSU, 2024""";
                                JOptionPane.showMessageDialog(null, new JLabel(helpStr),
                                                              "About", JOptionPane.INFORMATION_MESSAGE
                                                             );
                            }
                        }
                    }
                });
                menu.add(item);
            }
            add(menu);
        }

        add(Box.createHorizontalGlue());
    }
}
