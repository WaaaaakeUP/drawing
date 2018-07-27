import java.awt.*;
import java.awt.event.*;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlBar{
    private DrawController controller = null;
    public ControlBarView view;
    public ControlBarModel model;

    private JSlider slider;
    private JButton backwards;
    private JButton undo;
    private JButton redo;
    private JButton forewards;
    private JButton[] buttongroup;

    public ControlBar()
    {
        this.model = new ControlBarModel();
        this.view = new ControlBarView(model);

        this.backwards = new JButton();
        this.undo = new JButton();
        this.redo = new JButton();
        this.forewards = new JButton();
        this.slider = new JSlider(0,100, 100);

        buttongroup = new JButton[]{this.backwards, this.undo, this.redo, this.forewards};

        // add widgets to the view
        if (null != this.slider)
        {
            this.view.add(slider);
        }

        for (JButton btn: this.buttongroup)
        {
            if (null != btn)
            {
                this.view.add(btn);
            }
        }
        slider.setMajorTickSpacing(100);
    }

    public void initialize(DrawController controller){
        this.controller = controller;
        this.model.initialize(controller);
        this.view.initialize(controller);

        this.slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider)e.getSource();
                if (controller.getSliderMax() == s.getValue())
                {
                    redo.setEnabled(false);
                    undo.setEnabled(true);
                }
                else if (0 == s.getValue())
                {
                    redo.setEnabled(true);
                    undo.setEnabled(false);
                }
                else
                {
                    redo.setEnabled(true);
                    undo.setEnabled(true);
                }
                controller.updateSliderValue(s.getValue());
            }
        });

        this.forewards.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.forePlay();
            }
        });

        this.backwards.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.backPlay();
            }
        });

        this.undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.undo();
            }
        });

        this.redo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.redo();
            }
        });

        this.redo.setIcon(new ImageIcon(this.view.redoimage));
        this.undo.setIcon(new ImageIcon(this.view.undoimage));
        this.forewards.setIcon(new ImageIcon(this.view.foreimage));
        this.backwards.setIcon(new ImageIcon(this.view.backimage));
        this.redo.setEnabled(false);
    }

    public String convertIndexToTime(int index)
    {
        int minutes = index/60;
        int second = index%60;
        String time = "";
        if (second < 10)
        {
            time = Integer.toString(minutes)+":0"+Integer.toString(second);
        }
        else
        {
            time = Integer.toString(minutes)+":"+Integer.toString(second);
        }
        return time;
    }

    public void updateCount()
    {
        if (controller.getCount() != 0){
            slider.setPaintTicks(true);
            int count = controller.getCount();
            int timeInterval = count/10+1;
            Dictionary dic = new Hashtable();
            for (int i = 0; i <= count; i++)
            {
                if (0 == i%timeInterval || i == count)
                {
                    dic.put(i*controller.getSliderSpace(),new JLabel(convertIndexToTime(i)));
                }
                else
                {
                    dic.put(i*controller.getSliderSpace(),new JLabel(""));
                }
            }
            slider.setLabelTable(dic);
            slider.setPaintLabels(true);
        }
        else
        {
            slider.setPaintTicks(false);
        }
        slider.setMaximum(controller.getSliderMax());
        slider.setValue(controller.getSliderMax());
    }

    public void resetBtnIcon()
    {
        this.redo.setIcon(new ImageIcon(this.view.redoimage));
        this.undo.setIcon(new ImageIcon(this.view.undoimage));
        this.forewards.setIcon(new ImageIcon(this.view.foreimage));
        this.backwards.setIcon(new ImageIcon(this.view.backimage));
    }

    public void updateBtn(int event)
    {
        switch (event)
        {
            case 0:
                forewards.setIcon(new ImageIcon(this.view.foreimage));
                backwards.setIcon(new ImageIcon(this.view.backimage));
                redo.setEnabled(true);
                undo.setEnabled(true);
                forewards.setEnabled(true);
                backwards.setEnabled(true);
                break;
            case 1:
                backwards.setIcon(new ImageIcon(this.view.pauseimage));
                forewards.setIcon(new ImageIcon(this.view.foreimage));
                this.backwards.setEnabled(true);
                this.forewards.setEnabled(true);
                this.redo.setEnabled(false);
                this.undo.setEnabled(false);
                break;
            case 2:
                backwards.setIcon(new ImageIcon(this.view.backimage));
                this.backwards.setEnabled(true);
                this.forewards.setEnabled(true);
                this.redo.setEnabled(false);
                this.undo.setEnabled(false);
                break;
            case 3:
                forewards.setIcon(new ImageIcon(this.view.pauseimage));
                backwards.setIcon(new ImageIcon(this.view.backimage));
                this.backwards.setEnabled(true);
                this.forewards.setEnabled(true);
                this.redo.setEnabled(false);
                this.undo.setEnabled(false);
                break;
            case 4:
                forewards.setIcon(new ImageIcon(this.view.foreimage));
                this.backwards.setEnabled(true);
                this.forewards.setEnabled(true);
                this.redo.setEnabled(false);
                this.undo.setEnabled(false);
                break;
        }

    }
    public void changeSliderValue(int value)
    {
        this.slider.setValue(value);
    }

}
