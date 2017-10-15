import operations.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/* https://www.tutorialspoint.com/java_rmi/java_rmi_application.htm
 * https://docs.oracle.com/javase/tutorial/rmi/implementing.html
 */
public class MainServer{

    public static void main(String args[])throws Exception{

        boolean exit;
        Scanner input;

        try {

            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(new String ("start rmiregistry"));
            // Instantiating the Server class (implementationclass of the interface
            Remote server =new Server();

            // Export the object to the stub
            // BankOperations stub = (BankOperations) UnicastRemoteObject.exportObject(server, 0);
            // We don'nt need this because class server extends the unicastRemoteObject.
            // From the moment an object of this class is made, is is automatically a stub

            // Binding the stub (remote object) with the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("server", server);

            System.out.println("Server is ready");

            input = new Scanner(System.in);
            exit = false;

            System.out.println("Press 0 to stop server");

            while (!exit) {
                int number = input.nextInt();
                if (number == 0)
                    exit= true;
            }

            System.exit(0);

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}