import java.io.File;
import java.text.DecimalFormat;

public class Wave
{
    public static AppVisualiser visualiser;
    public static MidiPlayer midiPlayer;
    public static MidiInputReceiver midiInputReceiver;
    public static MidiSequence midiSequence;
    private static Thread midiSequenceThread;
    public static MidiKeyboard playerKeyboard = new MidiKeyboard();
    public static MidiKeyboard midiKeyboard = new MidiKeyboard();
    private static long tickAcceptance = 8;
    private static long tickCurrent = 0;
    private static boolean enableFeedback = true;
    private static boolean voiceControlReady = false;
    private static boolean gestureControlReady = false;

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

    public static void refreshKeyboards()
    {
        if (visualiserExists())
        {
            for (PianoAction pianoAction : visualiser.keyboard.getKeys())
            {
                pianoAction.isFeedback = enableFeedback;
                pianoAction.isPlaying = false;
                pianoAction.isPressed = false;
                pianoAction.isCorrect = false;
                pianoAction.tickStart = -9999;
                pianoAction.tickDuration = 0;
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

    public static void startMidiSequence()
    {
        if (midiSequenceExists())
        {
            if (midiSequenceThreadExists())
            {
                midiSequence.end();
                midiSequenceThread = null;
            }
            midiSequenceThread = new Thread(midiSequence);
            midiSequenceThread.start();
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

    public static void pauseMidiSequence()
    {
        if (midiSequenceExists() && midiSequence.isRunning())
        {
            midiSequence.pause();
        }
    }

    public static void resumeMidiSequence()
    {
        if (midiSequenceExists() && midiSequence.isRunning())
        {
            midiSequence.play();
        }
    }

    public static void restartMidiSequence()
    {
        if (midiSequenceExists() && midiSequence.isRunning())
        {
            midiSequence.restart();
        }
    }

    public static void increaseMidiSequenceSpeed()
    {
        if (midiSequenceExists())
        {
            midiSequence.increaseSpeed();
            if (visualiserExists())
            {
                visualiser.infoMidiFileSpeed = String.valueOf(midiSequence.getSpeedMultiplier());
                visualiser.repaint();
            }
        }
    }

    public static void decreaseMidiSequenceSpeed()
    {
        if (midiSequenceExists())
        {
            midiSequence.decreaseSpeed();
            if (visualiserExists())
            {
                visualiser.infoMidiFileSpeed = String.valueOf(midiSequence.getSpeedMultiplier());
                visualiser.repaint();
            }
        }
    }

    public static void zoomInVisualiser()
    {
        if (visualiserExists())
        {
            visualiser.keyboard.zoomIn();
        }
    }

    public static void zoomOutVisualiser()
    {
        if (visualiserExists())
        {
            visualiser.keyboard.zoomOut();
        }
    }

    public static boolean midiSequenceExists()
    {
        return midiSequence != null;
    }

    public static boolean midiSequenceThreadExists()
    {
        return midiSequenceThread != null;
    }

    public static boolean visualiserExists()
    {
        return visualiser != null;
    }

    public static void tickUpdate(long tick)
    {
        tickCurrent = tick;

        if (visualiser != null)
        {
            visualiser.keyboard.setTickCurrent(tick);
        }
    }

    public static void beatsPerMinuteUpdate(double beatsPerMinute)
    {
        if (visualiserExists())
        {
            DecimalFormat df = new DecimalFormat("###");
            visualiser.infoMidiFileBPM = df.format(beatsPerMinute);
        }
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
        if (midiSequenceExists())
        {
            midiSequence.restart();
            midiSequence.pause();
            midiSequence.setMidiFile(midiFile);
        }
        else
        {
            midiSequence = new MidiSequence(midiFile);
        }

        if (visualiserExists())
        {
            visualiser.infoMidiFileName = midiFile.getName();
            visualiser.keyboard.tickPerBar = midiSequence.getTickPerBar();
            visualiser.infoMidiFileSpeed = String.valueOf(midiSequence.getSpeedMultiplier());
            loadNoteActions();

            if (midiSequenceExists())
            {
                double calc1 = (double) midiSequence.getTickPerBar() * 2;
                visualiser.keyboard.setSeekTick((long) calc1);
            }

            visualiser.repaint();
        }

        tickAcceptance = (long) ((double) midiSequence.getTickPerBar() / 128.0);
        refreshKeyboards();
    }
    public static void setMidiSequence(String filePath)
    {
        File midiFile = new File(filePath);
        setMidiSequence(midiFile);
    }

    public static void enableFeedback(boolean enable)
    {
        if (visualiserExists())
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
