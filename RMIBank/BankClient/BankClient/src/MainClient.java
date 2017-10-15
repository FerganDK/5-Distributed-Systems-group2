/**
 * Created by Axel on 14/10/2017.
 */

import operations.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClient {
    public static void main(String args[]) throws Exception {

        try {
            // Get the registry
            Registry registry = LocateRegistry.getRegistry(null);

            // Look for object in registry
            BankOperations stub = (BankOperations) registry.lookup("server");

            // Calling the methodes of the remote object
            Client client = new Client(0,stub);
            while (client.run()) {
                System.exit(0);
            }



        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }


}
