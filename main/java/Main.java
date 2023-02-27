import javax.swing.*;
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
        customRenderer.start();

        // Initialise application window
        AppWindow mainWindow = new AppWindow();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setMinimumSize(new Dimension(720, 360));

        // Initialise the main container
        AppContainer mainContainer = new AppContainer();

        // Add the main container to the application window
        mainWindow.add(mainContainer);

        // Initialise the menu frame
        // This will contain all the elements needed for the menu
        AppFrame menuFrame = new AppFrame();
        menuFrame.setLayout(null);

        // Create button holder
        AppFrame buttonHolder = new AppFrame();
        buttonHolder.setBackground(AppTheme.getCustomColor("uniqueSpecial"));
        buttonHolder.transform.setSize(1, 100, .5, 0);
        buttonHolder.transform.setPosition(0, 0, .5, 0);
        buttonHolder.enableAbsoluteBounds(true);

        // Add the button holder to the menu frame via the custom renderer
        WaveGraphics.addChild(menuFrame, buttonHolder);
        //menuFrame.add(buttonHolder);

        /*
        // Initialise buttons to add
        AppButton button1 = new AppButton("Button 1");

        // We will call an API method called nextCard, passing it an AppContainer directly
        button1.addActionListener(e -> WaveAPI.debugButton());

        // Add the button to the first frame
        menuFrame.add(button1);
         */

        // Add the frames to the main AppContainer
        mainContainer.add(menuFrame);
    }
}