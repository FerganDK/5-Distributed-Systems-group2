/**
 * Created by Axel on 2/10/2017.
 * Bronnen: http://www.rgagnon.com/javadetails/java-0542.html
 *          http://www.java2s.com/Code/Java/Network-Protocol/TransferafileviaSocket.htm
 *          https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
 *          https://systembash.com/a-simple-java-tcp-server-and-tcp-client/
 */

import java.io.*;
import java.net.*;

public class Client {

    public static void main (String [] args ) throws IOException {
        int port = 13250;
        int fileSize;
        int count;
        String server = "127.0.0.1";  // localhost
        String receivedFile = "receivedFile.pdf";
        FileOutputStream fos = null;

        Socket socket = null;
        try {
            //connect to server
            socket = new Socket(server, port);
            System.out.println("Connecting...");
            //read first package, which gives the size of the file
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            fileSize = dis.readInt();
            //create inputstream
            InputStream is = socket.getInputStream();
            //byteArray where file will be written to
            byte[] buffer = new byte[fileSize];

            fos = new FileOutputStream(receivedFile);
            InputStream in = socket.getInputStream();
            while((count=in.read(buffer)) >0){
                fos.write(buffer,0,count);
            }
            System.out.println(((double)fileSize)/1000000 + " from total of " + ((double)buffer.length)/1000000 +  " Mb received, " + (((double)fileSize)/((double)buffer.length)) * 100 + "% succesfull");
            fos.close();
        }
        finally {
            fos.close();
            socket.close();
        }
    }
}
