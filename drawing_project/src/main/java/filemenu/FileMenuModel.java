import java.awt.*;

public class FileMenuModel {
    private DrawController controller;

    private int height;
    private int width;
    private int itemcount;
    private String[] itemoption;

    private Color background;


    FileMenuModel()
    {
        this.height = 30;
        this.width = 800;
        this.itemcount = 4;
        this.itemoption = new String[]{"new doodle", "open doodle", "save doodle", "exit"};
        this.background = Color.lightGray;
    }

    public void initialize(DrawController controller)
    {
        this.controller = controller;
    }

    public int getWidth() { return width; }

    public int getHeight() {return height; }

    public String getOptionByIndex(int index){ return itemoption[index]; }

    public int getItemCount() { return itemcount; }

    public Color getBackground(){ return this.background; }
}
