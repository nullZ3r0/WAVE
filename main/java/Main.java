import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main
{
    private static void setupWindowTheme()
    {
        // This is our own Application Theme system
        AppTheme.setup();

        // Modify the current Theme's properties that require java objects
        UIManager.put("TitlePane.font", AppTheme.titleFont.deriveFont(Font.BOLD, 12));
        UIManager.put("TitlePane.buttonSize", new Dimension(32, 24));

        // Setup look and feel to act as base theme for all objects, expect them to be overridden
        // The flatlaf library is needed because it allows us to easily modify things like the title bar size, customise title bar icons, etc
        DefaultTheme.setup();
    }

    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        setupWindowTheme();

        // Initialise custom render thread
        WaveGraphics customRenderer = new WaveGraphics();
        customRenderer.setDaemon(true);

        // Naming hierarchy and Class hierarchy
        // appWindow (AppWindow) -> appCanvas (AppCanvas) -> appPanel (AppFrame) -> appContainers (AppFrame) -> appFrames (AppFrame) / appButtons (AppButton)
        // AppWindow -> AppCanvas -> AppFrame -> AppButton

        // Initialise application window
        AppWindow mainWindow = new AppWindow();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setMinimumSize(new Dimension(1000, 600));

        // Initialise the main canvas
        AppCanvas mainCanvas = new AppCanvas();

        // Add the main canvas to the application window
        mainWindow.add(mainCanvas);

        // Initialise the menuPanel
        menuPanel menuPanel = new menuPanel();

        // Test manipulating exposed elements
        //menuPanel.resumeButton.addActionListener(e -> WaveAPI.hideMenu(menuPanel));
        menuPanel.resumeButton.addActionListener(e -> WaveAPI.nextCard(mainCanvas));

        // Initialise the mainPanel
        mainPanel mainPanel = new mainPanel();

        // Add the frames to the main AppCanvas
        mainCanvas.add(menuPanel.self);
        mainCanvas.add(mainPanel.self);

        // Run WaveGraphics
        customRenderer.start();
    }
}