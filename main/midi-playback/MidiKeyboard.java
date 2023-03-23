import java.util.ArrayList;

class MidiKeyboard
{
    /** Keyboard Public Attributes **/
    public final int rootKeyValue = 21; // This is A0 on a piano, anything before this won't be rendered
    public final int tailKeyValue = 108; // This is C8 on a piano, anything after this won't be rendered

    /** Keyboard Private Attributes **/
    private boolean useAutoDimensions = false;
    private AppVisualiser visualiser;
    private PianoAction[] keys; // There are 88 keys on a modern piano
    private ArrayList<PianoAction> noteActions;
    public long playheadTick = 0;
    public long playbackTick = 1000;
    private int keyboardHeight = 100;
    private int blackKeyHeight = 65;
    private int whiteKeyWidth = 24;
    private int blackKeyWidth = 18;
    private int bufferWidth = 0;

    /** Constructors **/
    MidiKeyboard()
    {
        //keys = new PianoAction[88];
        keys = new PianoAction[88];

        // Setup back-end for the keyboard
        for (int i = 0; i < keys.length; i++)
        {
            keys[i] = new PianoAction(21 + i);
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

    public void changeKeyPressed(int keyValue, int velocity, boolean isPressed)
    {
        if (keyValue >= this.rootKeyValue && keyValue <= this.tailKeyValue)
        {
            keys[keyValue - rootKeyValue].isPressed = isPressed;
            keys[keyValue - rootKeyValue].midiVelocity = velocity;
            if (this.visualiser != null)
            {
                this.visualiser.repaint();
            }
        }
        else
        {
            // This is out of range!
        }
    }

    public void setPlayheadTick(long tick)
    {
        playheadTick = tick;
        if (this.visualiser != null)
        {
            this.visualiser.repaint();
        }
    }

    /** Getters & Setters **/
    public int getHeight() {return keyboardHeight;}
    public void setHeight(int set) {keyboardHeight = set;}
    public int getWhiteKeyWidth() {return whiteKeyWidth;}
    public void setWhiteKeyWidth(int set) {whiteKeyWidth = set;}
    public int getBlackKeyWidth() {return blackKeyWidth;}
    public void setBlackKeyWidth(int set) {blackKeyWidth = set;}
    public int getBlackKeyHeight() {return blackKeyHeight;}
    public void setBlackKeyHeight(int set) {blackKeyHeight = set;}
    public boolean useAutoDimensions() {return useAutoDimensions;}
    public void useAutoDimensions(boolean set) {useAutoDimensions = set;}
    public PianoAction[] getKeys() {return keys;}
    public void setBufferWidth(int set) {bufferWidth = set;}
    public int getBufferWidth() {return bufferWidth;}
    public void setVisualiser(AppVisualiser visualiser) {this.visualiser = visualiser;}
    public void setNoteActions(ArrayList<PianoAction> noteActions)
    {
        this.noteActions = noteActions;
    }
    public ArrayList<PianoAction> getNoteActions()
    {
        return this.noteActions;
    }
}
