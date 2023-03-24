import java.util.ArrayList;

class MidiKeyboard
{
    /** Keyboard Public Attributes **/
    public int rootKeyValue = 21;
    public int keysVisible = 88;
    public long tickCurrent = 0;
    public long tickPerBar = 256;
    public long seekTick = 1000;

    /** Keyboard Private Attributes **/
    private boolean useAutoDimensions = false;
    private AppVisualiser visualiser;
    private PianoAction[] keys; // There are 88 keys on a modern piano
    private ArrayList<PianoAction> noteActions;
    private int keyboardHeight = 100;
    private int blackKeyHeight = 65;
    private int whiteKeyWidth = 24;
    private int blackKeyWidth = 18;
    private int bufferWidth = 0;

    /** Constructors **/
    MidiKeyboard()
    {
        this.keys = new PianoAction[128];
        this.noteActions = new ArrayList<>();
        this.rootKeyValue = 21; // A0
        this.keysVisible = 88;

        // Setup back-end for the keyboard
        for (int i = 0; i < this.keys.length; i++)
        {
            keys[i] = new PianoAction(i);
        }
    }

    /** Methods **/
    public void autoDimensions(int width)
    {
        // There are 52 white keys on a keyboard (making up its width)
        whiteKeyWidth = Math.min(width / this.getWhiteKeyCount(), 48);
        blackKeyWidth = (int) (whiteKeyWidth * 0.75);
        bufferWidth = Math.max(0, (width - (whiteKeyWidth * this.getWhiteKeyCount())) / 2);

        // A good height / width ratio
        keyboardHeight = (int) (whiteKeyWidth * 4.166666667);
        blackKeyHeight = (int) (whiteKeyWidth * 2.708333333);
    }

    public void changeKeyPressed(int keyValue, int velocity, boolean isPressed)
    {
        keys[keyValue].isPressed = isPressed;
        keys[keyValue].midiVelocity = velocity;

        // Only request a repaint if note is visible
        if (keyValue >= this.rootKeyValue && keyValue <= this.getTail())
        {
            if (this.visualiser != null)
            {
                this.visualiser.repaint();
            }
        }
    }

    public void changeKeyPressed(int keyValue, int velocity, boolean isPressed, boolean isCorrect)
    {
        keys[keyValue].isPressed = isPressed;
        keys[keyValue].isCorrect = isCorrect;
        keys[keyValue].midiVelocity = velocity;

        // Only request a repaint if note is visible
        if (keyValue >= this.rootKeyValue && keyValue <= this.getTail())
        {
            if (this.visualiser != null)
            {
                this.visualiser.repaint();
            }
        }
    }

    public void changeKeyPressed(PianoAction pianoAction)
    {
        this.keys[pianoAction.midiValue].isPressed = pianoAction.isPressed;
        this.keys[pianoAction.midiValue].isCorrect = pianoAction.isCorrect;
        this.keys[pianoAction.midiValue].midiVelocity = pianoAction.midiVelocity;

        // Only request a repaint if note is visible
        if (pianoAction.midiValue >= this.rootKeyValue && pianoAction.midiValue <= this.getTail())
        {
            if (this.visualiser != null)
            {
                this.visualiser.repaint();
            }
        }
    }

    public void changeKeyTick(int keyValue, long tickStart, long tickDuration)
    {
        keys[keyValue].tickStart = tickStart;
        keys[keyValue].tickDuration = tickDuration;
    }

    public boolean pianoActionVisible(PianoAction pianoAction)
    {
        if (pianoAction.midiValue >= rootKeyValue && pianoAction.midiValue <= this.getTail())
        {
            return true;
        }
        return false;
    }

    public boolean noteActionVisible(PianoAction noteAction)
    {
        long currentPosition = noteAction.tickStart - this.tickCurrent;

        if (noteAction.midiValue >= this.rootKeyValue && noteAction.midiValue <= this.getTail() && currentPosition <= this.seekTick && currentPosition + noteAction.tickDuration >= 0 )
        {
            return true;
        }
        return false;
    }

    public void setTickCurrent(long tick)
    {
        tickCurrent = tick;
        if (this.visualiser != null)
        {
            this.visualiser.repaint();
        }
    }

    /** Getters & Setters **/
    public int getWhiteKeyCount()
    {
        int count = 0;
        for (PianoAction pianoAction : this.keys)
        {
            if (pianoAction.isWhite && this.pianoActionVisible(pianoAction))
            {
                count++;
            }
        }
        return count;
    }

    public int getTail()
    {
        return this.rootKeyValue + this.keysVisible;
    }

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
    public PianoAction getKey(int midiValue) {return keys[midiValue];}
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
