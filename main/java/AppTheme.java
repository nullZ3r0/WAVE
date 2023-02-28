import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

class FrameTheme
{
    public Color background;
    public Color backgroundHover;
    public Color backgroundPressed;
    public Color foreground;
    public int cornerRadius = 0; // Needs to be implemented
}
class ButtonTheme
{
    public Color background;
    public Color backgroundHover;
    public Color backgroundPressed;
    public Color foreground;
    public Font font;
    public int cornerRadius = 0;
}

public class AppTheme
{
    public static Color error = new Color(220,28, 227);
    public static Color window;
    public static Color background;
    public static Color foreground;
    public static Font titleFont;
    public static Font textFont;
    private static HashMap<String, Color> customColours = new HashMap<String, Color>();

    public static ButtonTheme button;
    public static FrameTheme frame;

    public static Color getCustomColor(String colorName)
    {
        Color retrieved = AppTheme.customColours.get(colorName);
        if (retrieved == null)
        {
            System.out.println("This custom colour could not be found!");
            retrieved = error;
        }

        return retrieved;
    }

    private static void StandardTheme()
    {
        Color backgroundLight3 = new Color(79, 82, 106);
        Color backgroundLight2 = new Color(68, 71, 91);
        Color backgroundLight1 = new Color(57, 59, 77);
        background = new Color(46, 48, 62);
        Color backgroundDark1  = new Color(35, 37, 47);
        Color backgroundDark2  = new Color(24, 25, 33);
        Color backgroundDark3  = new Color(13, 14, 18);

        Color uniqueBackground = new Color(51, 178, 115);

        window = backgroundDark3;
        foreground = new Color(255, 255, 255);

        button = new ButtonTheme();
        button.background = backgroundDark2;
        button.backgroundHover = backgroundLight2;
        button.backgroundPressed = backgroundDark3;
        button.foreground = foreground;
        button.cornerRadius = 12;

        frame = new FrameTheme();
        frame.background = background;
        frame.backgroundHover = background;
        frame.backgroundPressed = background;
        frame.foreground = foreground;

        // Example of setting a custom colour with its own name
        customColours.put("uniqueSpecial", uniqueBackground);

        // Initialise custom fonts
        try
        {
            Font nunitoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("main/assets/fonts/Nunito-ExtraBold.ttf"));
            titleFont = nunitoExtraBold;

            Font nunitoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("main/assets/fonts/Nunito-Regular.ttf"));
            textFont = nunitoRegular;
            button.font = nunitoRegular;
        }
        catch(IOException | FontFormatException e)
        {
            System.out.println("Failed to apply custom fonts");
        }
    }

    public static void setup()
    {
        StandardTheme();
    }
}
