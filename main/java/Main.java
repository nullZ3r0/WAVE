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
        customRenderer.start();

        // Naming hierarchy and Class hierarchy
        // appWindow (AppWindow) -> appCanvas (AppCanvas) -> appPanel (AppFrame) -> appContainers (AppFrame) -> appFrames (AppFrame) / appButtons (AppButton)
        // AppWindow -> AppCanvas -> AppFrame -> AppButton

        // Initialise application window
        AppWindow mainWindow = new AppWindow();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setMinimumSize(new Dimension(1000, 500));

        // Initialise the main canvas
        AppCanvas mainCanvas = new AppCanvas();

        // Add the main canvas to the application window
        mainWindow.add(mainCanvas);

        // Initialise the menuPanel
        // NOTE: This "setBorder(new EmptyBorder())" stuff is hacky as f###
        // This will contain all the elements needed for the menu panel
        AppFrame menuPanel = new AppFrame();
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        menuPanel.setBorder(new EmptyBorder(50,0,0,0));

        // Create leftContainer
        AppFrame leftContainer = new AppFrame();
        leftContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        leftContainer.setBackground(AppTheme.backgroundDark1);
        leftContainer.transform.setSize(0, -100, 0.30, 1);
        leftContainer.transform.setPosition(0, 0, 0, 0);
        leftContainer.transform.addConstraint(new UISizeConstraint(300,300,0,9999));
        leftContainer.transform.setCornerRadius(8);
        leftContainer.setBorder(new EmptyBorder(16,0,0,0));

        // Add the leftContainer to the menuPanel via the custom renderer
        WaveGraphics.addChild(menuPanel, leftContainer);

        // Initialise buttons to add
        AppButton button1 = new AppButton("Resume Practice");
        button1.transform.setSize(-48, 32, 1, 0);
        button1.addActionListener(e -> WaveAPI.hideMenu());

        AppButton button2 = new AppButton("Credits");
        button2.transform.setSize(-48, 32, 1, 0);
        button2.addActionListener(e -> WaveAPI.debugButton());

        WaveGraphics.addChild(leftContainer, button1);
        WaveGraphics.addChild(leftContainer, button2);

        // Initialise dummy buttons
        for (int i = 0; i < 10; i++)
        {
            AppButton dummyButton = new AppButton("Button " + String.valueOf(i + 1));
            dummyButton.transform.setSize(-48, 32, 1, 0);
            dummyButton.addActionListener(e -> WaveAPI.debugButton());
            WaveGraphics.addChild(leftContainer, dummyButton);
        }


        // Create dividerContainer
        AppFrame dividerContainer = new AppFrame();
        dividerContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
        dividerContainer.setBackground(AppTheme.transparent);
        dividerContainer.transform.setSize(50, -200, 0, 1);
        dividerContainer.setBorder(new EmptyBorder(-50,0,0,0));

        // Create divider
        AppFrame divider = new AppFrame();
        divider.setBackground(AppTheme.foreground);
        divider.transform.setSize(4, 0, 0, 1);
        divider.transform.setCornerRadius(4);

        // Add divider to the dividerFrame via WaveGraphics
        WaveGraphics.addChild(dividerContainer, divider);

        // Add dividerContainer to the menuPanel vai WaveGraphics
        WaveGraphics.addChild(menuPanel, dividerContainer);


        // Create rightContainer
        AppFrame rightContainer = new AppFrame();
        rightContainer.setLayout(new CardLayout());
        rightContainer.setBackground(AppTheme.backgroundDark1);
        rightContainer.transform.setCornerRadius(8);
        rightContainer.transform.setSize(600, -100, 0, 1);

        // Add rightFrame to the menuFrame vai WaveGraphics
        WaveGraphics.addChild(menuPanel, rightContainer);

        // Add the frames to the main AppContainer
        mainCanvas.add(menuPanel);
    }
}