import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
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
    public Color backgroundLight50;
    public Color foreground;
    public Color foregroundLight50;
    public Color boardBackground;
    public Color whiteKeyBackground;
    public Color whiteKeyForeground;
    public Color whiteKeyPlaying;
    public Color blackKeyBackground;
    public Color blackKeyForeground;
    public Color blackKeyPlaying;
    public Color keyPlaying;
    public Color whiteKeyPressedCorrect = new Color(130, 177, 109);;
    public Color whiteKeyPressedIncorrect = new Color(221, 116, 116);;
    public Color blackKeyPressedCorrect = AppTheme.darkenColor(whiteKeyPressedCorrect, 0.5);
    public Color blackKeyPressedIncorrect = AppTheme.darkenColor(whiteKeyPressedIncorrect, 0.5);
    public Color octaveDividerLight1;
    public Color octaveDivider;
    public Color whiteNoteBackground;
    public Color blackNoteBackground;
    public Color whiteNoteForeground;
    public Color blackNoteForeground;
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

public class AppTheme
{
    public static String name = "none";
    public static final Color error = new Color(220,28, 227);
    public static final Color white = new Color(255, 255, 255);;
    public static final Color black = new Color(0, 0, 0);

    // Bugs occur when a container with children is set to have a transparent background
    public static final Color transparent = new Color(255, 255, 255, 0);
    public static Color window;
    public static Color backgroundLight3;
    public static Color backgroundLight2;
    public static Color backgroundLight1;
    public static Color background;
    public static Color backgroundDark1;
    public static Color backgroundDark2;
    public static Color backgroundDark3;
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

    public static Color mixColor(Color color1, Color color2, double percent)
    {
        double inversePercent = 1.0 - percent;
        int redPart = (int) (color1.getRed()*percent + color2.getRed() * inversePercent);
        int greenPart = (int) (color1.getGreen()*percent + color2.getGreen() * inversePercent);
        int bluePart = (int) (color1.getBlue()*percent + color2.getBlue() * inversePercent);
        int alphaPart = (int) (color1.getAlpha()*percent + color2.getAlpha() * inversePercent);
        return new Color(redPart, greenPart, bluePart, alphaPart);
    }

    public static Color darkenColor(Color color, double percent)
    {
        return mixColor(color, AppTheme.black, percent);
    }

    public static Color fadeColor(Color color, double percent)
    {
        double inversePercent = 1.0 - percent;
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * inversePercent));
    }

    public static void addFont(String fontKey, Font font)
    {
        fonts.put(fontKey, font);
    }

    public static ColorUIResource colorUIResourceFromColor(Color color)
    {
        return new ColorUIResource(color);
    }

    public static void setup()
    {
        fonts.put("error", new JLabel().getFont());
        UITheme StandardTheme = new StandardTheme();
        UITheme LaloTheme = new LaloTheme();
        StandardTheme.apply();
    }
}