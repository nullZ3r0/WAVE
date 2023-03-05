import javax.sound.midi.*;

// Imported class, look into it and learn
public class MidiPlayer implements Runnable
{
    MidiPlayer()
    {

    }

    @Override
    public void run()
    {
        try{
            /* Create a new Sythesizer and open it. Most of
             * the methods you will want to use to expand on this
             * example can be found in the Java documentation here:
             * https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Synthesizer.html
             */
            Synthesizer midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();

            //get and load default instrument and channel lists
            Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
            MidiChannel[] mChannels = midiSynth.getChannels();

            midiSynth.loadInstrument(instr[0]);//load an instrument


            mChannels[0].noteOn(60, 100);//On channel 0, play note number 60 with velocity 100
            try { Thread.sleep(1000); // wait time in milliseconds to control duration
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
            mChannels[0].noteOff(60);//turn of the note


        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
