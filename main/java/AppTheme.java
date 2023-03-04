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
        name = "Kinetik";
        backgroundLight3 = new Color(79, 82, 106);
        backgroundLight2 = new Color(68, 71, 91);
        backgroundLight1 = new Color(57, 59, 77);
        background = new Color(46, 48, 62);
        backgroundDark1  = new Color(35, 37, 47);
        backgroundDark2  = new Color(24, 25, 33);
        backgroundDark3  = new Color(13, 14, 18);

        white = new Color(255, 255, 255);
        Color white25 = new Color(255, 255, 255,192);
        Color white50 = new Color(255, 255, 255,128);

        Color uniqueBackground = new Color(51, 178, 115);

        window = backgroundDark3;
        foreground = new Color(255, 255, 255);

        button = new ButtonTheme();
        button.background = backgroundDark2;
        button.backgroundHover = backgroundLight1;
        button.backgroundPressed = backgroundLight1;
        button.foreground = white25;
        button.foregroundHover = white;
        button.cornerRadius = 8;

        frame = new FrameTheme();
        frame.background = background;
        frame.backgroundHover = background;
        frame.backgroundPressed = background;
        frame.foreground = foreground;

        visualiser = new VisualiserTheme();
        visualiser.background = background;
        visualiser.boardBackground = backgroundDark3;
        visualiser.whiteKeyBackground = white;
        visualiser.whiteKeyForeground = backgroundDark2;
        visualiser.whiteKeyPlaying = new Color(140, 109, 177);
        visualiser.blackKeyBackground = backgroundDark3;
        visualiser.blackKeyForeground = white;
        visualiser.blackKeyPlaying = new Color(48, 38, 61);
        visualiser.keyPressed = new Color(236, 151, 62);
        visualiser.octaveDividerLight1 = backgroundDark1;
        visualiser.octaveDivider = backgroundDark3;

        // Example of setting a custom colour with its own name
        customColours.put("uniqueSpecial", uniqueBackground);

        // Initialise custom fonts
        try
        {
            Font nunitoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("main/assets/fonts/Nunito-ExtraBold.ttf"));
            titleFont = nunitoExtraBold;

            Font nunitoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("main/assets/fonts/Nunito-Regular.ttf"));
            textFont = nunitoRegular;

            //Font nunitoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("main/assets/fonts/Nunito-ExtraBold.ttf"));
            button.font = nunitoExtraBold;

            // Example of setting the fonts
            fonts.put("extraBold", nunitoExtraBold);
            fonts.put("regular", nunitoRegular);
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
