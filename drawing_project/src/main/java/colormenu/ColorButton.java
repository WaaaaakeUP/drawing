import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorButton extends JButton{
    private static int defaultBorderWidth = 3;
    private static int defaultWidth = 60;
    private DrawController controller;

    public static int getDefaultWidth() {
        return defaultWidth;
    }

    public static int getDefaultBorderWidth(){
        return defaultBorderWidth;
    }

    public void updateForeColor(){
        this.setBackground(controller.getForeColor());
    }


    public ColorButton(DrawController controller, Color cColor){
        this.controller = controller;
        this.setPreferredSize(new Dimension(defaultWidth-2*defaultBorderWidth,
                defaultWidth-2*defaultBorderWidth));
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setBackground(cColor);
    }
}
