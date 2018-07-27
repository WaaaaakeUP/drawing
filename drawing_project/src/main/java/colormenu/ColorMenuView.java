import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ColorMenuView extends JPanel {
    private DrawController controller;
    private ColorMenuModel model;

    public void initialize(DrawController controller, ColorMenuModel model)
    {
        this.controller = controller;
        this.model = model;
        this.setBackground(model.getBackground());
        this.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
        this.setLayout(new ColorMenuLayout());
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                float scale = (float) getWidth()/(float) getPreferredSize().width;
                controller.setThicknessScale(scale);
                setLayout(new ColorMenuLayout());
            }
        });
    }
}


class ColorMenuLayout implements LayoutManager {
    public void addLayoutComponent(String name, Component comp) {
        // no-op
    }

    public void layoutContainer(Container parent) {
        Component [] components = parent.getComponents();
        Dimension parentSize = parent.getSize();
        Point pos = parent.getLocation();
        int currentWidth = parentSize.width/2;
        int defaultWidth = ColorButton.getDefaultWidth();
        int defaultBorderWidth = ColorButton.getDefaultBorderWidth();
        int currentBorderWidth = defaultBorderWidth*currentWidth/defaultWidth;
        int ColorButtonSize = components.length-3;
        for (int i = 0; i < ColorButtonSize; i++) {
            Component btn = components[i];
            btn.setSize(currentWidth-2*currentBorderWidth,
                    currentWidth-2*currentBorderWidth);
            btn.setLocation(i%2 * currentWidth+currentBorderWidth,
                    i/2*currentWidth+currentBorderWidth);
        }
        Component ftColor = components[ColorButtonSize];
        Component thicknessIcon = components[ColorButtonSize+1];
        int remainHeight = currentWidth*ColorButtonSize/2;
        ftColor.setSize(parentSize.width-2*currentBorderWidth,
                (parentSize.width-2*currentBorderWidth)*3/2);
        ftColor.setLocation(currentBorderWidth,remainHeight+currentBorderWidth);
        remainHeight = ftColor.getY()+ ftColor.getHeight();
        currentBorderWidth *= 2;
        int thicknessSize = thicknessIcon.getPreferredSize().width*currentWidth/defaultWidth;
        thicknessIcon.setSize(thicknessSize, thicknessSize);
        thicknessIcon.setLocation(currentBorderWidth,
                remainHeight+ (parentSize.height-remainHeight-thicknessIcon.getHeight())/2);
        Component thicknessSlider = components[ColorButtonSize+2];
        thicknessSlider.setSize(parentSize.width-currentBorderWidth-thicknessSize,
                thicknessSize);
        thicknessSlider.setLocation(currentBorderWidth+thicknessIcon.getWidth(),
                thicknessIcon.getY());
    }

    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(50, 200);
    }

    public Dimension preferredLayoutSize(Container parent) { return parent.getPreferredSize(); }

    public void removeLayoutComponent(Component comp) {
        // no-op
    }
}