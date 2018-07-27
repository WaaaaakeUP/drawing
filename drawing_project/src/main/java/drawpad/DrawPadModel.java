import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

public class DrawPadModel {
    private DrawController controller;
    public Vector<Shape> shapegroup;
    public Shape curShape = null;
    public int curDrawIndex = -1;
    private DecimalFormat ptrFormat = new DecimalFormat("0");
    private float scalex;
    private float scaley;

    DrawPadModel()
    {
        this.shapegroup = new Vector();
    }

    public void initialize(DrawController controller)
    {

        this.controller = controller;
        createNewShape();
        this.curShape.setColour(controller.getForeColor());
    }

    public void createNewShape()
    {
        if (null == curShape)
        {
            curShape = new Shape();
            curShape.setColour(controller.getForeColor());
            curShape.setStrokeThickness(controller.getThickness());
            shapegroup.add(curShape);
            curDrawIndex = shapegroup.size() - 1;
            curShape.setScale(scalex);
        }
        else
        {
            if (0 != curShape.getLength())
            {
                curShape = new Shape();
                curShape.setColour(controller.getForeColor());
                curShape.setStrokeThickness(controller.getThickness());
                shapegroup.add(curShape);
                curDrawIndex = shapegroup.size() - 1;
                curShape.setScale(scalex);
            }
        }
    }

    public void updateDrawing()
    {
        this.curDrawIndex = controller.getCount()-1;
        int size = shapegroup.size();
        for(int i = curDrawIndex+1; i < size-1; i++)
        {
            shapegroup.removeElementAt(curDrawIndex+1);
        }
        if (this.curDrawIndex >= 0)
        {
            shapegroup.elementAt(this.curDrawIndex).update();
        }
        this.curDrawIndex++;
        curShape = shapegroup.elementAt(this.curDrawIndex);
    }

    public void changeDraw(int sliderValue)
    {
        if (0 == controller.getCount())
        {
            return;
        }
        int space = controller.getSliderSpace();
        this.curDrawIndex =sliderValue/space;
        int proportion = (sliderValue - curDrawIndex*space)*100/space;

        if (0 == proportion && this.curDrawIndex != 0)
        {
            this.curDrawIndex--;
            proportion = 100;
        }

        for (int i = 0; i < curDrawIndex; i++)
        {
            Shape shape = shapegroup.elementAt(i);
            shape.setMode(EventDef.allDraw, 100);
        }
        shapegroup.elementAt(curDrawIndex).setMode(EventDef.partDraw, proportion);
    }

    public float getScaleX()
    {
        return this.scalex;
    }

    public float getScaleY()
    {
        return this.scaley;
    }

    public void addPoint(int x, int y)
    {
        if (0 == curShape.getLength())
        {
            if (controller.getSliderValue() != controller.getSliderMax())
            {
                controller.updateDrawing();
            }
            controller.increaseCount();
        }
        int actualx = (int)((float) x / scalex);
        int actualy = (int)((float) y / scaley);
        curShape.addPoint(actualx, actualy);
    }

    public void updateForeColor()
    {
        createNewShape();
        curShape.setColour(controller.getForeColor());
    }

    public void updateThickness()
    {
        createNewShape();
        curShape.setStrokeThickness(controller.getThickness());
    }

    public ArrayList<DataPack> packData()
    {
        int size = controller.getCount();
        ArrayList<DataPack> doodleData = new ArrayList<DataPack>(size);

        for (int i = 0; i < size; i++)
        {
            DataPack data = shapegroup.elementAt(i).packData();
            doodleData.add(i, data);
        }
        return doodleData;
    }

    public void setScale(float scalex, float scaley)
    {
        this.scalex = scalex;
        this.scaley = scaley;

        for (Shape shape: shapegroup)
        {
            shape.setScale(this.scalex);
        }
    }

    public void clearData()
    {
        curDrawIndex = -1;
        curShape = null;
        shapegroup.clear();
    }

    public void setData(ArrayList<DataPack> newdata)
    {
        clearData();
        shapegroup = new Vector();
        for (DataPack data: newdata)
        {
            Shape shape = new Shape();
            shape.setData(data);
            shapegroup.add(shape);
        }
        if (0 == shapegroup.size())
        {
            curShape = null;
            curDrawIndex = -1;
        }
        else
        {
            curDrawIndex = shapegroup.size()-1;
            curShape = shapegroup.elementAt(curDrawIndex);
        }
        setScale(getScaleX(), getScaleY());
    }
}
