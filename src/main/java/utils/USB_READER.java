package utils;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import javax.usb.*;
import javax.usb.event.UsbPipeDataEvent;
import javax.usb.event.UsbPipeErrorEvent;
import javax.usb.event.UsbPipeListener;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Eitan on 12/5/2016.
 */
public class USB_READER {
    /**
     * Arduino DUE VendorId = 1003, ProductId = 24868
     * Arduino DUE Virtual Port VendorId = 9025, ProductId = 62
     * Arduino Nano VendorId = 6790, ProductId = 29987
     * 3DS Transceiver = 4292, ProductId = -5536
     * @param args
     * @throws UsbException
     */
    static final short
//            ID_VENDOR_ARDUINO_DUE = 1003,
//            ID_PRODUCT_ARDUINO_DUE = 24868,
//            ID_VENDOR_ARDUINO_DUE_Virtual = 9025,
//            ID_PRODUCT_ARDUINO_DUE_Virtual = 62,
//            ID_VENDOR_ARDUINO_DUE_PROG = 9025,
//            ID_PRODUCT_ARDUINO_DUE_PROG = 61,
            ID_VENDOR_3DR_TRANSCEIVER = 4292,
            ID_PRODUCT_3DR_TRANSCEIVER = -5536,
            ID_VENDOR_ARDUINO_NANO = 6790,
            ID_PRODUCT_ARDUINO_NANO = 29987;

    public static void main(String args[]) throws UsbException{
        System.out.println("Starting USB program");

//        UsbServices services = UsbHostManager.getUsbServices();
//        printDevices(services.getRootUsbHub());

        SerialPort[] serialPorts = SerialPort.getCommPorts();
//        final SerialPort serialPort =  SerialPort.getCommPort("COM18");
//        final SerialPort serialPort =  SerialPort.getCommPort("COM4");
        final SerialPort serialPort =  SerialPort.getCommPort("COM38");
        serialPort.setBaudRate(9600);
//        serialPort.setBaudRate(115200);
//        serialPort.setBaudRate(9600);
//        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING,SerialPort.TIMEOUT_NONBLOCKING,SerialPort.TIMEOUT_NONBLOCKING);
        while(!serialPort.openPort())
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("Communication channel opened: " + serialPort.getSystemPortName() );
        serialPort.addDataListener(new MyDataListener(serialPort));


//*
        byte[] buffer = new byte[1024];
        int num=0;
        if(serialPort.openPort()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            String str1= "hello from computer\n";
            while(true) {
                byte[] buffer2 = str1.getBytes();
                serialPort.writeBytes(buffer2, buffer2.length);

                System.out.println("sent: " +str1);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            /*
            while (true) {
                if(serialPort.bytesAvailable()>0) {
                    num = serialPort.bytesAvailable();
                    serialPort.readBytes(buffer, Math.min(num, 1024));
                    String str = new String(buffer);
                    System.out.print(str);
                }
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException ex) {
//                    Thread.currentThread().interrupt();
//                }
            }
            */
        }
//*/
        Scanner kb = new Scanner(System.in);
        int n=0;
        while(n==0)
            n = kb.nextInt();

        System.out.println("Finished");

    }



    /**
     * Get the required USB device
     * @param hub
     * @param idVendor
     * @param idProduct
     * @return
     */
    public static  UsbDevice getDevice(UsbHub hub,short idVendor, short idProduct)
    {
        for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices())
        {
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            if(idVendor == desc.idVendor() && idProduct == desc.idProduct())
                return device;
            if (device.isUsbHub())
            {
                device = getDevice((UsbHub) device,idVendor,idProduct);
                if (device != null) return device;
            }
        }
        return null;
    }

    /**
     * Print all the available USB devices
     * @param hub
     * @return
     */
    public static UsbDevice printDevices(UsbHub hub)
    {
        for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices())
        {
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            System.out.println("VendorId = " + desc.idVendor() + ", ProductId = " + desc.idProduct());
            if (device.isUsbHub())
            {
                device = printDevices((UsbHub) device);
                if (device != null) return device;
            }
        }
        return null;
    }
}
