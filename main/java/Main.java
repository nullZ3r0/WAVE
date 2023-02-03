import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");

        // Initialise application window
        AppWindow mainWindow = new AppWindow();

        // Initialise application panels
        AppPanel mainPanel = new AppPanel();
        AppPanel innerPanel = new AppPanel();

        // Render it on mainWindow
        mainWindow.add(mainPanel);
        mainPanel.autoSize.setDimensions(-40, -40, 1, 1);
        mainPanel.autoPosition.setDimensions(20, 20, 0, 0);
        mainPanel.autoBounds();

        mainPanel.add(innerPanel);
        innerPanel.setBackground(Color.lightGray);
        innerPanel.autoSize.setDimensions(-40, -40, 1, 0.5);
        innerPanel.autoPosition.setDimensions(20, 20, 0, 0.5);
        innerPanel.autoBounds();
    }
}