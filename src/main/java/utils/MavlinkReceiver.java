package utils;

import com.sun.xml.internal.ws.api.message.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by Eitan on 9/14/2018.
 */
public class MavlinkReceiver {

    public static void main (String[] args) {


        Runnable server = new Server();
        server.run();


    }

    static class Server implements Runnable
    {

        public void run()
        {
            try
            {
                //get the multicast ip
                InetAddress ip = InetAddress.getByName("127.0.0.1");

                int portNumber=5762;

                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String inputLine, outputLine;

                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }

            }
            catch (Exception ex) {
                Thread t = Thread.currentThread();
                t.getUncaughtExceptionHandler().uncaughtException(t, ex);
            }

        }
    }

}
