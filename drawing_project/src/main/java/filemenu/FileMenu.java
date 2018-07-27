import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileMenu{
    private DrawController controller;
    public FileMenuView view;
    private FileMenuModel model;

    private JMenu menu;
    private JMenuItem[] itemgroup;


    FileMenu(){
        this.view = new FileMenuView();
        this.model = new FileMenuModel();
        this.menu = new JMenu("File");
        this.view.add(menu);
    }

    public void initialize(DrawController controller)
    {
        this.controller = controller;
        this.model.initialize(controller);
        this.view.initialize(controller, this.model);
        itemgroup = new JMenuItem[model.getItemCount()];
        for (int i = 0; i < model.getItemCount(); i++)
        {
            this.itemgroup[i] = new JMenuItem(model.getOptionByIndex(i));
            this.menu.add(this.itemgroup[i]);
        }
        this.addListener();
    }

    private void addListener()
    {
        for (int i = 0; i < model.getItemCount(); i++)
        {
            JMenuItem item = itemgroup[i];
            if ("exit" == model.getOptionByIndex(i))
            {
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.exit();
                    }
                });
            }
            else if ("new doodle" == model.getOptionByIndex(i))
            {
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.newFile();
                    }
                });
            }
            else if ( "open doodle" == model.getOptionByIndex(i))
            {
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.openFileAlert();
                    }
                });
            }
            else if ("save doodle" == model.getOptionByIndex(i))
            {
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.saveFile();
                    }
                });
            }
        }
    }
}
