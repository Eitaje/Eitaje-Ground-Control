package utils;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Created by Eitan on 12/8/2016.
 */
public class MyDataListener implements SerialPortDataListener {

    SerialPort comPort;
    String currentString = "";
    boolean lineComplete = false;

    public MyDataListener(SerialPort comPort) {
        this.comPort = comPort;
    }
    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
    @Override
    public void serialEvent(SerialPortEvent event)
    {
        if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
            return;
        byte[] newData = new byte[comPort.bytesAvailable()];
        int numRead = comPort.readBytes(newData, newData.length);

        System.out.print(new String(newData));
                /*
        for(int i=0;i<numRead ;i++) {
            currentString += (char)newData[i];
            if((int)newData[i]==0 || (int)newData[i]==13)
                lineComplete = true;

            if(lineComplete) {
                System.out.print(new String(currentString));
                currentString = "";
            }

        }
        */
//        System.out.println("Read " + numRead + " bytes.");

    }

    public void setComPort(SerialPort comPort) {
        this.comPort = comPort;
    }
}
