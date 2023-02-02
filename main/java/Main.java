import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");

        // Initialise application window
        AppWindow mainWindow = new AppWindow();

        // Connect to our python helper
        PythonHandler helper = new PythonHandler("main/assets/python/gesture_helper.exe");
        System.out.println(helper.getFullPath());
    }
}