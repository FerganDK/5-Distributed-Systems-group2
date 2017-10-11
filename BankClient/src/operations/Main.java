package operations;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Looking up the registry for the remote object
            BankOperations stub = (BankOperations) registry.lookup("Hello");

            // Calling the remote method using the obtained object
            System.out.println(stub.deposit(5));
            System.out.println(stub.checkBalance());

            // System.out.println("Remote method invoked");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }


    }
}
