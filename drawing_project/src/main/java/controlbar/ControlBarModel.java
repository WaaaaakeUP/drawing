import java.awt.*;

public class ControlBarModel {
    DrawController controller;

    private int ControlBarWidth;
    private int ControlBarHeight;
    private int buttonSize;
    private int iconSize;
    private int sliderWidth;
    private int sliderHeight;

    private Color background;

    private String redoIconPath = "redo.png";
    private String undoIconPath = "undo.png";
    private String foreIconPath = "fore.gif";
    private String backIconPath = "back.gif";
    private String pauseIconPath = "pause.png";

    ControlBarModel(){
        buttonSize = 50;
        iconSize = 40;
        ControlBarWidth = 800;
        ControlBarHeight = 80;
        sliderHeight = 50;
        sliderWidth = 550;
        background = Color.lightGray;
    }

    void initialize(DrawController controller) { this.controller = controller; }

    public int getWidth() { return ControlBarWidth; }

    public int getHeight() { return ControlBarHeight; }

    public Color getBackground() { return background; }

    public int getBtnSize() { return buttonSize; }

    public int getSliderWidth() { return sliderWidth; }

    public int getSliderHeight() { return sliderHeight; }

    public int getIconSize() {return iconSize; }

    public String getRedoIconPath() { return redoIconPath; }

    public String getUndoIconPath() { return undoIconPath; }

    public String getForeIconPath() {return foreIconPath; }

    public String getBackIconPath() {return backIconPath; }

    public String getPauseIconPath() { return pauseIconPath; }
}
