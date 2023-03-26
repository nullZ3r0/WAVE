import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/*
Basic system
Keybinds will be used to create new WaveActions(String command)
Keybinds should probably purge other instances where different KeyStrokes are set to the same command
this will stop two differ keystrokes from making the same command

 */


public class Keybind
{
    public class KeybindAction extends AbstractAction
    {
        private String actionName;

        KeybindAction(String actionName)
        {
            this.actionName = actionName;
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            WaveAPI.debugAction(this.actionName);
        }
    }
    public AppFrame swingConnector;

    Keybind()
    {
        swingConnector = new AppFrame();
        swingConnector.setLayout(null);
        swingConnector.setVisible(true);
        swingConnector.setPreferredSize(new Dimension(0,0));
        swingConnector.transform.setSize(0,0,0,0);
    }

    public Boolean setKeybinding(String keySyntax, String actionName)
    {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keySyntax);
        if (keyStroke != null)
        {
            setKeybinding(keyStroke, actionName);
            return true;
        }
        else
        {
            System.out.println("["+keySyntax+"] is not a valid KeyStroke syntax!");
        }
        return false;
    }

    public Boolean setKeybinding(KeyStroke keyStroke, String actionName)
    {
        // Check if the keyName is valid
        if (keyStroke != null)
        {
            System.out.println("Binding ["+keyStroke.toString()+"] to ["+actionName+"]");
            this.swingConnector.getInputMap().put(keyStroke, actionName);

            Action keybindAction = this.swingConnector.getActionMap().get(actionName);
            if (keybindAction == null)
            {
                System.out.println("["+actionName+"] doesn't exists, creating...");
                this.swingConnector.getActionMap().put(actionName, new KeybindAction(actionName));
            }
            return true;
        }

        return false; // Failed to set keybinding
    }

}
