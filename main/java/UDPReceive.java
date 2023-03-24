import java.net.DatagramPacket; // Imports 
import java.net.DatagramSocket; // Imports 

public class UDPReceive // Receieves messages
{
    public static void main(String args[]) 
    {
        try 
        {
            int port = 5005; // Use UD Protocol for listening
            DatagramSocket dsocket = new DatagramSocket(port);

            byte[] buffer = new byte[2048]; // Maximum size of message acceptable is 2048 Bytes
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) 
            {
                dsocket.receive(packet); // Searches for sent packets

                String msg = new String(buffer, 0, packet.getLength());      // Receieves the message sent
                System.out.println(packet.getAddress().getHostName() + ": " + msg);  // Prints out the message sent
                packet.setLength(buffer.length);
            }
        } 
        catch (Exception e) 
        {
            System.err.println(e); // Any error prints out error
        }
    }
}