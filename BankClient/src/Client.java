import operations.*;

import java.rmi.RemoteException;

/**
 * Created by Axel on 11/10/2017.
 */
public class Client implements BankOperations {

    int balance;
    public Client(int balance){
        this.balance = balance;
    }

    @Override
    public int checkBalance() throws RemoteException {
        return 0;
    }

    @Override
    public int withdraw(int value) throws RemoteException, BankExceptions {
        try{

            return balance;
        }catch(Exception e){
            e.getMessage();
        }

    }

    @Override
    public int deposit(int value) throws RemoteException, BankExceptions {
        return 0;
    }
}
