import java.io.File;

public class Wave implements Runnable
{
    public static AppVisualiser visualiser;
    public static MidiPlayer midiPlayer;
    public static MidiInputReceiver midiInputReceiver;
    public static MidiSequence midiSequence;
    public static MidiKeyboard playerKeyboard = new MidiKeyboard();
    public static MidiKeyboard midiKeyboard = new MidiKeyboard();

    /** Methods **/
    public static void changePianoKeyPressed(int keyValue, int keyVelocity, boolean keyPressed)
    {
        // Play or stop the note
        if (midiPlayer != null)
        {
            if (keyPressed)
            {
                midiPlayer.playNote(keyValue, keyVelocity);
            }
            else
            {
                midiPlayer.stopNote(keyValue);
            }
        }

        // Send visual feedback
        if (visualiser != null)
        {
            visualiser.keyboard.changeKeyPressed(keyValue, keyVelocity, keyPressed);
        }
    }

    public static void loadNoteActions()
    {
        // Send visual feedback
        if (visualiser != null && midiSequence != null)
        {
            visualiser.keyboard.setNoteActions(midiSequence.getNoteActions());
        }
    }

    public static void loadMidiSequence()
    {
        if (midiSequence != null)
        {
            midiSequence.load();
        }
    }

    public static void playMidiSequence()
    {
        if (midiSequence != null)
        {
            midiSequence.start();
        }
    }

    public static void pauseMidiSequence()
    {
        if (midiSequence != null)
        {
            midiSequence.pause = true;
        }
    }

    public static void setPlayhead(long tick)
    {
        if (visualiser != null)
        {
            visualiser.keyboard.setPlayheadTick(tick);
        }
    }

    @Override
    public void run()
    {

    }

    /** Getters & Setters **/
    public static void setVisualiser(AppVisualiser set)
    {
        visualiser = set;
    }
    public static void setMidiPlayer(MidiPlayer set)
    {
        midiPlayer = set;
    }
    public static void setMidiInputReceiver(MidiInputReceiver set)
    {
        midiInputReceiver = set;
    }
    public static void setMidiSequence(File midiFile)
    {
        if (midiSequence != null)
        {
            // Do some stuff first
            midiSequence = null;
        }
        midiSequence = new MidiSequence(midiFile);
    }
    public static void setMidiSequence(String filePath)
    {
        File midiFile = new File(filePath);
        if (midiSequence != null)
        {
            // Do some stuff first
            midiSequence = null;
        }
        midiSequence = new MidiSequence(midiFile);
    }
}
