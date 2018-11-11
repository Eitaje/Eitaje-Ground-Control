
import java.io.*;
import java.net.*;

/**
 * Created by Eitan on 7/16/2018.
 */
public class GCMain {



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        // TODO code application logic here

//        String tosend = "Eitaje!\n";
        String tosend = "Hello from JAVA!";
        String tosend1 = "This is:  DatagramSocket datagramSocket = new DatagramSocket();\n" +
                "        datagramSocket.send(packet);\n" +
                "        System.out.println(InetAddress.getLocalHost().getHostAddress()); ";

//        byte [] IP={(byte)192,(byte)168,4,1};
        byte [] IP={(byte)192,(byte)168,4,1};
        InetAddress serverAddress = InetAddress.getByAddress(IP);

        System.out.println("Starting comminucation with server: " + serverAddress );
        Networking tracker = new Networking(serverAddress);

        for(int i=0;i<1;i++) {
//            System.out.println("Asking server for status. hold on");
//            String status_srt = tracker.getServerStatus();
//            System.out.println("Server status: " + status_srt);
//            tracker.moveTo(0,0);
//            tracker.moveTo(30,20);
//            tracker.panRelative(0,-90);
//            tracker.panRelative(0,-10);
            tracker.moveToNorth();
//            tracker.panRelative(360,0);
        }


        /*
        for(int i=0;i<10;i++) {


            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */

/*

        DatagramPacket packet = new DatagramPacket(
                buffer, buffer.length, address, 5775
        );
        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.send(packet);
        System.out.println(InetAddress.getLocalHost().getHostAddress());

/*/

        PacketListener packetListener = new PacketListener();
        packetListener.run();
        /*
        PrintWriter writer = new PrintWriter(outputs, true);
        for(int i=0;i<10000;i++) {
            try {
                Thread.currentThread().sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writer.println(i + "\t" + tosend);
            System.out.println(i + "\t" + tosend);
        }
        */

//*/
    }

}

class PacketListener extends Thread

{

    @Override
    public synchronized void start() {

    }

    @Override
    public void run() {
        System.out.println("PacketListener started");
    }
}
