import java.awt.*;

public class ColorMenuModel {
    private int thickness;
    private int slidermax;
    private int thickWidth;
    private int thickHeight;
    private Color background;
    private int width;
    private int height;

    ColorMenuModel()
    {
        width = 120;
        height = 490;
        thickness = 5;
        slidermax = 10;
        thickWidth = 20;
        thickHeight = 20;
        background = new Color(229,229,229);
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int getSliderMax() { return slidermax; }

    public int getThickness() { return thickness; }

    public int getThickWidth() { return thickWidth; }

    public int getThickHeight() { return thickHeight; }

    public Color getBackground() { return background; }
}
