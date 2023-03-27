import javax.swing.*;
import java.awt.*;

public class WaveAPI
{
    public static void debugAction(String actionName)
    {
        switch (actionName)
        {
            case "togglePlay":
                Wave.togglePlayMidiSequence();
                break;
            case "increaseSpeed":
                Wave.increaseMidiSequenceSpeed();
                break;
            case "decreaseSpeed":
                Wave.decreaseMidiSequenceSpeed();
                break;
            case "zoomIn":
                Wave.zoomInVisualiser();
                break;
            case "zoomOut":
                Wave.zoomOutVisualiser();
                break;
            case "toggleMenu":
                WaveAPI.nextCard(Main.mainCanvas);
                Wave.pauseMidiSequence();
                break;
        }
    }
    public static void showCard(Container container, String name)
    {
        System.out.println("Test API: showCard");

        LayoutManager currentLayoutManager = container.getLayout();
        if (currentLayoutManager.getClass() == CardLayout.class)
        {
            CardLayout manager = (CardLayout) currentLayoutManager;
            manager.show(container, name);
            container.revalidate();
            container.repaint();
        }
    }

    public static void switchToCredits()
    {
        System.out.println("Test API: This show the creditsContainer on the menuPanel -> rightContainer");
    }

    public static void debugButton()
    {
        System.out.println("This button has been pressed");
    }

    public static void nextCard(Container container)
    {
        System.out.println("Test API: nextCard");

        LayoutManager currentLayoutManager = container.getLayout();
        if (currentLayoutManager.getClass() == CardLayout.class)
        {
            CardLayout manager = (CardLayout) currentLayoutManager;
            manager.next(container);
            container.revalidate();
            container.repaint();
        }
    }
}
