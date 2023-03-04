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
    public int cornerRadius = 0;
}

class VisualiserTheme
{
    public Color background;
    public Color boardBackground;
    public Color whiteKeyBackground;
    public Color whiteKeyForeground;
    public Color whiteKeyPlaying;
    public Color blackKeyBackground;
    public Color blackKeyForeground;
    public Color blackKeyPlaying;
    public Color keyPressed;
    public Color octaveDividerLight1;
    public Color octaveDivider;
}
class ButtonTheme
{
    public Color background;
    public Color backgroundHover;
    public Color backgroundPressed;
    public Color foreground;
    public Color foregroundHover;
    public Font font;
    public int cornerRadius = 0;
}

interface UITheme
{
    static void apply() {};
}

public class AppTheme
{
    public static String name = "none";
    public static Color error = new Color(220,28, 227);
    public static Color white;
    public static Color window;
    public static Color backgroundLight3;
    public static Color backgroundLight2;
    public static Color backgroundLight1;
    public static Color background;
    public static Color backgroundDark1;
    public static Color backgroundDark2;
    public static Color backgroundDark3;

    // Bugs occur when a container with children is set to have a transparent background
    public static Color transparent = new Color(255, 255, 255, 0);
    public static Color foreground;
    public static Font titleFont;
    public static Font textFont;
    private static HashMap<String, Color> customColours = new HashMap<String, Color>();
    private static HashMap<String, Font> fonts = new HashMap<String, Font>();

    public static ButtonTheme button;
    public static FrameTheme frame;

    public static VisualiserTheme visualiser;

    public static Font getFont(String fontName)
    {
        Font retrieved = fonts.get(fontName);
        if (retrieved == null)
        {
            System.out.println("This font could not be found!");
            retrieved = fonts.get("error");;
        }

        return retrieved;
    }

    public static void addFont(String fontKey, Font font)
    {
        fonts.put(fontKey, font);
    }

    public static void setup()
    {
        fonts.put("error", new JLabel().getFont());
        StandardTheme.apply();
    }
}