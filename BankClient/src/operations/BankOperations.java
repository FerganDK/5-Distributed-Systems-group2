package operations;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Axel on 11/10/2017.
 */
public interface BankOperations extends Remote {
    public int checkBalance () throws RemoteException;

    public int withdraw (int value) throws RemoteException,BankExceptions;

    public int deposit (int value) throws RemoteException,BankExceptions;


}
