import java.awt.*;

public class WaveAPI
{
    public static void test(Container frame)
    {
        // The API has been written to accept a Container for the sake of compatibility with swing syntax [.getParent() -> Container]
        // If we want to perform actions that are only compatible with our own swing extended classes, just do a class check
        // Example: frame.getClass() == AppFrame.class
        System.out.println("Test API: test");

        // We will change the colour of an AppFrame
        Color currentColor = frame.getBackground();
        if (currentColor.equals(AppTheme.customColours.get("uniqueSpecial")))
        {
            frame.setBackground(new Color(116, 65, 204));
        }
        else
        {
            frame.setBackground(AppTheme.customColours.get("uniqueSpecial"));
        }
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
