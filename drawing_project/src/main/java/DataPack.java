import java.awt.*;
import java.util.ArrayList;

public class DataPack {
    public float thickness;
    public Color color;
    public ArrayList<Double> xList;
    public ArrayList<Double> yList;

    DataPack()
    {
        xList = new ArrayList<Double>();
        yList = new ArrayList<Double>();
    }

    public void setThickness(float thickness) { this.thickness = thickness; }

    public void setColor(Color color) { this.color = color; }

    public void addX(Double x) { this.xList.add(x); }

    public void addY(Double y) { this.yList.add(y); }
}
