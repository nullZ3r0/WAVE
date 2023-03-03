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
        if (currentColor.equals(AppTheme.getCustomColor("uniqueSpecial")))
        {
            frame.setBackground(new Color(116, 65, 204));
        }
        else
        {
            frame.setBackground(AppTheme.getCustomColor("uniqueSpecial"));
        }
    }

    public static void hideMenu(menuPanel menuPanel)
    {
        System.out.println("API: This should hide the menuPanel");
        Container parent = menuPanel.self.getParent();
        if (parent != null)
        {
            parent.remove(menuPanel.self);
            parent.revalidate();
            parent.repaint();
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
