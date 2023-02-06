import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReceive {
    public static void main(String args[]) {
        try {
            int port = 5005;

            DatagramSocket dsocket = new DatagramSocket(port);

            byte[] buffer = new byte[2048];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                dsocket.receive(packet);

                String msg = new String(buffer, 0, packet.getLength());
                System.out.println(packet.getAddress().getHostName() + ": "
                        + msg);

                packet.setLength(buffer.length);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}