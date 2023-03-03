import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;


public class AppVisualiser extends JPanel
{
    class Keyboard
    {
        /** Keyboard Attributes **/
        private int keyboardHeight = 80;
        private int rootNote = 48; // This is C2 on a piano
        private Color keyboardBackground;

        /** Constructor **/
        Keyboard()
        {
            keyboardBackground = AppTheme.backgroundDark3;
        }

        /** Setters **/
        public void setHeight(int _height) {keyboardHeight = _height;}
        public void setBackground(Color _background) {keyboardBackground = _background;}

        /** Getters **/
        public int getHeight() {return keyboardHeight;}
        public Color getBackground() {return keyboardBackground;}
    }

    public UITransform transform;
    private Component parent;
    private Boolean useTransform = false;
    private Boolean useWaveGraphics = false;

    public Keyboard keyboard;

    public AppVisualiser()
    {
        this.setBackground(AppTheme.frame.background);
        this.setOpaque(true);
        this.transform = new UITransform();
        this.keyboard = new Keyboard();
    }

    // Custom render
    @Override
    protected void paintComponent(Graphics g)
    {
        if (useWaveGraphics == true)
        {
            // Render using WaveGraphics
            WaveGraphics.draw(this, g);
        }
        else
        {
            // Render using Java Swing
            super.paintComponent(g);
        }
    }

    public void useTransform(Boolean set) {useTransform = set;}
    public Boolean useTransform() {return useTransform;}
    public void useWaveGraphics(Boolean set) {useWaveGraphics = set;}

}
