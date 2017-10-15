import java.io.*;
import java.net.Socket;

public class Connection extends Thread {
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private String filePath;
    private File myFile;
    private byte[] myByteArray;
    private int maxSize = 64000;
    private int size;
    private int offset;

    public Connection(Socket aClientSocket) {
        try {
            myFile = new File("fileToSend.pdf");
            clientSocket = aClientSocket;
            myByteArray = new byte[(int) myFile.length()];
            out = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            dos.writeInt((int)myFile.length());
            byte[] file = readFile(myFile);
            size = (int)myFile.length();
            while(size > 0){
                if(size >= maxSize){
                    out.write(file,offset,maxSize);
                    offset+=maxSize;
                    size-=maxSize;
                }
                else{
                    out.write(file,offset,size);
                    offset+=size;
                    size-=size;
                }
            }
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        }finally{
            try{
                out.flush(); //flushes the buffer
                clientSocket.close();
            }catch(IOException e){
                System.out.println("close: "+e.getMessage());
            }

        }
    }
    public byte[] readFile(File file){


        try {
            //reads the file into a byteArray
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
            reader.read(myByteArray,0,myByteArray.length);
            reader.close();
        }catch(FileNotFoundException e){
            System.out.println("The file has not been found: "+e.getMessage());
        }catch(IOException e){
            System.out.println("problem with reading the file: "+e.getMessage());
        }
        return myByteArray;
    }
}
