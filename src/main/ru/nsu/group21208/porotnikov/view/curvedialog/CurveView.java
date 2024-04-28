package main.ru.nsu.group21208.porotnikov.view.curvedialog;

import main.ru.nsu.group21208.porotnikov.Curve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CurveView extends JPanel {
    private Curve curve = null;
    private int selectedPoint = -1;
    private int isDragged = 0;
    private int centerX;
    private int centerY;
    private CurveParametersEditor parametersEditor = null;

    private final int pointRadius = 15;

    private int findPoint(MouseEvent e) {
        Point[] basePoints = curve.getBasePoints();
        int target = -1;
        for (int i = 0; i < basePoints.length; ++i) {
            int dx = e.getX() - basePoints[i].x - this.getWidth() / 2;
            int dy = e.getY() + basePoints[i].y - this.getHeight() / 2;
            if (dx * dx + dy * dy <= this.pointRadius * this.pointRadius) {
                target = i;
                break;
            }
        }
        return target;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (curve != null) {
            g.setColor(Color.GRAY);
            g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
            g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
            g.setColor(Color.GREEN);
            Point[][] curvePoints = curve.getCurvePoints();
            for (Point[] curvePoint : curvePoints) {
                for (int j = 0; j < curvePoint.length - 1; ++j) {
                    g.drawLine(curvePoint[j].x + this.getWidth() / 2, this.getHeight() / 2 - curvePoint[j].y,
                            curvePoint[j + 1].x + this.getWidth() / 2, this.getHeight() / 2 - curvePoint[j + 1].y);
                }
            }
            Point[] basePoints = curve.getBasePoints();
            for (int i = 0; i < basePoints.length; ++i) {
                g.setColor(Color.BLUE);
                if (i == this.selectedPoint) {
                    g.setColor(Color.GREEN);
                }
                g.drawArc(basePoints[i].x + this.getWidth() / 2 - pointRadius,
                        this.getHeight() / 2 - basePoints[i].y - pointRadius, pointRadius * 2, pointRadius * 2, 0, 360);
            }
            g.setColor(Color.BLUE);
            for (int i = 0; i < basePoints.length - 1; ++i) {
                g.drawLine(basePoints[i].x + this.getWidth() / 2, this.getHeight() / 2 - basePoints[i].y,
                        basePoints[i + 1].x + this.getWidth() / 2, this.getHeight() / 2 - basePoints[i + 1].y);
            }
        }
    }

    public CurveView() {
        super();
        setBackground(Color.BLACK);
        curve = new Curve(new Point[]{
                new Point(-40 * 3, 40 * 2),
                new Point(20 * 3, 50 * 2),
                new Point(50 * 3, -30 * 2),
                new Point(80 * 3, 0),
                new Point(100 * 3, 100 * 2),
                new Point(150 * 3, 0)
        }, 10);
        centerX = 0;
        centerY = 0;

        CurveView parent = this;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int target = findPoint(e);
                if (e.getButton() == MouseEvent.BUTTON3 && parent.curve.getBasePoints().length > 4) {
                    if (target != -1) {
                        ArrayList<Point> basePointsList = new ArrayList<>(Arrays.asList(parent.curve.getBasePoints()));
                        basePointsList.remove(target);
                        Point[] newBasePoints = basePointsList.toArray(new Point[0]);
                        parent.curve = new Curve(newBasePoints, parent.curve.getN());
                        if (target <= parent.selectedPoint) {
                            parent.selectedPoint = -1;
                        }
                        if (parent.parametersEditor != null) {
                            parent.parametersEditor.setBasePointsNum(newBasePoints.length);
                        }
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                    if (target != -1) {
                        parent.selectedPoint = target;
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {
                    if (target == -1) {
                        Point[] basePoints = parent.curve.getBasePoints();
                        Point[] newBasePoints = new Point[basePoints.length + 1];
                        System.arraycopy(basePoints, 0, newBasePoints, 0, basePoints.length);
                        newBasePoints[basePoints.length] = new Point(e.getX() - parent.getWidth() / 2,
                                parent.getHeight() / 2 - e.getY());
                        parent.curve = new Curve(newBasePoints, parent.curve.getN());
                        if (parent.parametersEditor != null) {
                            parent.parametersEditor.setBasePointsNum(newBasePoints.length);
                        }
                    }
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseClicked(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                parent.isDragged = 0;
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0 &&
                        e.getX() >= 0 && e.getX() < parent.getWidth() &&
                        e.getY() >= 0 && e.getY() < parent.getHeight()) {
                    int target;
                    if (parent.isDragged == 0) {
                        target = findPoint(e);
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        parent.isDragged = (target != -1) ? 1 : 0;
                        parent.selectedPoint = (target != -1) ? target : parent.selectedPoint;
                    }
                    else {
                        target = parent.selectedPoint;
                    }
                    if (target != -1) {
                        Point[] newBasePoints = parent.curve.getBasePoints();
                        newBasePoints[target].x = e.getX() - parent.getWidth() / 2;
                        newBasePoints[target].y = parent.getHeight() / 2 - e.getY();
                        parent.curve = new Curve(newBasePoints, parent.curve.getN());
                        repaint();
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public int getCurveN() {
        return this.curve.getN();
    }

    public int getCurveBasePointsNum() {
        return this.curve.getBasePoints().length;
    }

    public boolean setCurveN(int N) {
        if (N < 1) {
            return false;
        }
        this.curve = new Curve(this.curve.getBasePoints(), N);
        repaint();
        return true;
    }

    public boolean setCurveBasePointsNum(int K) {
        if (K < 4) {
            return false;
        }
        Point[] basePoints = this.curve.getBasePoints();
        if (K <= basePoints.length) {
            this.curve = new Curve(Arrays.copyOf(this.curve.getBasePoints(), K), this.curve.getN());
        }
        else {
            int[] dP = new int[basePoints.length - 1];
            int t = K - basePoints.length;
            int ii = 0;
            while (t > 0) {
                dP[ii] += 1;
                --t;
                ++ii;
                if (ii == dP.length) {
                    ii = 0;
                }
            }
            ArrayList<Point> newBasePointsList = new ArrayList<>(Arrays.asList(basePoints.clone()));
            int offset = 0;
            for (int i = 0; i < dP.length; ++i) {
                int dx = basePoints[i + 1].x - basePoints[i].x;
                int dy = basePoints[i + 1].y - basePoints[i].y;
                for (int j = 0; j < dP[i]; ++j) {
                    newBasePointsList.add(i + offset + 1, new Point(basePoints[i].x + dx * (j + 1) / (dP[i] + 1),
                            basePoints[i].y + dy * (j + 1) / (dP[i] + 1)));
                    ++offset;
                }
            }
            Point[] newBasePoints = newBasePointsList.toArray(new Point[0]);
            this.curve = new Curve(newBasePoints, this.curve.getN());
        }
        repaint();
        return true;
    }

    public void setParametersEditor(CurveParametersEditor parametersEditor) {
        if (parametersEditor != null) {
            this.parametersEditor = parametersEditor;
        }
    }
}
