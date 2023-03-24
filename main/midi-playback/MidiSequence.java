import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

class TempoAction
{
    /** Attributes **/
    public long tickStart = 0;
    public int midiValue = 0;

    /** Constructors **/
    TempoAction(){}

    TempoAction(long tickStart, int midiValue)
    {
        this.tickStart = tickStart;
        this.midiValue = midiValue;
    }
}

class PianoAction
{
    /** Attributes **/
    public boolean isPressed = false;
    public boolean isPlaying = false;
    public boolean isCorrect = false;
    public boolean isFeedback = false;
    public boolean isWhite = true;
    public int midiValue = 0;
    public int midiVelocity = 0;
    public long tickStart = 0;
    public long tickDuration = 0;

    /** Constructors **/
    PianoAction(int midiValue)
    {
        this.midiValue = midiValue;

        switch(midiValue % 12)
        {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
                this.isWhite = false;
        }
    }

    PianoAction(long tickStart, int midiValue, int midiVelocity, boolean isPressed)
    {
        this(midiValue);
        this.tickStart = tickStart;
        this.midiVelocity = midiVelocity;
        this.isPressed = isPressed;
    }

    PianoAction(PianoAction pianoAction0, PianoAction pianoAction1)
    {
        this(pianoAction0.midiValue);
        this.midiVelocity = pianoAction0.midiVelocity;
        this.tickStart = pianoAction0.tickStart;
        this.tickDuration = pianoAction1.tickStart - pianoAction0.tickStart;
        this.isPressed = true;
    }
}

public class MidiSequence extends Thread
{
    private File midiFile;
    private Sequence sequence;
    public Boolean pause = false;
    private Boolean running = false;
    private int tempo = 500000;
    private int PPQ = 96; // How many ticks are in a beat
    private double beatsPerMinute = 120; // 120 BPM is the same as a tempo value of 500,000 microseconds per quarter note
    private double beatsPerBar = 4;
    private double tickDuration = 5; // In milliseconds
    private long currentTick = -512;
    private long duration = 0;
    private HashMap<Long, ArrayList<PianoAction>> pianoEvents = new HashMap<Long, ArrayList<PianoAction>>();
    public ArrayList<PianoAction> pianoActions = new ArrayList<PianoAction>();
    private HashMap<Long, ArrayList<TempoAction>> tempoEvents = new HashMap<Long, ArrayList<TempoAction>>();
    public ArrayList<TempoAction> tempoActions = new ArrayList<TempoAction>();
    public ArrayList<PianoAction> noteActions = new ArrayList<PianoAction>();

