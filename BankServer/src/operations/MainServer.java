package operations;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer extends Server {

    public MainServer() throws RemoteException {
    }

    public static void main(String[] args) throws RemoteException {

        try {
            Server server = new Server();

            BankOperations stub = (BankOperations) UnicastRemoteObject.exportObject(server, 5099);
            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.getRegistry();

            registry.bind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
