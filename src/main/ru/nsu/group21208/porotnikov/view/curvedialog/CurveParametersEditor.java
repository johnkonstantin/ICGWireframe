package main.ru.nsu.group21208.porotnikov.view.curvedialog;

import main.ru.nsu.group21208.porotnikov.Curve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurveParametersEditor extends JPanel {
    private JSpinner  nEditor;
    private JSpinner  basePointsEditor;
    private JSpinner  mEditor;
    private JSpinner  m1Editor;
    private CurveView curveView;

    public CurveParametersEditor(CurveView curveView) {
        super();
        if (curveView == null) {
            throw new RuntimeException("CurveView parameter must be not null!");
        }
        this.curveView = curveView;
        this.curveView.setParametersEditor(this);
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
        mEditor  = new JSpinner(new SpinnerNumberModel(2, 2, 100, 1));
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

                for (int i = 0; i < newBase.length; ++i) {
                    newBase[i].x -= centerX;
                    newBase[i].y -= centerY;
                }

                curveView.setCenter(new Point(0, 0));
                curveView.setScale(k * 2);
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
}