    MidiSequence(File midiFile)
    {
        try
        {
            this.midiFile = midiFile;
            // Accessing the MIDI file data using SEQUENCE class
            this.sequence = MidiSystem.getSequence(this.midiFile);
        }
        catch (InvalidMidiDataException e)
        {
            System.out.println("This is not a valid MIDI file!");
            this.midiFile = null;
            this.sequence = null;
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setTempo(int set)
    {
        this.tempo = set;
        this.beatsPerMinute = Math.round(60000000.0 / (double) this.tempo);
        this.tickDuration = 60000.0 / (this.beatsPerMinute * (double) this.PPQ);
    }

    public void setBeatsPerMinute(double set)
    {
        this.tempo = (int) Math.round(set / 0.00012);
        this.beatsPerMinute = this.tempo * 0.00012;
        this.tickDuration = 60000 / (this.beatsPerMinute * this.sequence.getResolution());
    }

    private void addPianoAction(long tick, PianoAction action)
    {
        duration = Math.max(duration, tick);

        ArrayList<PianoAction> event = pianoEvents.get(tick);
        if (event == null)
        {
            pianoEvents.put(tick, new ArrayList<PianoAction>());
            event = pianoEvents.get(tick);
        }

        event.add(action);
    }

    private void addTempoAction(long tick, TempoAction tempoAction)
    {
        ArrayList<TempoAction> event = tempoEvents.get(tick);
        if (event == null)
        {
            tempoEvents.put(tick, new ArrayList<TempoAction>());
            event = tempoEvents.get(tick);
        }

        event.add(tempoAction);
    }

    public void load()
    {
        // Variable to show the number of tracks in the MIDI file
        int trackNumber = 0;

        this.PPQ = this.sequence.getResolution();
        this.tickDuration = 60000 / (this.beatsPerMinute * this.sequence.getResolution());

        // Loop to go through every track of the MIDI file
        for (Track track: sequence.getTracks())
        {
            trackNumber++;

            // Loop to go through every event of the current track
            for (int i=0; i<track.size(); i++)
            {
                // New variable for the event
                MidiEvent event = track.get(i);

                // New variable for the info of the event
                MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage)
                {
                    // Don't know what this is
                    ShortMessage sm = (ShortMessage) message;
                    //System.out.println("Channel: " + sm.getChannel());

                    if (sm.getCommand() == ShortMessage.NOTE_ON)
                    {
                        if (sm.getData2() == 0)
                        {
                            addPianoAction(event.getTick(), new PianoAction(event.getTick(), sm.getData1(), sm.getData2(), false));
                        }
                        else
                        {
                            addPianoAction(event.getTick(), new PianoAction(event.getTick(), sm.getData1(), sm.getData2(), true));
                        }
                    }
                    else if (sm.getCommand() == ShortMessage.NOTE_OFF)
                    {
                        addPianoAction(event.getTick(), new PianoAction(event.getTick(), sm.getData1(), sm.getData2(), false));
                    }
                }
                else if (message instanceof MetaMessage)
                {
                    // New variable for the info of the event
                    MetaMessage metaMessage = (MetaMessage) message;

                    // Tempo change
                    if (metaMessage.getType() == 0x51)
                    {
                        byte[] data = metaMessage.getData();
                        int tempo = (data[0] & 0xff) << 16 | (data[1] & 0xff) << 8 | (data[2] & 0xff);

                        this.setTempo(tempo);
                        this.addTempoAction(event.getTick(), new TempoAction(event.getTick(), tempo));
                    }
                }
            }
        }

    }

    public ArrayList<PianoAction> getNoteActions()
    {
        ArrayList<PianoAction> pressActions = new ArrayList<PianoAction>();
        ArrayList<PianoAction> releaseActions = new ArrayList<PianoAction>();
        ArrayList<PianoAction> noteActions = new ArrayList<PianoAction>();

        // Sort events into 2 ArrayLists
        for (ArrayList<PianoAction> pianoEvent : pianoEvents.values())
        {
            for (PianoAction pianoAction : pianoEvent)
            {
                if (pianoAction.isPressed)
                {
                    pressActions.add(pianoAction);
                }
                else
                {
                    releaseActions.add(pianoAction);
                }
            }
        }

        // Merge PianoActions from both ArrayLists
        while (pressActions.size() != 0)
        {
            // Find the most recent pressAction
            PianoAction recentPressAction = null;
            for (PianoAction pianoAction : pressActions)
            {
                if (recentPressAction == null)
                {
                    recentPressAction = pianoAction;
                }
                else
                {
                    if (recentPressAction.tickStart > pianoAction.tickStart)
                    {
                        recentPressAction = pianoAction;
                    }
                }
            }

            // Remove it from the list if found
            if (recentPressAction != null)
            {
                pressActions.remove(recentPressAction);
            }

            // Find the most recent releaseAction that matches
            PianoAction recentReleaseAction = null;
            for (PianoAction pianoAction : releaseActions)
            {
                if (recentReleaseAction == null && pianoAction.midiValue == recentPressAction.midiValue)
                {
                    recentReleaseAction = pianoAction;
                }
                else if(recentReleaseAction != null && pianoAction.midiValue == recentPressAction.midiValue)
                {
                    if (recentReleaseAction.tickStart > pianoAction.tickStart)
                    {
                        recentReleaseAction = pianoAction;
                    }
                }
            }

            // Remove it from the list if found
            if (recentReleaseAction != null)
            {
                releaseActions.remove(recentReleaseAction);

                // Merge information into 1 piano action
                noteActions.add(new PianoAction(recentPressAction, recentReleaseAction));
            }
            else
            {
                // In the case where the midi doesn't specify when to end this note for whatever reason
                // Apply an arbitrary number
                recentPressAction.tickDuration = 8;
                noteActions.add(recentPressAction);
            }
        }

        this.noteActions = noteActions;
        return noteActions;
    }

    public PianoAction getNoteActionFromPianoAction(PianoAction pianoAction, long tickAcceptance)
    {
        if (this.noteActions.size() == 0)
        {
            System.out.println("MidiSequence.getNoteActions() hasn't been called before!");
            this.getNoteActions();
        }

        if (this.noteActions.size() != 0)
        {
            PianoAction mostRecentNote = null;
            for (PianoAction noteAction : this.noteActions)
            {
                if (noteAction.midiValue == pianoAction.midiValue && noteAction.tickStart + noteAction.tickDuration + tickAcceptance >= pianoAction.tickStart)
                {
                    if(mostRecentNote == null)
                    {
                        mostRecentNote = noteAction;
                    }
                    else
                    {
                        if (mostRecentNote.tickStart > noteAction.tickStart)
                        {
                            mostRecentNote = noteAction;
                        }
                    }
                }
            }
            return mostRecentNote;
        }
        else
        {
            System.out.println("This MidiSequence has no notes!");
            return null;
        }
    }

    public long getTickPerBar()
    {
        return (long) (this.PPQ * this.beatsPerBar);
    }

    @Override
    public void run()
    {
        if (!this.running)
        {
            this.running = true;
            try
            {
                long startDelay = -1024;
                for (currentTick = startDelay; currentTick <= duration; currentTick++)
                {
                    Wave.tickUpdate(currentTick);
                    ArrayList<PianoAction> pianoEvent = pianoEvents.get(currentTick);
                    ArrayList<TempoAction> tempoEvent = tempoEvents.get(currentTick);

                    if (pianoEvent != null)
                    {
                        for (PianoAction pianoAction : pianoEvent)
                        {
                            Wave.changeMidiKeyboardPressed(pianoAction);
                        }
                    }

                    if (tempoEvent != null)
                    {
                        for (TempoAction tempoAction : tempoEvent)
                        {
                            this.setTempo(tempoAction.midiValue);
                        }
                    }

                    DecimalFormat df = new DecimalFormat("##.######");
                    long ns = Long.valueOf(df.format(this.tickDuration).replace(".",""));
                    Thread.sleep(0);
                    // This allows us to wait for a much more accurate amount of time
                    // Very important when dealing with music
                    final long INTERVAL = ns;
                    long start = System.nanoTime();
                    long end = 0;
                    do
                    {
                        Thread.onSpinWait();
                        end = System.nanoTime();
                    }
                    while(start + INTERVAL >= end);

                }
            }
            catch (InterruptedException e)
            {
                System.out.println("MidiSequence was interrupted!");
            }
            this.running = false;
        }
    }
}
