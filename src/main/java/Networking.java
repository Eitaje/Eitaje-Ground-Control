import java.io.*;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by Eitan on 7/17/2018.
 */
public class Networking {


    InetAddress serverAddress = null;
    OutputStream outputS = null;
    InputStream inputS = null;
    BufferedReader inS = null;
    Socket socket = null;
    public Networking(InetAddress serverAddress) {
        this.serverAddress = serverAddress;
        setupStreams();
    }

    private void setupStreams()
    {

        try {
            if(socket ==null)
                socket = new Socket(serverAddress, 5776);

            outputS = socket.getOutputStream();
            inputS = socket.getInputStream();

            inS =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
        * Return the server's address
        * */
    public InetAddress getServerIP ()
    {

        String ip;

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            int position =0;
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                //This filters out the interfaces
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    position++;

                    if (position > -1 ) {
                        System.out.println(iface.getDisplayName() + " " + ip);
                    }

                    else{
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }





        InetAddress[] addresses = new InetAddress[0];
        try {
            addresses = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());

            byte[] local_address_bytes = null;
            for(InetAddress address:addresses)
                if(address.getAddress()[0] == (byte)192 && address.getAddress()[1] == (byte)168)
                    local_address_bytes = address.getAddress();

            if(local_address_bytes==null)
            {
                System.out.println("Error: didnt find server. Exiting");
                System.exit(-1);
            }

            local_address_bytes[3] = 1;
            return InetAddress.getByAddress(local_address_bytes);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getServerStatus() {
        String msg = "get_status\n";
        try {
            //sending message to server
            outputS.write(msg.getBytes());
            outputS.flush();

            String userInput;

            //reading server's replay
            while ((userInput = inS.readLine()) != null) {
                return  userInput;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "?";
    }

    public void moveTo(int pan, int tilt) {
        String msg1 = "/pan " + pan + " " + tilt + "*";


        try {
            //sending message to server
            outputS.write(msg1.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
//        return "?";
    }

    public void panRelative(int pan, int tilt) {
        String msg1 = "/rlt " + pan + " " + tilt + "*";
        try {
            //sending message to server
            outputS.write(msg1.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
//        return "?";
    }
}
