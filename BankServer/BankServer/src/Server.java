import operations.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Axel on 11/10/2017.
 */
public class Server extends UnicastRemoteObject implements BankOperations {

    int balance;

    public Server() throws RemoteException{
        balance = 0;
    }

    public Server(int value) throws RemoteException {
        balance = value;
    }

    @Override
    public synchronized int checkBalance() throws RemoteException {
        return balance;
    }

    @Override
    public synchronized int withdraw(int value) throws RemoteException,BankExceptions {
        if (value >= 0) {
            if (balance >= value) {
                balance -= value;
                return balance;
            } else{
                throw new BankExceptions("No sufficient funds, you are " + (value-balance) + " short!");
            }
        }else{
            throw new BankExceptions("Amount to withdraw is less then 0!");
        }

    }

    @Override
    public synchronized int deposit(int value) throws RemoteException,BankExceptions {
        if (value >= 0) {
            balance += value;
            return balance;
        }else{
            throw new BankExceptions("Amount to deposit is less then 0!");
        }
    }
}
