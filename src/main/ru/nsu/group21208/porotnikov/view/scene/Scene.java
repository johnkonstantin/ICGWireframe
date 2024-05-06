package main.ru.nsu.group21208.porotnikov.view.scene;

import main.ru.nsu.group21208.porotnikov.Curve;
import main.ru.nsu.group21208.porotnikov.matrix.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Scene extends JPanel {
    private Curve                curve;
    private int                  M    = 5000;
    private int                  M1   = 1;
    private double               zn   = 2;
    private double               fovy = 10;
    private double               camx = -10;
    private TransformationMatrix rot  = new RotationMatrix(0, RotationMatrix.RotationAxis.AxisX);
    private int                  x1;
    private int                  y1;


    private Vector3DHomo[][] getRotatedCurve() {
        Point[][]        curvePoints  = this.curve.getCurvePoints();
        Vector3DHomo[][] curveVectors = new Vector3DHomo[this.M][curvePoints.length * (curvePoints[0].length - 1) + 1];

        for (int i = 0; i < this.M; ++i) {
            int m = 0;
            for (int j = 0; j < curvePoints.length; ++j) {
                for (int k = 0; k < curvePoints[0].length - 1; ++k) {
                    curveVectors[i][m++] = new Vector3DHomo(
                            curvePoints[j][k].y * Math.cos(i * 2 * Math.PI / this.M),
                            curvePoints[j][k].y * Math.sin(i * 2 * Math.PI / this.M),
                            curvePoints[j][k].x,
                            1.0
                    );
                }
            }
        }
        for (int i = 0; i < this.M; ++i) {
            curveVectors[i][curvePoints.length * (curvePoints[0].length - 1)] = new Vector3DHomo(
                    curvePoints[curvePoints.length - 1][curvePoints[0].length - 1].y *
                    Math.cos(i * 2 * Math.PI / this.M),
                    curvePoints[curvePoints.length - 1][curvePoints[0].length - 1].y *
                    Math.sin(i * 2 * Math.PI / this.M),
                    curvePoints[curvePoints.length - 1][curvePoints[0].length - 1].x,
                    1.0
            );
        }

        return curveVectors;
    }

    private Vector3DHomo[][] getCircles() {
        Point[][]        curvePoints    = this.curve.getCurvePoints();
        Vector3DHomo[][] circlesVectors = new Vector3DHomo[curvePoints.length + 1][this.M * this.M1 + 1];

        for (int i = 0; i < curvePoints.length; ++i) {
            int m = 0;
            for (int j = 0; j < this.M; ++j) {
                for (int k = 0; k < this.M1; ++k) {
                    circlesVectors[i][m++] = new Vector3DHomo(
                            curvePoints[i][0].y * Math.cos(2 * Math.PI * (j * this.M1 + k) / (this.M * this.M1)),
                            curvePoints[i][0].y * Math.sin(2 * Math.PI * (j * this.M1 + k) / (this.M * this.M1)),
                            curvePoints[i][0].x,
                            1.0
                    );
                }
            }
            circlesVectors[i][m] = new Vector3DHomo(
                    curvePoints[i][0].y,
                    0,
                    curvePoints[i][0].x,
                    1.0
            );
        }

        int m = 0;
        for (int i = 0; i < this.M; ++i) {
            for (int j = 0; j < this.M1; ++j) {
                circlesVectors[curvePoints.length][m++] = new Vector3DHomo(
                        curvePoints[curvePoints.length - 1][curvePoints[0].length - 1].y *
                        Math.cos(2 * Math.PI * (i * this.M1 + j) / (this.M * this.M1)),
                        curvePoints[curvePoints.length - 1][curvePoints[0].length - 1].y *
                        Math.sin(2 * Math.PI * (i * this.M1 + j) / (this.M * this.M1)),
                        curvePoints[curvePoints.length - 1][curvePoints[0].length - 1].x,
                        1.0
                );
            }
        }
        circlesVectors[curvePoints.length][m] = new Vector3DHomo(
                curvePoints[curvePoints.length - 1][curvePoints[0].length - 1].y,
                0,
                curvePoints[curvePoints.length - 1][curvePoints[0].length - 1].x,
                1.0
        );

        return circlesVectors;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.curve != null) {
            Vector3DHomo xAxis     = Vector3D.toHomogeneous(new Vector3D(1, 0, 0));
            Vector3DHomo yAxis     = Vector3D.toHomogeneous(new Vector3D(0, 1, 0));
            Vector3DHomo zAxis     = Vector3D.toHomogeneous(new Vector3D(0, 0, 1));
            Vector3DHomo center    = Vector3D.toHomogeneous(new Vector3D(0, 0, 0));
            Vector3D     cameraPos = new Vector3D(this.camx, 0, 0);
            Vector3D     up        = new Vector3D(0, 1, 0);

            Vector3DHomo[][] curveVectors   = getRotatedCurve();
            Vector3DHomo[][] circlesVectors = getCircles();

            double minX = Double.POSITIVE_INFINITY;
            double maxX = Double.NEGATIVE_INFINITY;
            double minY = Double.POSITIVE_INFINITY;
            double maxY = Double.NEGATIVE_INFINITY;
            double minZ = Double.POSITIVE_INFINITY;
            double maxZ = Double.NEGATIVE_INFINITY;

            for (int i = 0; i < curveVectors.length; ++i) {
                for (int j = 0; j < curveVectors[0].length; ++j) {
                    double x = curveVectors[i][j].getX();
                    double y = curveVectors[i][j].getY();
                    double z = curveVectors[i][j].getZ();
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    minZ = Math.min(minZ, z);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                    maxZ = Math.max(maxZ, z);
                }
            }

            for (int i = 0; i < circlesVectors.length; ++i) {
                for (int j = 0; j < circlesVectors[0].length; ++j) {
                    double x = circlesVectors[i][j].getX();
                    double y = circlesVectors[i][j].getY();
                    double z = circlesVectors[i][j].getZ();
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    minZ = Math.min(minZ, z);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                    maxZ = Math.max(maxZ, z);
                }
            }

            double   centerX     = (maxX + minX) / 2;
            double   centerY     = (maxY + minY) / 2;
            double   centerZ     = (maxZ + minZ) / 2;
            Vector3D modelCenter = new Vector3D(centerX, centerY, centerZ);
            double   kFactor     = Math.max(Math.max((maxX - minX), (maxY - minY)), (maxZ - minZ));

            TransformationMatrix modelView = new ModelViewMatrix(
                    modelCenter, new double[]{1 / kFactor, 1 / kFactor, 1 / kFactor}, this.rot, cameraPos, up);
            TransformationMatrix axisView = new ModelViewMatrix(
                    new Vector3D(0, 0, 0), new double[]{0.1, 0.1, 0.1}, this.rot, cameraPos, up);
            TransformationMatrix proj = new PerspectiveProjectionMatrix(
                    0.1, this.fovy, (double) getWidth() / getHeight());
            TransformationMatrix viewport = new ViewPortMatrix(getWidth(), getHeight(), -10, -10, 0.1, 100);
            TransformationMatrix modelTransform = TransformationMatrix.composeTransformation(
                    modelView, TransformationMatrix.composeTransformation(proj, viewport));
            TransformationMatrix axisTransform = TransformationMatrix.composeTransformation(
                    axisView, TransformationMatrix.composeTransformation(proj, viewport));

            xAxis  = TransformationMatrix.transform(axisTransform, xAxis);
            yAxis  = TransformationMatrix.transform(axisTransform, yAxis);
            zAxis  = TransformationMatrix.transform(axisTransform, zAxis);
            center = TransformationMatrix.transform(axisTransform, center);

            Vector3D xV = Vector3DHomo.toCartesian(xAxis);
            Vector3D yV = Vector3DHomo.toCartesian(yAxis);
            Vector3D zV = Vector3DHomo.toCartesian(zAxis);
            Vector3D cV = Vector3DHomo.toCartesian(center);

            ((Graphics2D) g).setStroke(new BasicStroke(2));
            g.setColor(Color.BLUE);
            g.drawLine(
                    (int) cV.getX(), (int) cV.getY(),
                    (int) xV.getX(),
                    (int) xV.getY()
                      );
            g.setColor(Color.GREEN);
            g.drawLine(
                    (int) cV.getX(), (int) cV.getY(),
                    (int) yV.getX(),
                    (int) yV.getY()
                      );
            g.setColor(Color.RED);
            g.drawLine(
                    (int) cV.getX(), (int) cV.getY(),
                    (int) zV.getX(),
                    (int) zV.getY()
                      );


            for (int i = 0; i < curveVectors.length; ++i) {
                for (int j = 0; j < curveVectors[0].length; ++j) {
                    curveVectors[i][j] = TransformationMatrix.transform(modelTransform, curveVectors[i][j]);
                }
            }

            for (int i = 0; i < circlesVectors.length; ++i) {
                for (int j = 0; j < circlesVectors[0].length; ++j) {
                    circlesVectors[i][j] = TransformationMatrix.transform(modelTransform, circlesVectors[i][j]);
                }
            }

            minZ = Double.POSITIVE_INFINITY;
            maxZ = Double.NEGATIVE_INFINITY;

            for (Vector3DHomo[] line : curveVectors) {
                for (Vector3DHomo vector : line) {
                    minZ = Math.min(minZ, Vector3DHomo.toCartesian(vector).getZ());
                    maxZ = Math.max(maxZ, Vector3DHomo.toCartesian(vector).getZ());
                }
            }

            g.setColor(Color.BLACK);
            for (int i = 0; i < curveVectors.length; ++i) {
                for (int j = 0; j < curveVectors[0].length - 1; ++j) {
                    Vector3D vector1 = Vector3DHomo.toCartesian(curveVectors[i][j]);
                    Vector3D vector2 = Vector3DHomo.toCartesian(curveVectors[i][j + 1]);
                    double   z       = (vector1.getZ() + vector2.getZ()) / 2;
                    double   normZ   = (z - minZ) / (maxZ - minZ);
                    int      c       = (int) (normZ * normZ * 255);
                    g.setColor(new Color(c, c, 0));
                    g.drawLine((int) vector1.getX(), (int) vector1.getY(), (int) vector2.getX(), (int) vector2.getY());
                }
            }

            for (int i = 0; i < circlesVectors.length; ++i) {
                for (int j = 0; j < circlesVectors[0].length - 1; ++j) {
                    Vector3D vector1 = Vector3DHomo.toCartesian(circlesVectors[i][j]);
                    Vector3D vector2 = Vector3DHomo.toCartesian(circlesVectors[i][j + 1]);
                    double   z       = (vector1.getZ() + vector2.getZ()) / 2;
                    double   normZ   = (z - minZ) / (maxZ - minZ);
                    normZ = Math.min(1, Math.max(0, normZ));
                    int c = (int) (normZ * normZ * 255);
                    g.setColor(new Color(c, c, 0));
                    g.drawLine((int) vector1.getX(), (int) vector1.getY(), (int) vector2.getX(), (int) vector2.getY());
                }
            }
        }

    }

    public Scene() {
        super();
        setMinimumSize(new Dimension(640, 480));
        setBackground(Color.WHITE);
        addMouseWheelListener(e -> {
            fovy += e.getPreciseWheelRotation();
            fovy = Math.min(170, Math.max(2, fovy));
            repaint();
        });

        curve = new Curve(new Point[]{
                new Point(-30, 10),
                new Point(0, 10),
                new Point(30, 10),
                new Point(60, 10)
        }, 1);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

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
                if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0 && e.getX() >= 0 &&
                    e.getX() < getWidth() && e.getY() >= 0 && e.getY() < getHeight()) {
                    int    x2 = e.getX();
                    int    y2 = e.getY();
                    double dx = x2 - x1;
                    double dy = y2 - y1;
                    RotationMatrix rotX = new RotationMatrix(
                            dx / getWidth() * 5, RotationMatrix.RotationAxis.AxisY);
                    RotationMatrix rotY = new RotationMatrix(
                            dy / getHeight() * 5, RotationMatrix.RotationAxis.AxisZ);
                    TransformationMatrix rotXY = TransformationMatrix.composeTransformation(rotX, rotY);
                    rot = TransformationMatrix.composeTransformation(rot, rotXY);
                    x1  = x2;
                    y1  = y2;
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public boolean setCurve(Curve curve) {
        if (curve == null) {
            return false;
        }
        this.curve = curve;
        repaint();
        return true;
    }

    public boolean setM(int M) {
        if (M < 2) {
            return false;
        }
        this.M = M;
        repaint();
        return true;
    }

    public boolean setM1(int M1) {
        if (M1 < 1) {
            return false;
        }
        this.M1 = M1;
        repaint();
        return true;
    }

    public void resetRotations() {
        rot = new RotationMatrix(0, RotationMatrix.RotationAxis.AxisX);
        repaint();
    }

    public TransformationMatrix getRotations() {
        return new TransformationMatrix(rot.getDoubleArray().clone());
    }

    public double getFOV() {
        return fovy;
    }

    public void setFOV(double fov) {
        this.fovy = fov;
        repaint();
    }

    public void setRotation(TransformationMatrix rot) {
        this.rot = rot;
        repaint();
    }
}
