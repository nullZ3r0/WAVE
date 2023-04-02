import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class WaveAPI
{
    public static void fireAction(String actionName)
    {
        switch (actionName)
        {
            case "togglePlay":
                Wave.togglePlayMidiSequence();
                break;
            case "restart":
                Wave.restartMidiSequence();
                break;
            case "increaseSpeed":
                Wave.increaseMidiSequenceSpeed();
                break;
            case "decreaseSpeed":
                Wave.decreaseMidiSequenceSpeed();
                break;
            case "zoomIn":
                Wave.zoomInVisualiser();
                break;
            case "zoomOut":
                Wave.zoomOutVisualiser();
                break;
            case "toggleMenu":
                if (Main.mainPanel.self.isVisible())
                {
                    WaveAPI.showPanel(Main.menuPanel.self);
                }
                else
                {
                    WaveAPI.showPanel(Main.mainPanel.self);
                }
                Wave.pauseMidiSequence();
                break;
            case "openFileChooser":
                File midiFile = WaveAPI.chooseMidiFile();
                if (midiFile != null)
                {
                    Wave.setMidiSequence(midiFile);
                    WaveAPI.showPanel(Main.mainPanel.self);
                }
                break;
        }
    }

    public static File chooseMidiFile()
    {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("main/assets/midi-library"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.mid", "mid"));

        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println(selectedFile);
            return selectedFile;
        }

        return null;
    }

    public static void showCard(Container container, String name)
    {
        System.out.println("Test API: showCard");

        LayoutManager currentLayoutManager = container.getLayout();
        if (currentLayoutManager.getClass() == CardLayout.class)
        {
            CardLayout manager = (CardLayout) currentLayoutManager;
            manager.show(container, name);
            container.revalidate();
            container.repaint();
        }
    }

    public static void showPanel(AppFrame appFrame)
    {
        Container parent = appFrame.getParent();
        if (parent != null)
        {
            Component[] children = parent.getComponents();
            for (Component child : children)
            {
                child.setVisible(false);
            }
            appFrame.setVisible(true);
        }
    }

    public static void switchToCredits()
    {
        System.out.println("Test API: This show the creditsContainer on the menuPanel -> rightContainer");
    }

    public static void debugButton()
    {
        System.out.println("This button has been pressed");
    }

    public static void nextCard(Container container)
    {
        System.out.println("Test API: nextCard");

        LayoutManager currentLayoutManager = container.getLayout();
        if (currentLayoutManager.getClass() == CardLayout.class)
        {
            CardLayout manager = (CardLayout) currentLayoutManager;
            manager.next(container);
            container.revalidate();
            container.repaint();
        }
    }

    public static void exit()
    {
        System.exit(0);
    }
}
