import java.awt.*;
import java.util.*;
import java.util.List;

public class Model {
    private DrawController controller;
    private Color[] colorgroup;
    private Color foreColor;
    private int delay;
    private int sliderMax;
    private int sliderValue;
    private int sliderSpace;
    private float thickness;
    private int shapecount;

    /**
     * Create a new model.
     */
    public Model() {
        //this.observers = new ArrayList();
        this.controller = controller;
        this.colorgroup = new Color[]{new Color(254, 146, 19), new Color(254, 240, 19),
                new Color(15, 244, 80), new Color(0, 174, 231), new Color(16, 39, 255),
                new Color(145, 40, 253), new Color(0, 0, 0), new Color(255, 255, 255),};
        this.foreColor = colorgroup[0];
        this.delay = 10;
        this.sliderMax = 100;
        this.sliderValue = 100;
        this.sliderSpace = 100;
        this.thickness = 3.0f;
        this.shapecount = 0;
    }

    public void initialize(DrawController controller)
    {
        this.controller = controller;
    }

    public void increaseCount() {
        this.shapecount++;
        sliderMax = shapecount*sliderSpace;
        sliderValue = sliderMax;
    }

    public void updateCount() {
        this.shapecount = sliderValue/sliderSpace;
        if (0 != sliderValue%sliderSpace)
        {
            this.shapecount++;
        }
    }

    public void clearData()
    {
        shapecount = 0;
        sliderMax = 100;
        sliderValue = 100;
    }

    public void setCount(int count) {
        this.shapecount = count;
        sliderMax = shapecount*sliderSpace;
        sliderValue = Math.min(sliderMax, sliderValue);
    }

    public int getDelay() { return delay; }

    public int getSliderValue() { return sliderValue; }

    public void updateSliderValue(int value) { sliderValue = value; }

    public int getSliderSpace(){ return sliderSpace; }

    public int getSliderMax(){ return sliderMax; }

    public Color getForeColor() { return foreColor; }

    public float getThickness() { return thickness; }

    public void updateThickness(float value) { this.thickness = value; }

    public void updateForeColor(Color color) {
        foreColor = color;
    }

    public int getColorSize(){ return colorgroup.length; }

    public Color getColorByIndex(int i){ return colorgroup[i]; }

    public int getCount() { return shapecount; }
}
