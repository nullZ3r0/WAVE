import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;

class Keyboard
{
    class PianoKey
    {
        /** Attributes **/
        public boolean isWhite = true;

        /** MIDI File input attributes **/
        public boolean isPlaying = false;

        /** MIDI Piano input attributes **/
        public boolean isPressed = false;

        PianoKey(){}
        PianoKey(boolean _isWhite)
        {
            isWhite = _isWhite;
        }
    }

    /** Keyboard Public Attributes **/
    public final int rootNote = 21; // This is A0 on a piano, anything before this won't be rendered
    public final int tailNote = 108; // This is C8 on a piano, anything after this won't be rendered

    /** Keyboard Private Attributes **/
    private boolean useAutoDimensions = false;
    private PianoKey[] keys = new PianoKey[88]; // This is how much keys are on a modern piano
    private int keyboardHeight = 100;
    private int blackKeyHeight = 65;
    private int whiteKeyWidth = 24;
    private int blackKeyWidth = 18;
    private int bufferWidth = 0;
    private Color boardBackground;
    private Color whiteKeyBackground;
    private Color whiteKeyForeground;
    private Color blackKeyBackground;
    private Color blackKeyForeground;

    /** Constructors **/
    Keyboard()
    {
        boardBackground = AppTheme.visualiser.boardBackground;
        whiteKeyBackground = AppTheme.visualiser.whiteKeyBackground;
        whiteKeyForeground = AppTheme.visualiser.whiteKeyForeground;
        blackKeyBackground = AppTheme.visualiser.blackKeyBackground;
        blackKeyForeground = AppTheme.visualiser.blackKeyForeground;

        // Setup back-end for the keyboard
        for (int i = 0; i < keys.length; i++)
        {
            // Octave starting at A0 key colours
            // 1 2 3 4 5 6 7 8 9 10 11 12
            // W B W W B W B W W B  W  B

            int keyPosition = (i + 1) % 12;
            switch(keyPosition)
            {
                case 0:
                case 2:
                case 5:
                case 7:
                case 10:
                    keys[i] = new PianoKey(false);
                    break;
                default:
                    keys[i] = new PianoKey();
            }
        }
    }

    /** Methods **/
    public void autoDimensions(int width)
    {
        // There are 52 white keys on a keyboard (making up its width)
        whiteKeyWidth = Math.min(width / 52, 24);
        blackKeyWidth = (int) (whiteKeyWidth * 0.75);
        bufferWidth = Math.max(0, (width - (whiteKeyWidth * 52)) / 2);

        // A good height / width ratio
        keyboardHeight = (int) (whiteKeyWidth * 4.166666667);
        blackKeyHeight = (int) (whiteKeyWidth * 2.708333333);
    }

    /** Getters & Setters **/
    public int getHeight() {return keyboardHeight;}
    public void setHeight(int set) {keyboardHeight = set;}
    public Color getBoardBackground() {return boardBackground;}
    public void setBoardBackground(Color set) {boardBackground = set;}
    public Color getWhiteKeyBackground() {return whiteKeyBackground;}
    public void setWhiteKeyBackground(Color set) {whiteKeyBackground = set;}
    public Color getWhiteKeyForeground() {return whiteKeyForeground;}
    public void setWhiteKeyForeground(Color set) {whiteKeyForeground = set;}
    public Color getBlackKeyBackground() {return blackKeyBackground;}
    public void setBlackKeyBackground(Color set) {blackKeyBackground = set;}
    public Color getBlackKeyForeground() {return blackKeyForeground;}
    public void setBlackKeyForeground(Color set) {blackKeyForeground = set;}
    public int getWhiteKeyWidth() {return whiteKeyWidth;}
    public void setWhiteKeyWidth(int set) {whiteKeyWidth = set;}
    public int getBlackKeyWidth() {return blackKeyWidth;}
    public void setBlackKeyWidth(int set) {blackKeyWidth = set;}
    public int getBlackKeyHeight() {return blackKeyHeight;}
    public void setBlackKeyHeight(int set) {blackKeyHeight = set;}
    public boolean useAutoDimensions() {return useAutoDimensions;}
    public void useAutoDimensions(boolean set) {useAutoDimensions = set;}
    public PianoKey[] getKeys() {return keys;}
    public void setBufferWidth(int set) {bufferWidth = set;}
    public int getBufferWidth() {return bufferWidth;}
}

public class AppVisualiser extends JPanel
{
    /** Public AppVisualiser Attributes **/
    public UITransform transform;
    public Keyboard keyboard;

    /** Private AppVisualiser Attributes **/
    private Component parent;
    private Boolean useTransform = false;
    private Boolean useWaveGraphics = false;

    /** Constructors **/
    public AppVisualiser()
    {
        this.setBackground(AppTheme.visualiser.background);
        this.setOpaque(true);
        this.transform = new UITransform();
        this.keyboard = new Keyboard();
    }

    /** Overridden Methods **/
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

    /** Getters & Setters **/
    public Boolean useTransform() {return useTransform;}
    public void useTransform(Boolean set) {useTransform = set;}
    public Boolean useWaveGraphics() {return useWaveGraphics;}
    public void useWaveGraphics(Boolean set) {useWaveGraphics = set;}
}
