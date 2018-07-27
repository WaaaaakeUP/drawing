/*
*  Shape: See ShapeDemo for an example how to use this class.
*
*/

import javax.vecmath.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

// simple shape model class
class Shape {

    // shape points
    ArrayList<Point2d> points;

    private int drawMode = EventDef.allDraw;
    private int drawPart = 100;
    private Color colour = Color.BLACK;
    private float strokeThickness = 2.0f;
    private float scale;

    public void setData(DataPack data)
    {
        setColour(data.color);
        setStrokeThickness(data.thickness);
        clearPoints();
        int size = data.xList.size();
        for (int i = 0; i < size; i++)
        {
            addPoint(data.xList.get(i), data.yList.get(i));
        }
    }

    public void clearPoints() {
        points = new ArrayList<Point2d>();
        pointsChanged = true;
    }

    public int getLength(){
        if (null == points)
        {
            return 0;
        }
        return points.size();
    }

    public void update()
    {
        int totalSize = points.size();
        int ptrSize = points.size();
        if (EventDef.partDraw == drawMode)
        {
            ptrSize = ptrSize*drawPart/100;
        }

        for (int i = ptrSize+1; i < totalSize; i++)
        {
            points.remove(ptrSize+1);
        }
        drawMode = EventDef.allDraw;
    }

    public void setMode(int mode, int proportion)
    {
        drawMode = mode;
        drawPart = proportion;
    }

    // add a point to end of shape
    public void addPoint(Point2d p) {
        if (points == null) clearPoints();
        points.add(p);
        pointsChanged = true;
    }    

    // add a point to end of shape
    public void addPoint(double x, double y) {
        if (points == null) clearPoints();
        addPoint(new Point2d(x, y));
    }

    public DataPack packData()
    {
        DataPack data = new DataPack();
        data.setThickness(this.strokeThickness);
        data.setColor(this.colour);
        for (Point2d ptr: points)
        {
            data.addX(ptr.x);
            data.addY(ptr.y);
        }
        return data;
    }

    // shape is polyline or polygon
    Boolean isClosed = false; 

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }    

    // if polygon is filled or not
    Boolean isFilled = false;

    public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}


	public void setStrokeThickness(float strokeThickness) {
		this.strokeThickness = strokeThickness;
	}

    // shape's transform

    public void setScale(float scale){
        this.scale = scale;
    }

    // some optimization to cache points for drawing
    Boolean pointsChanged = false; // dirty bit
    int[] xpoints, ypoints;
    int npoints = 0;

    void cachePointsArray() {
        xpoints = new int[points.size()];
        ypoints = new int[points.size()];
        for (int i=0; i < points.size(); i++) {
            xpoints[i] = (int)points.get(i).x;
            ypoints[i] = (int)points.get(i).y;
        }
        npoints = points.size();
        pointsChanged = false;
    }

    // let the shape draw itself
    // (note this isn't good separation of shape View from shape Model)
    public void draw(Graphics2D g2) {

        // don't draw if points are empty (not shape)
        if (points == null) return;

        // see if we need to update the cache
        if (pointsChanged) cachePointsArray();

        // save the current g2 transform matrix 
        AffineTransform M = g2.getTransform();

        // call drawing functions
        int drawpoints = npoints;

        if (drawMode == EventDef.partDraw)
        {
            drawpoints = npoints*drawPart /100;
        }
        g2.setColor(colour);            
        if (isFilled) {
            g2.fillPolygon(xpoints, ypoints, drawpoints);
        } else {
            // can adjust stroke size using scale
        	g2.setStroke(new BasicStroke(strokeThickness * scale));
        	if (isClosed)
                g2.drawPolygon(xpoints, ypoints, drawpoints);
            else
                g2.drawPolyline(xpoints, ypoints, drawpoints);
        }
    }

}
