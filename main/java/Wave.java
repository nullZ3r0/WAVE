import java.io.File;

public class Wave implements Runnable
{
    public static AppVisualiser visualiser;
    public static MidiPlayer midiPlayer;
    public static MidiInputReceiver midiInputReceiver;
    public static MidiSequence midiSequence;
    public static MidiKeyboard playerKeyboard = new MidiKeyboard();
    public static MidiKeyboard midiKeyboard = new MidiKeyboard();
    private static long tickAcceptance = 8;
    private static long tickCurrent = 0;
    private static boolean enableFeedback = true;

    /** Methods **/
    private static void applyFeedback(PianoAction pianoAction)
    {
        pianoAction.isCorrect = false;
        if (midiSequence != null)
        {
            // Get midiKeyboard PianoAction and compare
            PianoAction comparisonPianoAction = midiSequence.getNoteActionFromPianoAction(pianoAction, tickAcceptance);

            if (comparisonPianoAction != null)
            {
                long noteTickStart = comparisonPianoAction.tickStart;
                long noteTickEnd = comparisonPianoAction.tickStart + comparisonPianoAction.tickDuration;

                if (pianoAction.isPressed)
                {
                    if (pianoAction.tickStart >= noteTickStart - tickAcceptance && pianoAction.tickStart <= noteTickEnd)
                    {
                        pianoAction.tickDuration = noteTickEnd - comparisonPianoAction.tickStart;
                        pianoAction.isCorrect = true;
                    }
                }
                else
                {
                    pianoAction.isCorrect = true;
                }
            }
        }
    }

    public static void changePlayerKeyboardPressed(PianoAction pianoAction)
    {
        // Update player keyboard
        playerKeyboard.changeKeyPressed(pianoAction.midiValue, pianoAction.midiVelocity, pianoAction.isPressed);

        // Send visual feedback
        if (visualiser != null)
        {
            if (enableFeedback)
            {
                // Do basic feedback
                pianoAction.tickStart = tickCurrent;
                applyFeedback(pianoAction);
            }
            else
            {
                pianoAction.tickStart = -9999;
                pianoAction.tickDuration = 0;
                pianoAction.isCorrect = true;
            }
            visualiser.keyboard.changeKeyTick(pianoAction.midiValue, pianoAction.tickStart, pianoAction.tickDuration);
            visualiser.keyboard.changeKeyPressed(pianoAction);
        }
    }

    public static void changeMidiKeyboardPressed(PianoAction pianoAction)
    {
        // Play or stop the note
        if (midiPlayer != null)
        {
            if (pianoAction.isPressed)
            {
                midiPlayer.playNote(pianoAction.midiValue, pianoAction.midiVelocity);
            }
            else
            {
                midiPlayer.stopNote(pianoAction.midiValue);
            }

            visualiser.keyboard.getKey(pianoAction.midiValue).isPlaying = pianoAction.isPressed;
            //visualiser.keyboard.changeKeyTick(pianoAction.midiValue, pianoAction.tickStart, pianoAction.tickDuration);
            //visualiser.keyboard.changeKeyPressed(pianoAction);
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
            if (visualiser != null)
            {
                visualiser.keyboard.tickPerBar = midiSequence.getTickPerBar();
            }
        }
    }

    public static void startMidiSequence()
    {
        if (midiSequence != null)
        {
            midiSequence.play();
        }
    }

    public static void togglePlayMidiSequence()
    {
        if (midiSequence != null)
        {
            if (midiSequence.isRunning())
            {
                if (midiSequence.isDone())
                {
                    midiSequence.restart();
                }
                else
                {
                    if (midiSequence.isPlaying())
                    {
                        midiSequence.pause();
                    }
                    else
                    {
                        midiSequence.play();
                    }
                }
            }
        }
    }
    public static void playMidiSequence()
    {
        if (midiSequence != null)
        {
            //midiSequence.pause();
        }
    }
    public static void pauseMidiSequence()
    {
        if (midiSequence != null)
        {
            //midiSequence.resume();
        }
    }

    public static void tickUpdate(long tick)
    {
        tickCurrent = tick;

        if (visualiser != null)
        {
            visualiser.keyboard.setTickCurrent(tick);
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

    public static void enableFeedback(boolean enable)
    {
        if (visualiser != null)
        {
            enableFeedback = enable;
            for (PianoAction pianoAction : visualiser.keyboard.getKeys())
            {
                pianoAction.isFeedback = enableFeedback;
            }
        }
        else
        {
            System.out.println("Must set a visualiser first!");
        }
    }
}
