import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorMenu{
    private DrawController controller = null;
    public ColorMenuModel model;
    public ColorMenuView view;

    private ColorButton[] buttongroup;
    private ColorButton foreColorBtn;
    private JColorChooser chooser;
    private thickPanel thickness;
    private JSlider thicknesschooser;

    public ColorMenu(){
        this.model = new ColorMenuModel();
        this.view = new ColorMenuView();
    }

    public void initialize(DrawController controller)
    {
        this.controller = controller;
        int colorSize = controller.getColorSize();
        buttongroup = new ColorButton[colorSize];
        for (int i = 0; i < colorSize; i++)
        {
            this.buttongroup[i] = new ColorButton(controller, controller.getColorByIndex(i));
            this.view.add(buttongroup[i]);
        }
        foreColorBtn = new ColorButton(controller, controller.getForeColor());
        chooser = new JColorChooser();
        thickness = new thickPanel(model.getThickWidth(), model.getThickHeight());
        thickness.initialize(model.getThickness());
        thicknesschooser = new JSlider(0,model.getSliderMax(),model.getThickness());
        thickness.repaint();

        this.view.add(foreColorBtn);
        this.view.add(thickness);
        this.view.add(thicknesschooser);
        this.view.initialize(controller, this.model);

        this.addListener();
    }

    public void addListener()
    {
        for (ColorButton btn:this.buttongroup)
        {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JButton btn = (JButton)e.getSource();
                    controller.changeForeColor(btn.getBackground());
                }
            });
        }

        foreColorBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.changeForeColor(chooser.showDialog(chooser, "chooseColor", Color.white));
            }
        });

        this.thicknesschooser.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider)e.getSource();
                controller.updateThickness(s.getValue());
            }
        });
    }

    public void setScale(float scale)
    {
        thickness.setScale(scale);
        thickness.repaint();
    }

    public void updateThickness(int value)
    {
        thickness.setWidth(value);
    }

    public void updateForeColor()
    {
        if (null != foreColorBtn)
        {
            foreColorBtn.updateForeColor();
        }
    }
}


class thickPanel extends JPanel{
    private int width;
    private int w;
    private int h;
    private float scale;

    thickPanel(int w, int h)
    {
        this.w = w;
        this.h = h;
    }

    public void initialize(int width)
    {
        this.setPreferredSize(new Dimension(w,h));
        this.width = width;
    }

    public void setWidth(int width)
    {
        this.width = width;
        repaint();
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void paintComponent(Graphics g)
    {
        int border = (int)((float)(10-width)*scale);
        int newWidth = (int)((float)width*scale);
        g.fillOval(border, border, 2*newWidth,2*newWidth);
    }
}


