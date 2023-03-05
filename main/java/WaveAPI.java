import java.awt.*;

public class WaveAPI
{
    public static void connectReceiverToVisualiser(MidiInputReceiver receiver, AppVisualiser visualiser)
    {
        receiver.setVisualiser(visualiser);
    }

    public static void pressPianoKey(int keyValue, int velocity, boolean isPressed)
    {

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
