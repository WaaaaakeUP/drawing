import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ControlBarView extends JPanel{
    DrawController controller;
    ControlBarModel model;

    public Image redoimage;
    public Image undoimage;
    public Image foreimage;
    public Image backimage;
    public Image pauseimage;

    ControlBarView(ControlBarModel model) { this.model = model; }

    public void initialize(DrawController controller)
    {
        this.controller = controller;
        this.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
        this.setBackground(model.getBackground());
        for (Component component: this.getComponents())
        {
            if (component instanceof JButton)
            {
                component.setPreferredSize(new Dimension(model.getBtnSize(), model.getBtnSize()));
            }
            else if (component instanceof JSlider)
            {
                component.setPreferredSize(new Dimension(model.getSliderWidth(), model.getSliderHeight()));
            }
        }

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newIconSize = model.getIconSize() * getWidth()/getPreferredSize().width;
                redoimage = new ImageIcon(this.getClass().getResource(model.getRedoIconPath())).getImage().
                        getScaledInstance(newIconSize, newIconSize, Image.SCALE_SMOOTH);
                undoimage = new ImageIcon(this.getClass().getResource(model.getUndoIconPath())).getImage().
                        getScaledInstance(newIconSize,newIconSize,Image.SCALE_SMOOTH);
                foreimage = new ImageIcon(this.getClass().getResource(model.getForeIconPath())).getImage().
                        getScaledInstance(newIconSize,newIconSize,Image.SCALE_SMOOTH);
                backimage = new ImageIcon(this.getClass().getResource(model.getBackIconPath())).getImage().
                        getScaledInstance(newIconSize,newIconSize,Image.SCALE_SMOOTH);
                controller.resetBtnIcon();
                setLayout(new ControlBarLayout());
            }
        });

        redoimage = new ImageIcon(this.getClass().getResource(model.getRedoIconPath())).getImage().
                getScaledInstance(model.getIconSize(),model.getIconSize(),Image.SCALE_SMOOTH);

        undoimage = new ImageIcon(this.getClass().getResource(model.getUndoIconPath())).getImage().
                getScaledInstance(model.getIconSize(),model.getIconSize(),Image.SCALE_SMOOTH);

        foreimage = new ImageIcon(this.getClass().getResource(model.getForeIconPath())).getImage().
                getScaledInstance(model.getIconSize(),model.getIconSize(),Image.SCALE_SMOOTH);

        backimage = new ImageIcon(this.getClass().getResource(model.getBackIconPath())).getImage().
                getScaledInstance(model.getIconSize(),model.getIconSize(),Image.SCALE_SMOOTH);

        pauseimage = new ImageIcon(this.getClass().getResource(model.getPauseIconPath())).getImage().
                getScaledInstance(model.getIconSize(),model.getIconSize(),Image.SCALE_SMOOTH);

        this.setLayout(new ControlBarLayout());
    }
}


class ControlBarLayout implements LayoutManager {
    public void addLayoutComponent(String name, Component comp) {
        // no-op
    }

    public void layoutContainer(Container parent) {
        Component [] components = parent.getComponents();
        Dimension parentSize = parent.getSize();
        Point pos = parent.getLocation();
        Component slider = components[0];
        int defaultBoarder = 5;
        int defaultSize = 60;
        int currentHeight = parentSize.height;
        int preferHeight = parent.getPreferredSize().height;
        int currentSize = defaultSize*currentHeight/preferHeight;
        int currentBoarder = defaultBoarder*currentHeight/preferHeight;
        int currentSliderHeight = slider.getPreferredSize().height*currentHeight/preferHeight;
        slider.setSize(parentSize.width-4*currentSize, currentSliderHeight);
        slider.setLocation(0,(parentSize.height-slider.getHeight())/2);
        for (int i = 1; i < components.length; i++)
        {
            Component btn = components[i];
            btn.setSize(currentSize-2*currentBoarder, currentSize-2*currentBoarder);
            btn.setLocation(slider.getWidth()+(i-1)*currentSize+currentBoarder,
                    (parentSize.height-currentSize)/2+currentBoarder);
        }

    }

    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(800, 80);
    }

    public Dimension preferredLayoutSize(Container parent) { return parent.getPreferredSize(); }

    public void removeLayoutComponent(Component comp) {
        // no-op
    }
}