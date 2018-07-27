import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawPadView extends JPanel {
    public DrawController controller = null;
    public DrawPadModel model;

    DrawPadView(DrawPadModel model) {
        this.model = model;
        this.setPreferredSize(new Dimension(680, 490));
        this.setBackground(Color.white);
    }

    public void initialize(DrawController controller) {
        this.controller = controller;
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                model.createNewShape();
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                model.addPoint(e.getX(), e.getY());
                repaint();
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                float scalex = (float) width /  (float) getPreferredSize().width;
                float scaley = (float) height / (float) getPreferredSize().height;
                model.setScale(scalex, scaley);
                repaint();
            }
        });
    }

    public void changeDraw(int sliderValue)
    {
        if (this.model != null)
        {
            this.model.changeDraw(sliderValue);
        }
        repaint();
    }


    // custom graphics drawing
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.scale(model.getScaleX(), model.getScaleY());
        for (int i = 0; i <= model.curDrawIndex; i++)
        {
            Shape myshape = model.shapegroup.get(i);
            if (myshape != null)
            {
                myshape.draw(g2);
            }
        }

    }

}