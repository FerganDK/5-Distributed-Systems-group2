import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Axel on 6/10/2017.
 * Client has to send a file and receive a reply
 */
public class UDPClient {

    public static void main (String args[]) throws Exception {

        String sendFilePath = "fileToSend.pdf";

        int datagramSize = 64000;

        byte[] filePackageBuffer = new byte[datagramSize];
        byte[] filePackageBuffer2;
        byte[] infoPackageBuffer;
        byte[] infoPackageBuffer2;
        byte[] replyBuffer = new byte[1024];

        int count = 0;
        int PORTNUMBER = 12345;

        InetAddress HOSTNAME;

        BufferedInputStream bis;

        DatagramSocket socket = null;

        DatagramPacket informationPacket;
        DatagramPacket informationPacket2;
        DatagramPacket filePacket;
        DatagramPacket receivePacket;

        try {

            // Get hostname
            HOSTNAME = InetAddress.getLocalHost();

            // Open a socket. Computer chooses port
            socket = new DatagramSocket();

            // Filestream
            File f = new File(sendFilePath);
            bis = new BufferedInputStream(new FileInputStream(f));

            // PACKAGE 1: information
            //              Sending the filesize and filename so the server knows how many packets he can expect
            infoPackageBuffer = ((Integer)bis.available()).toString().getBytes();
            informationPacket = new DatagramPacket(infoPackageBuffer,infoPackageBuffer.length,HOSTNAME,PORTNUMBER);
            socket.send(informationPacket);
            infoPackageBuffer2 = sendFilePath.getBytes();
            informationPacket2 = new DatagramPacket(infoPackageBuffer2, infoPackageBuffer2.length, HOSTNAME, PORTNUMBER);
            socket.send(informationPacket2);

            // PACKAGE 2: file
            //              As long there are bytes in the buffer of bis, we can send packages with size 65536-1-64 (max place in datagram)
            //              For safety reasons we take 64000
            while(bis.available() > 0){

                if (bis.available() < datagramSize) {
                    filePackageBuffer2 = new byte[bis.available()];
                    bis.read(filePackageBuffer2, 0, bis.available());
                    filePacket = new DatagramPacket(filePackageBuffer2, filePackageBuffer2.length, HOSTNAME, PORTNUMBER);
                    socket.send(filePacket);
                    count++;
                    System.out.println("Package " + count + " sending.." + filePackageBuffer2.length + "byte");
                }else {
                    bis.read(filePackageBuffer, 0, datagramSize);
                    filePacket = new DatagramPacket(filePackageBuffer, filePackageBuffer.length, HOSTNAME, PORTNUMBER);
                    socket.send(filePacket);
                    count++;
                    System.out.println("Package " + count + " sending.." + filePackageBuffer.length + "byte");
                }
                TimeUnit.MILLISECONDS.sleep(100);

            }

            System.out.println(count + " packages sent");

            // PACKAGE 3: reply
            receivePacket = new DatagramPacket(replyBuffer, replyBuffer.length);
            socket.receive(receivePacket);
            System.out.println("Reply from server: " + new String(receivePacket.getData()));

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

