import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StandardTheme extends UITheme
{
    public void apply()
    {
        ClassLoader classLoader = this.getClass().getClassLoader();

        AppTheme.name = "Default";
        AppTheme.backgroundLight3 = new Color(79, 82, 106);
        AppTheme.backgroundLight2 = new Color(68, 71, 91);
        AppTheme.backgroundLight1 = new Color(57, 59, 77);
        AppTheme.background = new Color(46, 48, 62);
        AppTheme.backgroundDark1  = new Color(35, 37, 47);
        AppTheme.backgroundDark2  = new Color(24, 25, 33);
        AppTheme.backgroundDark3  = new Color(13, 14, 18);

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
        AppTheme.visualiser.backgroundLight50 = AppTheme.fadeColor(AppTheme.black, 0.5);
        AppTheme.visualiser.foreground = AppTheme.white;
        AppTheme.visualiser.foregroundLight50 = AppTheme.fadeColor(AppTheme.visualiser.foreground, 0.5);
        AppTheme.visualiser.boardBackground = AppTheme.backgroundDark3;
        AppTheme.visualiser.whiteKeyBackground = AppTheme.white;
        AppTheme.visualiser.whiteKeyForeground = AppTheme.backgroundDark2;
        AppTheme.visualiser.whiteKeyPlaying = new Color(88, 135, 211);
        AppTheme.visualiser.blackKeyBackground = AppTheme.backgroundDark3;
        AppTheme.visualiser.blackKeyForeground = AppTheme.white;
        AppTheme.visualiser.blackKeyPlaying = AppTheme.darkenColor(AppTheme.visualiser.whiteKeyPlaying, 0.5);
        AppTheme.visualiser.octaveDividerLight1 = AppTheme.backgroundDark1;
        AppTheme.visualiser.octaveDivider = AppTheme.backgroundDark3;
        AppTheme.visualiser.keyPlaying = new Color(236, 151, 62);
        AppTheme.visualiser.whiteNoteBackground = new Color(195, 160, 255);
        AppTheme.visualiser.whiteNoteForeground = AppTheme.darkenColor(AppTheme.visualiser.whiteNoteBackground, 0.333);
        AppTheme.visualiser.blackNoteBackground = AppTheme.darkenColor(AppTheme.visualiser.whiteNoteBackground, 0.666);
        AppTheme.visualiser.blackNoteForeground = AppTheme.darkenColor(AppTheme.visualiser.whiteNoteForeground, 0.666);

        //AppTheme.visualiser.whiteKeyPlaying = new Color(69, 126, 217);

        // Initialise custom fonts
        try
        {
            Font nunitoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("main/resources/fonts/Nunito-ExtraBold.ttf"));
            Font nunitoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("main/resources/fonts/Nunito-Regular.ttf"));

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

        // Have to change the look of the window using Flatlaf
        UIManager.put("TitlePane.font", AppTheme.titleFont.deriveFont(Font.BOLD, 12));
        UIManager.put("TitlePane.foreground", AppTheme.colorUIResourceFromColor(AppTheme.foreground));
        UIManager.put("TitlePane.buttonHoverBackground", AppTheme.colorUIResourceFromColor(AppTheme.background));
        UIManager.put("TitlePane.buttonPressedBackground", AppTheme.colorUIResourceFromColor(AppTheme.backgroundDark1));
        UIManager.put("RootPane.background", AppTheme.colorUIResourceFromColor(AppTheme.backgroundDark3));

    }
}
