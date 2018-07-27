import javax.swing.*;
import java.awt.*;

public class FileMenuView extends JMenuBar {
    private DrawController controller;
    private FileMenuModel model;

    public void initialize(DrawController controller, FileMenuModel model)
    {
        this.controller = controller;
        this.model = model;
        this.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
        this.setOpaque(true);
        this.setBackground(model.getBackground());
    }
}
