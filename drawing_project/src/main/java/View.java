
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class View extends JFrame {
    private DrawController controller = null;
    private Model model;
    private JPanel screen;
    /**
     * Create a new View.
     * Use controller to handle model change and notify view
     */
    public View(Model model) {
        // Set up the window.
        this.setTitle("Drawing");
        this.setMinimumSize(new Dimension(520, 390));
        this.setSize(800, 600);
        this.setPreferredSize(new Dimension(800, 600));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                setSize(width, width*3/4);
                screen.setLayout(new screenLayout());
            }
        });
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;

        this.screen = new JPanel();
    }

    public void initialize(DrawController controller) {
        this.controller = controller;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.exit();
            }
        });
        this.screen.setLayout(new screenLayout());
        this.add(this.screen);
        setVisible(true);
    }

    void addComponent(Component component) {
        this.screen.add(component);
    }

    /**
     * Update with data from the model.
     */

    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        System.out.println("Model changed!");
    }

    public void exit(){ System.exit(0); }
}

class screenLayout implements LayoutManager {
    public void addLayoutComponent(String name, Component comp) {
        // no-op
    }

    public void layoutContainer(Container parent) {
        Component [] components = parent.getComponents();
        Dimension parentSize = parent.getSize();
        Point pos = parent.getLocation();
        int preferHeight;
        int currentHeight;
        Component fileMenu = components[0];
        Component colorMenu = components[1];
        Component drawPad = components[2];
        Component controlBar = components[3];
        fileMenu.setSize(parent.getWidth(), fileMenu.getPreferredSize().height);
        fileMenu.setLocation(0,0);
        preferHeight = parent.getPreferredSize().height - fileMenu.getHeight();
        currentHeight = parent.getHeight() - fileMenu.getHeight();
        colorMenu.setSize(colorMenu.getPreferredSize().width*currentHeight/preferHeight,
                colorMenu.getPreferredSize().height*currentHeight/preferHeight);
        colorMenu.setLocation(0,fileMenu.getHeight());
        drawPad.setSize(parentSize.width- colorMenu.getWidth(), colorMenu.getHeight());
        drawPad.setLocation(colorMenu.getWidth(),fileMenu.getHeight());
        controlBar.setSize(parentSize.width, parentSize.height-fileMenu.getHeight()-colorMenu.getHeight());
        controlBar.setLocation(0,parentSize.height-controlBar.getHeight());

    }

    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(400, 300);
    }

    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(800, 600);
    }

    public void removeLayoutComponent(Component comp) {
        // no-op
    }
}
