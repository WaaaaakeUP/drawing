import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class DrawController {
    private Model model;
    private View view;
    private FileMenu filemenu;
    private DrawPad drawpad ;
    private ColorMenu colormenu;
    private ControlBar controlbar;
    private Timer foreplay;
    private Timer backplay;
    private JFileChooser filechooser;
    private JFileChooser filesaver;
    private FileNameExtensionFilter filter;
    private int curevent;

    DrawController()
    {
        model = new Model();
        view = new View(model);
        filemenu = new FileMenu();
        drawpad = new DrawPad();
        colormenu = new ColorMenu();
        controlbar = new ControlBar();
        if (filemenu != null)
        {
            this.view.addComponent(filemenu.view);
        }
        if (colormenu != null)
        {
            this.view.addComponent(colormenu.view);
        }
        if (drawpad != null){
            this.view.addComponent(drawpad.view);
        }
        if (controlbar != null)
        {
            this.view.addComponent(controlbar.view);
        }
    }

    public void initialize(){
        this.curevent = EventDef.Drawing;
        this.model.initialize(this);
        this.filemenu.initialize(this);
        this.drawpad.initialize(this);
        this.colormenu.initialize(this);
        this.controlbar.initialize(this);
        this.view.initialize(this);
        this.filter = new FileNameExtensionFilter("DoodleFile","doodle");
        this.filechooser = new JFileChooser();
        this.filechooser.setDialogTitle("OpenFile");
        this.filechooser.setFileFilter(filter);
        this.filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.filesaver = new JFileChooser();
        this.filesaver.setDialogTitle("SaveFile");
        this.filesaver.setFileFilter(filter);
        this.filesaver.setDialogType(JFileChooser.SAVE_DIALOG);
        this.filesaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        this.foreplay = new Timer(model.getDelay(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getSliderMax() == getSliderValue())
                {
                    curevent = EventDef.Drawing;
                    foreplay.stop();
                    if (null != controlbar)
                    {
                        controlbar.updateBtn(curevent);
                    }
                }
                changeSliderValue(getSliderValue()+1);
            }
        });

        this.backplay = new Timer(model.getDelay(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (0 == getSliderValue())
                {
                    curevent = EventDef.Drawing;
                    backplay.stop();
                    if (null != controlbar)
                    {
                        controlbar.updateBtn(curevent);
                    }
                }
                changeSliderValue(model.getSliderValue()-1);
            }
        });

    }

    public void forePlay()
    {
        if (null == this.controlbar)
        {
            return;
        }
        this.backplay.stop();
        if (curevent == EventDef.ForewardPause)
        {
            curevent = EventDef.ForewardPlay;
            this.foreplay.start();
        }
        else if (curevent == EventDef.ForewardPlay)
        {
            curevent = EventDef.ForewardPause;
            this.foreplay.stop();
        }
        else {
            this.controlbar.changeSliderValue(0);
            curevent = EventDef.ForewardPlay;
            this.foreplay.restart();
        }
        controlbar.updateBtn(curevent);
    }

    public void backPlay()
    {
        if (null == this.controlbar)
        {
            return;
        }
        this.foreplay.stop();
        if (curevent == EventDef.BackwardPause)
        {
            curevent = EventDef.BackwardPlay;
            this.backplay.start();
        }
        else if (curevent == EventDef.BackwardPlay)
        {
            curevent = EventDef.BackwardPause;
            this.backplay.stop();
        }
        else {
            this.controlbar.changeSliderValue(getSliderMax());
            curevent = EventDef.BackwardPlay;
            this.backplay.restart();
        }
        controlbar.updateBtn(curevent);
    }

    public void changeSliderValue(int value)
    {
        if (null == this.controlbar)
        {
            return;
        }
        controlbar.changeSliderValue(value);
    }

    public void updateSliderValue(int value)
    {
        if (null == this.model)
        {
            return;
        }

        if (null == this.drawpad)
        {
            return;
        }
        model.updateSliderValue(value);
        drawpad.changeDraw(value);
    }

    public void updateDrawing()
    {
        if (null == this.model)
        {
            return;
        }
        if (null == this.drawpad)
        {
            return;
        }
        model.updateCount();
        drawpad.updateDrawing();
    }

    public void updateThickness(int value)
    {
        if (null == this.model)
        {
            return;
        }

        if (null == this.drawpad)
        {
            return;
        }
        if (null == this.colormenu)
        {
            return;
        }

        float newThickness = 0.0f + 8.0f *value/10;

        model.updateThickness(newThickness);
        drawpad.updateThickness();
        colormenu.updateThickness(value);
    }

    public void undo()
    {
        if (null == this.drawpad)
        {
            return;
        }
        drawpad.undo();
    }

    public void redo()
    {
        if (null == this.drawpad)
        {
            return;
        }
        drawpad.redo();
    }

    public void changeForeColor(Color color){
        if (null == model ){
            return;
        }
        if (null == drawpad)
        {
            return;
        }
        if (null == colormenu)
        {
            return;
        }
        if (null == color)
        {
            return;
        }
        model.updateForeColor(color);
        drawpad.updateForeColor();
        colormenu.updateForeColor();
    }

    public void increaseCount()
    {
        if (null == this.model)
        {
            return;
        }
        model.increaseCount();
        if (null == controlbar)
        {
            return;
        }
        controlbar.updateCount();
    }

    public void alert (int info)
    {
        String title = "Alert";
        String message;
        if (info == EventDef.NotValidDoodleFile)
        {
            message = "You are opening an invalid doodle file.";
        }
        else if (info == EventDef.FileNotExit)
        {
            message = "The file doesn't exist.";
        }
        else
        {
            return;
        }
        JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean checkFormat(File doodle)
    {
        boolean valid = true;
        int totalStroke = -1;
        Scanner content;
        try {
            content = new Scanner(doodle);
            if (content.hasNext())
            {
                String sTotalCount = content.nextLine();
                try{
                    totalStroke = Integer.parseInt(sTotalCount);
                }catch (NumberFormatException e)
                {
                    valid = false;
                }
            }
            else
            {
                valid = false;
            }
            if (valid && totalStroke < 0)
            {
                valid = false;
            }
            while (valid && (totalStroke > 0))
            {
                String sThickness;
                String sColor;
                String sPointsX;
                String sPointsY;
                int xLength = -1;
                int yLength = -1;
                try{
                    if (content.hasNext())
                    {
                        sThickness = content.nextLine();
                        float thickness = Float.parseFloat(sThickness);
                    }
                    else
                    {
                        valid = false;
                        break;
                    }
                    if (content.hasNext())
                    {
                        sColor = content.nextLine();
                        Color color = Color.decode(sColor);
                    }
                    else
                    {
                        valid = false;
                        break;
                    }
                    if (content.hasNext())
                    {
                        sPointsX = content.nextLine();
                        ArrayList<String> ptrList = new ArrayList<String>(Arrays.asList(sPointsX.split(";")));
                        for(String x: ptrList)
                        {
                            double xvalue = Double.parseDouble(x);
                        }
                        xLength = ptrList.size();
                    }
                    else
                    {
                        valid = false;
                        break;
                    }
                    if (content.hasNext())
                    {
                        sPointsY = content.nextLine();
                        ArrayList<String> ptrList = new ArrayList<String>(Arrays.asList(sPointsY.split(";")));
                        for(String y: ptrList)
                        {
                            double yvalue = Double.parseDouble(y);
                        }
                        yLength = ptrList.size();
                    }
                    else
                    {
                        valid = false;
                        break;
                    }
                    if (xLength != yLength)
                    {
                        valid = false;
                        break;
                    }
                }catch (NumberFormatException e)
                {
                    valid = false;
                    break;
                }

                totalStroke--;
            }

        }catch (FileNotFoundException e)
        {
            valid = false;
            alert(EventDef.FileNotExit);
        }
        return valid;
    }

    public void clearData()
    {
        if (null == this.model)
        {
            return;
        }
        if (null == this.drawpad)
        {
            return;
        }
        model.clearData();
        drawpad.clearData();
    }

    public void readInFile(File doodle)
    {
        if (!doodle.canRead() || !checkFormat(doodle))
        {
            this.alert(EventDef.NotValidDoodleFile);
            return;
        }
        int totalStroke = -1;
        Scanner content;
        ArrayList <DataPack> newdata;
        try {
            content = new Scanner(doodle);
            totalStroke = Integer.parseInt(content.nextLine());
            newdata = new ArrayList<DataPack>(totalStroke);
            for (int i = 0; i < totalStroke; i++)
            {
                DataPack data = new DataPack();
                data.setThickness(Float.parseFloat(content.nextLine()));
                data.setColor(Color.decode(content.nextLine()));
                ArrayList<String> xList = new ArrayList<String>(Arrays.asList(content.nextLine().split(";")));
                ArrayList<String> yList = new ArrayList<String>(Arrays.asList(content.nextLine().split(";")));
                for (String x: xList)
                {
                    data.addX(Double.parseDouble(x));
                }
                for (String y: yList)
                {
                    data.addY(Double.parseDouble(y));
                }
                newdata.add(data);
            }

        }catch (FileNotFoundException e)
        {
            alert(EventDef.FileNotExit);
            return;
        }
        clearData();
        model.setCount(totalStroke);
        drawpad.setData(newdata);
        controlbar.updateCount();
        drawpad.view.repaint();
    }

    public void openFile()
    {
        int chooserreturn = filechooser.showDialog(null, "Open");
        if (chooserreturn == JFileChooser.APPROVE_OPTION)
        {

            File newdoodle = filechooser.getSelectedFile();
            this.readInFile(newdoodle);
        }
    }

    public void openFileAlert()
    {
        String title = "Alert";
        String info = "Do you want to save the previous work before opening a existed doodle";
        int result = JOptionPane.showInternalConfirmDialog(
                null,info,title, JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION)
        {
            saveFile();
            openFile();
        }
        else if (result == JOptionPane.NO_OPTION)
        {
            openFile();
        }
    }

    public boolean overWriteAlert()
    {
        String title = "Alert";
        String info = "The file already exit, do you still want to save it";
        int result = JOptionPane.showInternalConfirmDialog(
                null,info,title, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
        {
            return true;
        }
        return false;
    }

    public void newFile()
    {
        String title = "Alert";
        String info = "Do you want to save the previous work before creating a new doodle";
        int result = JOptionPane.showInternalConfirmDialog(
                null,info,title, JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION)
        {
            saveFile();
            clearData();
            drawpad.view.repaint();
        }
        else if (result == JOptionPane.NO_OPTION)
        {
            clearData();
            drawpad.view.repaint();
        }
    }

    public ArrayList <DataPack> packData()
    {
        return drawpad.packData();
    }

    public void saveFile()
    {
        ArrayList <DataPack> newdata = packData();
        int choosereturn = filesaver.showSaveDialog(this.view);
        if (choosereturn == JFileChooser.APPROVE_OPTION)
        {
            if (filesaver.getSelectedFile().exists())
            {
                if (!overWriteAlert())
                {
                    saveFile();
                    return;
                }
            }
            File directory = new File(filesaver.getSelectedFile().toString()+".doodle");
            try {
                BufferedWriter datawrite =  new BufferedWriter(new FileWriter(directory));
                datawrite.write(Integer.toString(getCount()));
                datawrite.newLine();
                for (DataPack data: newdata)
                {
                    datawrite.write(Float.toString(data.thickness));
                    datawrite.newLine();
                    String sValue = Integer.toHexString(data.color.getRGB());
                    datawrite.write("#"+sValue.substring(2).toUpperCase());
                    datawrite.newLine();
                    String xData = "";
                    String yData = "";
                    for (Double x:data.xList)
                    {
                        xData += Double.toString(x);
                        xData += ";";
                    }
                    for (Double y:data.yList)
                    {
                        yData += Double.toString(y);
                        yData += ";";
                    }
                    datawrite.write(xData);
                    datawrite.newLine();
                    datawrite.write(yData);
                    datawrite.newLine();
                }
                datawrite.close();
            }catch (IOException e)
            {
                alert(EventDef.FileNotExit);
            }
        }
    }

    public void exit() {
        String title = "Alert";
        String info = "Do you want to save the previous work before exiting";
        int result = JOptionPane.showInternalConfirmDialog(
                null,info,title, JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION)
        {
            saveFile();
            this.view.exit();
        }
        else if (result == JOptionPane.NO_OPTION)
        {
            this.view.exit();
        }
    }

    public void setThicknessScale(float scale) { colormenu.setScale(scale); }

    public void resetBtnIcon() { controlbar.resetBtnIcon(); }

    public int getSliderMax(){ return model.getSliderMax(); }

    public int getSliderSpace(){ return model.getSliderSpace(); }

    public int getCount() { return model.getCount(); }

    public Color getColorByIndex(int i) { return model.getColorByIndex(i); }

    public int getColorSize() { return model.getColorSize(); }

    public Color getForeColor() { return this.model.getForeColor(); }

    public int getSliderValue() { return  this.model.getSliderValue(); }

    public float getThickness() { return this.model.getThickness(); }

}
