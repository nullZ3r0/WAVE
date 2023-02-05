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

        // Initialise application window
        AppWindow mainWindow = new AppWindow();

        // Initialise the main container
        AppContainer mainContainer = new AppContainer();

        // Add the main container to the application window
        mainWindow.add(mainContainer);

        // Example: switching between two different frames and changing the background colour of one
        // Initialise the first frame
        AppFrame frame1 = new AppFrame();
        frame1.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 12));

        // Initialise buttons to add
        AppButton button1 = new AppButton("Switch to frame2");

        // We will call an API method called nextCard, passing it an AppContainer directly
        button1.addActionListener(e -> WaveAPI.nextCard(mainContainer));

        // Add the button to the first frame
        frame1.add(button1);

        // Initialise the second frame
        AppFrame frame2 = new AppFrame();
        frame2.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 12));

        // This custom colour doesn't exist in the standard theme, let's see what happens
        frame2.setBackground(AppTheme.getCustomColor("uniqueSpecial2"));

        // Initialise first button to add
        AppButton button2 = new AppButton("Switch to frame1");

        // We will call an API method called nextCard, passing it a Container (Usually an AppContainer) indirectly (for learning purposes)
        // Why does getParent need to be called twice?
        button2.addActionListener(e -> WaveAPI.nextCard(button2.getParent().getParent()));

        // We will call an API method called test, passing it a Container (Usually an AppFrame)
        AppButton button3 = new AppButton("Change background colour");
        button3.addActionListener(e -> WaveAPI.test(frame2));

        // Add the buttons to the second frame
        frame2.add(button2);
        frame2.add(button3);

        // Add the frames to the main AppContainer
        mainContainer.add(frame1);
        mainContainer.add(frame2);
    }
}