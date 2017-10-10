import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

/**
 * Created by Axel on 6/10/2017.
 * Client has to send a file and receive a reply
 */
public class UDPClient {

    public static void main (String args[]) throws Exception {

        String sendFilePath = "C:\\Users\\fergan\\Desktop\\chapter 15.pdf";


        byte[] filePackageBuffer = null;
        byte[] infoPackageBuffer = null;
        byte[] replyBuffer = new byte[1024];

        int count = 0;
        int PORTNUMBER = 12345;
        int bytesRead = 0;
        InetAddress HOSTNAME = null;

        BufferedInputStream bis = null;

        DatagramSocket socket = null;

        DatagramPacket informationPacket = null;
        DatagramPacket filePacket = null;
        DatagramPacket receivePacket = null;

        try {
            // Get hostname
            HOSTNAME = InetAddress.getLocalHost();

            // Open a socket. Computer chooses port
            socket = new DatagramSocket();

            // Filestream
            File f = new File(sendFilePath);
            bis = new BufferedInputStream(new FileInputStream(f));
            System.out.println("filestream open");
            filePackageBuffer = new byte[64000];
            // PACKAGE 1: information
            //              Sending the filesize so the server knows how many packets he can expect
            infoPackageBuffer = ((Integer)bis.available()).toString().getBytes();
            informationPacket = new DatagramPacket(infoPackageBuffer,infoPackageBuffer.length,HOSTNAME,PORTNUMBER);
            socket.send(informationPacket);

            // PACKAGE 2: file
            //              As long there are bytes in the buffer of bis, we kan send packets with size 65536-1-64 (max place in datagram)
            while((bytesRead=(bis.read(filePackageBuffer,0,filePackageBuffer.length))) !=-1 ){
                System.out.println("A packet has been send");
                System.out.println("bytes : "+bytesRead);
                socket.send(new DatagramPacket(filePackageBuffer,bytesRead,HOSTNAME,PORTNUMBER));

            }

            System.out.println("Count: " + count);
/*
            // PACKAGE 3: reply
            receivePacket = new DatagramPacket(replyBuffer, replyBuffer.length);
            socket.receive(receivePacket);
            System.out.println("Reply: " + new String(receivePacket.getData()));

*/
            System.out.println("BIS closing...");
            bis.close();

        }catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (socket != null) {
                System.out.println("Socket closing...");
                socket.close();

            }
        }
    }
}

