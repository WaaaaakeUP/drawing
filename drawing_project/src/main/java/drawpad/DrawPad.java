/*
* CS 349 Java Code Examples
*
* ShapeDemo    Demo of Shape class: draw shapes using mouse.
*
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

// create the window and run the demo
public class DrawPad{

    private DrawController controller = null;
    public DrawPadView view;
    private DrawPadModel model;

    DrawPad() {
        this.model = new DrawPadModel();
        this.view = new DrawPadView(model);
    }

    public void initialize(DrawController controller)
    {
        this.controller = controller;
        this.model.initialize(controller);
        this.view.initialize(controller);

    }
    public void updateForeColor()
    {
        if (this.model == null)
        {
            return;
        }
        model.updateForeColor();
    }

    public void updateThickness()
    {
        if (this.model == null)
        {
            return;
        }

        model.updateThickness();
    }

    public void changeDraw(int sliderValue){
        if (this.view != null)
        {
            view.changeDraw(sliderValue);
        }
    }

    public void updateDrawing()
    {
        if (this.model == null)
        {
            return;
        }
        this.model.updateDrawing();
    }

    public void undo()
    {
        if (0 == controller.getCount())
        {
            return;
        }
        int prevvalue = controller.getSliderValue();
        int size = prevvalue/controller.getSliderSpace();
        if (0 == prevvalue%controller.getSliderSpace())
        {
            size = Math.max(0, size-1);
        }
        controller.changeSliderValue(size*controller.getSliderSpace());
    }

    public void redo()
    {
        int prevvalue = controller.getSliderValue();
        int size = prevvalue/controller.getSliderSpace()+1;
        size = Math.min(size, controller.getCount());
        controller.changeSliderValue(size*controller.getSliderSpace());
    }

    public ArrayList<DataPack> packData()
    {
        return model.packData();
    }

    public void clearData()
    {
        if (null == this.model)
        {
            return;
        }
        this.model.clearData();
    }

    public void setData(ArrayList<DataPack> newdata)
    {
        if (null == this.model)
        {
            return;
        }
        this.model.setData(newdata);
    }
}

