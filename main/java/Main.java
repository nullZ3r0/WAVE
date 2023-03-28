import com.formdev.flatlaf.FlatLaf;
import javax.swing.*;
import java.awt.*;

public class Main
{
    public static AppCanvas mainCanvas;

    private static void setupWindowTheme()
    {
        // This is our own Application Theme system
        AppTheme.setup();

        // Modify the current Theme's properties that require java objects
        UIManager.put("TitlePane.buttonSize", new Dimension(32, 24));

        // Setup look and feel to act as base theme for all objects, expect them to be overridden
        // The flatlaf library is needed because it allows us to easily modify things like the title bar size, customise title bar icons, etc
        DefaultTheme.setup();
    }

    public static void main(String[] args)
    {
        System.out.println("Starting WAVE!");
        //System.setProperty("sun.java2d.opengl", "True");
        setupWindowTheme();

        // Midi testing
        MidiConnector midiConnector = new MidiConnector();

        // Initialise custom render thread
        WaveGraphics customRenderer = new WaveGraphics();
        customRenderer.enableHighQuality(true);
        customRenderer.setDaemon(true);

        // Naming hierarchy and Class hierarchy
        // appWindow (AppWindow) -> appCanvas (AppCanvas) -> appPanel (AppFrame) -> appContainers (AppFrame) -> appFrames (AppFrame) / appButtons (AppButton)
        // AppWindow -> AppCanvas -> AppFrame -> AppButton

        // Initialise application window
        AppWindow mainWindow = new AppWindow();
        //mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        // DO NOT change this to be LOWER THAN 1000 x 600!
        mainWindow.setMinimumSize(new Dimension(1000, 600));

        // Connect keybind framework to mainPanel
        Keybind keybind = new Keybind();
        mainWindow.add(keybind.swingConnector);

        // Multimedia keybindings
        keybind.setKeybinding("SPACE","togglePlay");
        keybind.setKeybinding("UP","increaseSpeed");
        keybind.setKeybinding("DOWN","decreaseSpeed");
        keybind.setKeybinding("pressed R","restart");

        // Zoom keybindings
        keybind.setKeybinding("ADD","zoomIn");
        keybind.setKeybinding("control EQUALS","zoomIn");
        keybind.setKeybinding("SUBTRACT","zoomOut");
        keybind.setKeybinding("control MINUS","zoomOut");

        // Panel keybindings
        keybind.setKeybinding("ESCAPE","toggleMenu");


        // Initialise the main canvas
        mainCanvas = new AppCanvas();
        mainWindow.add(mainCanvas);

        // Initialise the menuPanel
        menuPanel menuPanel = new menuPanel();

        // Test manipulating exposed elements
        menuPanel.resumeButton.addActionListener(e -> WaveAPI.showCard(mainCanvas,"mainPanel"));

        // creditsPanel
        creditsPanel creditsPanel = new creditsPanel();
        //WaveGraphics.addChild(menuPanel.rightContainer, creditsPanel.self);

        // Initialise the mainPanel
        mainPanel mainPanel = new mainPanel();

        // Add the frames to the main AppCanvas
        mainCanvas.add(menuPanel.self, "menuPanel");
        mainCanvas.add(mainPanel.self, "mainPanel");

        // Run WaveGraphics
        customRenderer.start();

        // Setup some other stuff
        System.setProperty("sun.java2d.opengl", "True");
        MidiPlayer midiPlayer = new MidiPlayer();

        MidiInputReceiver testReceiver = midiConnector.findReceiver("Wave MIDI Experiment");
        if (testReceiver != null)
        {
            testReceiver.startListening();
            //midiConnector.printReceivers();
            Wave.setMidiInputReceiver(testReceiver);
        }
        else
        {
            midiConnector.printReceivers();
        }

        Wave.setVisualiser(mainPanel.visualiser);
        Wave.setMidiPlayer(midiPlayer);
        Wave.setMidiSequence("main/assets/midi-library/pattern 9.mid");
        Wave.loadNoteActions();
        Wave.startMidiSequence();
        Wave.enableFeedback(true);
    }
}