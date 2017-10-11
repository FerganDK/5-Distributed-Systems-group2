import operations.*;

import java.rmi.RemoteException;

/**
 * Created by Axel on 11/10/2017.
 */
public class Server implements BankOperations {

    int balance = 0;

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
                throw new BankExceptions("No sufficient funds, you are " + (balance-value) + " short!");
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
