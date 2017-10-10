/**
 * Created by Axel on 4/10/2017.
 * The goal of this project, UDP server and client, is that the server has to send
 * a message if he have received correct the pakkage of the client
 */

import java.net.*;
import java.io.*;

public class UDPServer {

    public static void main(String args[]) throws Exception {

        String receiveFilePath = "received_";
        String reply = "Package received";

        int cnt = 1;
        int datagramSize = 64000;

        byte[] receiveInfoBuffer = new byte[1024];
        byte[] receivePackageBuffer = new byte[datagramSize];
        byte[] receivePackageBuffer1;
        byte[] sendBuffer = new byte[1024];

        DatagramSocket socket = null;

        DatagramPacket receivePacket = null;
        DatagramPacket informationPacket;

        BufferedOutputStream bos;

        try {

            socket = new DatagramSocket(12345);

            // PACKAGE 1: information
            //      information of incoming file (size and filename). Remark: incoming data smaller than the buffersize will be filled with 0's. For this reason
            //      the string wil be trimmed before converted to an integer
            informationPacket = new DatagramPacket(receiveInfoBuffer, receiveInfoBuffer.length);
            socket.receive(informationPacket);
            String temp = new String(informationPacket.getData());
            Integer fileSize = Integer.parseInt(temp.trim());
            informationPacket = new DatagramPacket(receiveInfoBuffer,receiveInfoBuffer.length);
            socket.receive(informationPacket);
            receiveFilePath = receiveFilePath + new String(informationPacket.getData(),0,informationPacket.getLength());
            System.out.println("InfoPackage received. File: " + receiveFilePath + " with size: " + fileSize);

            bos = new BufferedOutputStream(new FileOutputStream(receiveFilePath));

            while (fileSize > 0) {
                if (fileSize < datagramSize){
                    receivePackageBuffer1 = new byte[fileSize];
                    receivePacket = new DatagramPacket(receivePackageBuffer1, fileSize);
                    socket.receive(receivePacket);
                    fileSize = fileSize - receivePacket.getLength();
                    bos.write(receivePacket.getData(),0, receivePacket.getLength());
                    System.out.println(cnt + " package received");
                    cnt++;
                } else {
                    receivePacket = new DatagramPacket(receivePackageBuffer,datagramSize);
                    socket.receive(receivePacket);
                    fileSize -= receivePacket.getLength();
                    bos.write(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println(cnt + " package received");
                    cnt++;
                }

            }

            //PACKAGE 3: reply
            sendBuffer = reply.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
            socket.send(sendPacket);
            System.out.println("Package received. Reply sent");

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
