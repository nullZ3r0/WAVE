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
        buttonHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 24));
        buttonHolder.setBackground(AppTheme.getCustomColor("uniqueSpecial"));
        buttonHolder.transform.setSize(0, 100, 0.30, 1);
        buttonHolder.transform.setPosition(0, 0, 0, 0);
        buttonHolder.transform.addConstraint(new UISizeConstraint(250,400,0,9999));

        // Add the button holder to the menu frame via the custom renderer
        WaveGraphics.addChild(menuFrame, buttonHolder);

        // Initialise buttons to add
        AppButton button1 = new AppButton("Button 1");
        button1.transform.setSize(-20, 34, 1, 0);
        button1.transform.addConstraint(new UISizeConstraint(0,300,0,9999));
        button1.addActionListener(e -> WaveAPI.debugButton());

        AppButton button2 = new AppButton("Button 2");
        button2.transform.setSize(-20, 34, 1, 0);
        button2.addActionListener(e -> WaveAPI.debugButton());
        WaveGraphics.addChild(buttonHolder, button1);
        WaveGraphics.addChild(buttonHolder, button2);

        // Create divider
        AppFrame dividerFrame = new AppFrame();
        dividerFrame.setBackground(AppTheme.error);
        dividerFrame.transform.setSize(2, 0, 0, 1);
        dividerFrame.transform.setPosition(40, 0, 0.3, 0);
        dividerFrame.transform.addConstraint(new UIPositionConstraint(290,440,0,9999));

        WaveGraphics.addChild(menuFrame, dividerFrame);

        // Add the frames to the main AppContainer
        mainContainer.add(menuFrame);
    }
}