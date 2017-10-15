import operations.*;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Created by Axel on 11/10/2017.
 */
public class Client {

    int balance;
    BankOperations stub;
    Scanner input;
    Boolean exit;

    public Client(int balance, BankOperations stub){
        this.balance = balance;
        this.stub = stub;
        this.input = new Scanner(System.in);
        this.exit = false;
    }

    public boolean run () throws RemoteException , BankExceptions {
        System.out.println("Welcome to your bankaccount!");
        System.out.println("------------------------------");
        System.out.println("To retrieve your balance, press 1");
        System.out.println("To make a deposit, press 2");
        System.out.println("To make a withdrawal, press 3");
        System.out.println("To exit, press 0");

        while (!exit) {
            try {
                int number = input.nextInt();

                switch (number) {
                    case 0: {
                        exit = true;
                        break;
                    }
                    case 1: {
                        try {
                            System.out.println("Your balance: " + stub.checkBalance() + " euro");
                            break;
                        }catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    case 2: {
                        try {
                            System.out.println("Enter the amount for the deposit: ");
                            int amount = input.nextInt();
                            System.out.println("Your new balance: " + stub.deposit(amount) + " euro");
                            break;
                        }catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 3: {
                        try {
                            System.out.println("Enter the amount for the withdrawal: ");
                            int amount = input.nextInt();
                            System.out.println("Your new balance: " + stub.withdraw(amount) + " euro");
                            break;
                        }catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    default: {
                        System.out.println("Invalid number!");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Goodbye!");
        return true;
    }

}
