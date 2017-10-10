/**
 * Created by Axel on 4/10/2017.
 * The goal of this project, UDP server and client, is that the server has to send
 * a message if he have received correct the pakkage of the client
 * TO DO: -threads
 *        -filefix
 *        -
 */

import javax.xml.crypto.Data;
import java.net.*;
import java.io.*;

public class UDPServer {

    public static void main(String args[]) throws Exception {

        String receiveFilePath = "C:\\Users\\fergan\\Desktop\\test.pdf";
        String reply = "Package received";

        int count = 0;

        byte[] receiveInfoBuffer = new byte[1024];
        byte[] receivePackageBuffer;
        byte[] receivePackageBuffer1;
        byte[] sendBuffer = new byte[1024];

        DatagramSocket socket = null;

        DatagramPacket receivePacket = null;
        DatagramPacket receivePacket1 = null;
        DatagramPacket receivePacket2 = null;

        DatagramPacket informationPacket = null;

        BufferedOutputStream bos = null;

        try {

            socket = new DatagramSocket(12345);
            bos = new BufferedOutputStream(new FileOutputStream(receiveFilePath));

            // PACKAGE 1: information
            //      incoming data can be put into a string. Remark: incoming data smaller than the buffersize will be filled with 0's. For this reason
            //      the string wil be trimmed befor converted to an integer
            informationPacket = new DatagramPacket(receiveInfoBuffer, receiveInfoBuffer.length);
            socket.receive(informationPacket);
            String temp = new String(informationPacket.getData());
            Integer fileSize = Integer.parseInt(temp.trim());
            int offset = 0;
            System.out.println("infoPackage received. Filesize: " + fileSize);
            int cnt = 0;

            while (fileSize > 0) {

                    receivePackageBuffer = new byte[64000];
                    receivePacket = new DatagramPacket(receivePackageBuffer,64000);
                    System.out.println("waiting..");
                    socket.receive(receivePacket);
                    System.out.println(cnt + ": received");
                    System.out.println("receivePacket: " + receivePacket.getLength());
                    System.out.println("filesize before: " + fileSize);
                    fileSize -= receivePacket.getLength();
                    System.out.println("filesize after: " + fileSize);
                    bos.write(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("written");
                    cnt++;
            }

            //PACKAGE 3: reply
/*            sendBuffer = reply.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
            socket.send(sendPacket);
            System.out.println("reply sent");*/

            System.out.println("BOS closing...");
            bos.close();




        } catch (SocketException e) {
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
