package main.ru.nsu.group21208.porotnikov.view.curvedialog;

import main.ru.nsu.group21208.porotnikov.Curve;
import main.ru.nsu.group21208.porotnikov.view.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurveParametersEditor extends JPanel {
    private JSpinner    nEditor;
    private JSpinner    basePointsEditor;
    private JSpinner    mEditor;
    private JSpinner    m1Editor;
    private CurveView   curveView;
    private Scene       scene;
    private CurveDialog curveDialog;

    public CurveParametersEditor(CurveView curveView, CurveDialog curveDialog) {
        super();
        if (curveView == null) {
            throw new RuntimeException("CurveView parameter must be not null!");
        }
        this.curveView = curveView;
        this.curveView.setParametersEditor(this);
        this.curveDialog = curveDialog;
        JLabel  nLabel          = new JLabel("N");
        JLabel  basePointsLabel = new JLabel("K");
        JLabel  mLabel          = new JLabel("M");
        JLabel  m1Label         = new JLabel("M1");
        JButton okButton        = new JButton("OK");
        JButton applyButton     = new JButton("Apply");
        nEditor = new JSpinner(new SpinnerNumberModel(curveView.getCurveN(), 1, 100, 1));
        nEditor.addChangeListener(e -> curveView.setCurveN((Integer) ((JSpinner) e.getSource()).getValue()));
        basePointsEditor = new JSpinner(new SpinnerNumberModel(curveView.getCurveBasePointsNum(), 4, 50, 1));
        basePointsEditor.addChangeListener(
                e -> curveView.setCurveBasePointsNum((Integer) ((JSpinner) e.getSource()).getValue()));
        mEditor  = new JSpinner(new SpinnerNumberModel(3, 2, 100, 1));
        m1Editor = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        add(nLabel);
        add(nEditor);
        add(basePointsLabel);
        add(basePointsEditor);
        add(mLabel);
        add(mEditor);
        add(m1Label);
        add(m1Editor);
        add(okButton);
        add(applyButton);
        JButton normButton = new JButton("Norm");
        add(normButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scene != null) {
                    scene.setCurve(curveView.getCurve());
                    scene.setM((Integer) mEditor.getValue());
                    scene.setM1((Integer) m1Editor.getValue());
                }
                curveDialog.setVisible(false);
            }
        });
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scene != null) {
                    scene.setCurve(curveView.getCurve());
                    scene.setM((Integer) mEditor.getValue());
                    scene.setM1((Integer) m1Editor.getValue());
                }
            }
        });
        normButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point[] newBase = curveView.getCurve().getBasePoints();
                double  minX    = Double.POSITIVE_INFINITY;
                double  maxX    = Double.NEGATIVE_INFINITY;
                double  minY    = Double.POSITIVE_INFINITY;
                double  maxY    = Double.NEGATIVE_INFINITY;


                for (int i = 0; i < newBase.length; ++i) {
                    minX = Math.min(minX, newBase[i].x);
                    maxX = Math.max(maxX, newBase[i].x);
                    minY = Math.min(minY, newBase[i].y);
                    maxY = Math.max(maxY, newBase[i].y);
                }

                double centerX = (maxX + minX) / 2;
                double centerY = (maxY + minY) / 2;
                double k       = Math.max((maxX - minX) / curveView.getWidth(), (maxY - minY) / curveView.getHeight());

                curveView.setCenter(new Point((int) -centerX, (int) centerY));
                curveView.setScale(0.9 / k);
                curveView.setCurve(new Curve(newBase, curveView.getCurveN()));
            }
        });
        setMaximumSize(new Dimension(999999999, applyButton.getHeight() + 50));
    }

    public boolean setBasePointsNum(int K) {
        try {
            this.basePointsEditor.setValue(K);
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void setM(int M) {
        mEditor.setValue(M);
        if (scene != null) {
            scene.setM(M);
        }
    }

    public void setM1(int M1) {
        m1Editor.setValue(M1);
        if (scene != null) {
            scene.setM1(M1);
        }
    }

    public int getM() {
        return (Integer) mEditor.getValue();
    }

    public int getM1() {
        return (Integer) m1Editor.getValue();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
