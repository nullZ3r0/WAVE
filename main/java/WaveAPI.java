import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.regex.Pattern;

public class WaveAPI
{
    private static boolean listening = false;
    private static String lastAction = "";
    public static void fireAction(String actionName)
    {
        lastAction = actionName;
        switch (actionName)
        {
            case "togglePlay":
                Wave.togglePlayMidiSequence();
                break;
            case "play":
                Wave.resumeMidiSequence();
                break;
            case "pause":
                Wave.pauseMidiSequence();
                break;
            case "restart":
                Wave.restartMidiSequence();
                break;
            case "restartPlay":
                Wave.restartMidiSequence();
                Wave.resumeMidiSequence();
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
            case "signalGestureReady":
                System.out.println("[i] pyhelper_gestures has loaded");
                break;
            case "signalVoiceReady":
                System.out.println("[i] pyhelper_voice has loaded");
                break;
        }
    }

    public static boolean containsFlag(String string, String flag)
    {
        return string.matches(".*\\b" + Pattern.quote(flag) + "\\b.*");
    }
    public static void stringToAction(String rawString)
    {
        String string = rawString.toLowerCase();
        if (containsFlag(string, "wave") || containsFlag(string, "waves") || listening)
        {
            System.out.println("[i] Wave heard a potential command");
            if (containsFlag(string, "play") || containsFlag(string, "resume") || containsFlag(string, "start") || containsFlag(string, "continue"))
            {
                fireAction("play");
            }
            else if(containsFlag(string, "stop") || containsFlag(string, "pause") || containsFlag(string, "break"))
            {
                fireAction("pause");
            }
            else if(containsFlag(string, "from the top") || containsFlag(string, "restart"))
            {
                fireAction("restartPlay");
            }
            else if(containsFlag(string, "even faster") || containsFlag(string, "speed up more") || containsFlag(string, "more fast"))
            {
                fireAction("increaseSpeed");
                fireAction("increaseSpeed");
            }
            else if(containsFlag(string, "faster") || containsFlag(string, "speed up") || containsFlag(string, "fast"))
            {
                fireAction("increaseSpeed");
            }
            else if(containsFlag(string, "even slower") || containsFlag(string, "slow down more") || containsFlag(string, "more slow"))
            {
                fireAction("decreaseSpeed");
                fireAction("decreaseSpeed");
            }
            else if(containsFlag(string, "slower") || containsFlag(string, "slow down") || containsFlag(string, "slow"))
            {
                fireAction("decreaseSpeed");
            }
            else if(containsFlag(string, "listen") || containsFlag(string, "start listening"))
            {
                listening = true;
            }
            else if(containsFlag(string, "ignore") || containsFlag(string, "stop listening") || containsFlag(string, "don't listen"))
            {
                listening = false;
            }
            else if (containsFlag(string, "again") || containsFlag(string, "once more") || containsFlag(string, "one more time"))
            {
                fireAction(lastAction);
            }
            else if (containsFlag(string, "even more"))
            {
                switch (lastAction)
                {
                    case "decreaseSpeed":
                    case "increaseSpeed":
                        fireAction(lastAction);
                        fireAction(lastAction);
                }
            }
            else if (containsFlag(string, "more"))
            {
                switch (lastAction)
                {
                    case "decreaseSpeed":
                    case "increaseSpeed":
                        fireAction(lastAction);
                }
            }
        }
        else if (containsFlag(string, "jarvis"))
        {
            if (containsFlag(string, "hello") || containsFlag(string, "hi") || containsFlag(string, "hey"))
            {
                System.out.println("[Jarvis] Hello Mr. Stark");
            }
        }
    }

    public static File chooseMidiFile()
    {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("main/resources/midi-library"));
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
