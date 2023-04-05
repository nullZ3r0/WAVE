import java.io.IOException;
import java.net.DatagramPacket; // Imports
import java.net.DatagramSocket; // Imports
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

public class UDPReceiver implements Runnable // Receieves messages
{
    int port = 0;
    byte[] buffer = new byte[2048];
    private final AtomicBoolean running = new AtomicBoolean(false);
    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;
    Thread thread;

    UDPReceiver(int port)
    {
        this.port = port;
        try
        {
            this.datagramSocket = new DatagramSocket(this.port);
            this.datagramPacket = new DatagramPacket(this.buffer, this.buffer.length);
        }
        catch
        (SocketException e)
        {
            System.out.println("[!] Failed to initialise UDPReceiver");
        }
    }

    public String search()
    {
        try
        {
            this.datagramSocket.receive(this.datagramPacket); // Searches for sent packets
            String msg = new String(this.buffer, 0, this.datagramPacket.getLength()); // Receieves the message sent
            System.out.println(this.datagramPacket.getAddress().getHostName() + ": " + msg);  // Prints out the message sent
            this.datagramPacket.setLength(buffer.length);
            return msg;
        }
        catch (IOException e)
        {
            System.out.println("[!] Failed to search for sent packets");
        }

        return "";
    }

    public void start()
    {
        this.thread = new Thread(this);
        this.thread.start();
    }
    public void stop()
    {
        running.set(false);
    }
    @Override
    public void run()
    {
        this.running.set(true);
        while (running.get())
        {
            try
            {
                this.search();
                Thread.sleep(3);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}