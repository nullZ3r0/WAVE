import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StandardTheme implements UITheme
{
    public static void apply()
    {
        AppTheme.name = "Kinetik";
        AppTheme.backgroundLight3 = new Color(79, 82, 106);
        AppTheme.backgroundLight2 = new Color(68, 71, 91);
        AppTheme.backgroundLight1 = new Color(57, 59, 77);
        AppTheme.background = new Color(46, 48, 62);
        AppTheme.backgroundDark1  = new Color(35, 37, 47);
        AppTheme.backgroundDark2  = new Color(24, 25, 33);
        AppTheme.backgroundDark3  = new Color(13, 14, 18);

        AppTheme.white = new Color(255, 255, 255);
        Color white25 = new Color(255, 255, 255,192);
        Color white50 = new Color(255, 255, 255,128);

        Color uniqueBackground = new Color(51, 178, 115);

        AppTheme.window = AppTheme.backgroundDark3;
        AppTheme.foreground = new Color(255, 255, 255);

        AppTheme.button = new ButtonTheme();
        AppTheme.button.background = AppTheme.backgroundDark2;
        AppTheme.button.backgroundHover = AppTheme.backgroundLight1;
        AppTheme.button.backgroundPressed = AppTheme.backgroundLight1;
        AppTheme.button.foreground = white25;
        AppTheme.button.foregroundHover = AppTheme.white;
        AppTheme.button.cornerRadius = 8;

        AppTheme.frame = new FrameTheme();
        AppTheme.frame.background = AppTheme.background;
        AppTheme.frame.backgroundHover = AppTheme.background;
        AppTheme.frame.backgroundPressed = AppTheme.background;
        AppTheme.frame.foreground = AppTheme.foreground;

        AppTheme.visualiser = new VisualiserTheme();
        AppTheme.visualiser.background = AppTheme.background;
        AppTheme.visualiser.boardBackground = AppTheme.backgroundDark3;
        AppTheme.visualiser.whiteKeyBackground = AppTheme.white;
        AppTheme.visualiser.whiteKeyForeground = AppTheme.backgroundDark2;
        AppTheme.visualiser.whiteKeyPlaying = new Color(140, 109, 177);
        AppTheme.visualiser.blackKeyBackground = AppTheme.backgroundDark3;
        AppTheme.visualiser.blackKeyForeground = AppTheme.white;
        AppTheme.visualiser.blackKeyPlaying = new Color(81, 65, 103);
        AppTheme.visualiser.keyPressed = new Color(236, 151, 62);
        AppTheme.visualiser.octaveDividerLight1 = AppTheme.backgroundDark1;
        AppTheme.visualiser.octaveDivider = AppTheme.backgroundDark3;

        // Initialise custom fonts
        try
        {
            Font nunitoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("main/assets/fonts/Nunito-ExtraBold.ttf"));
            Font nunitoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("main/assets/fonts/Nunito-Regular.ttf"));

            AppTheme.titleFont = nunitoExtraBold;
            AppTheme.textFont = nunitoRegular;
            AppTheme.button.font = nunitoExtraBold;

            // Example of setting the fonts
            AppTheme.addFont("extraBold", nunitoExtraBold);
            AppTheme.addFont("regular", nunitoRegular);
        }
        catch(IOException | FontFormatException e)
        {
            System.out.println("Failed to apply custom fonts");
        }
    }
}
