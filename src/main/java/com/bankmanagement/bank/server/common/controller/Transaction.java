package com.bankmanagement.bank.server.common.controller;

import com.bankmanagement.bank.server.common.Bank;
import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.common.util.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Transaction
{
    Database database = null;

    String bankCode = null;

    public Transaction(Database database,String bankCode)
    {
        this.database = database;

        this.bankCode = bankCode;

    }

    public void getChoice(BufferedReader clientReader, int customerId, PrintWriter clientWriter,PrintWriter rbiWriter)
    {
        try
        {
            while(true)
            {

                String operationCode = clientReader.readLine();


                switch(operationCode)
                {
                    case "1":
                    {
                        deposit(clientReader, customerId, clientWriter,rbiWriter);

                        break;
                    }
                    case "2":
                    {
                        withdraw(clientReader, customerId, clientWriter,rbiWriter);

                        break;

                    }
                    case "3":
                    {
                        transfer(clientReader, customerId, clientWriter,rbiWriter);

                        break;
                    }
                    case "4":
                    {
                        showBalance(customerId, clientWriter);

                        break;
                    }
                    case "5":
                    {
                        handleLogout(customerId, clientWriter);

                        return;
                    }


                }
            }
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public void deposit(BufferedReader clientReader, int customerId, PrintWriter clientWriter,PrintWriter rbiWriter)
    {
        try
        {
            int amountToDeposit = Integer.parseInt(clientReader.readLine());

            if(Validation.isValidAmount(amountToDeposit))
            {
                double updatedBalance = database.getCustomer(customerId).getAccountDetails().depositAmount(amountToDeposit);

                clientWriter.println("Amount Deposition Successful! Your updated balance is " + updatedBalance);

                rbiWriter.flush();

                rbiWriter.println(bankCode + "2");

                rbiWriter.println(customerId +" "+ amountToDeposit);

            }
            else
            {
                clientWriter.println("Amount to be deposited cannot be negative or zero");


            }


        } catch(NumberFormatException e)
        {
            System.out.println("Enter number only");

            deposit(clientReader,customerId,clientWriter,rbiWriter);


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public void withdraw(BufferedReader clientReader, int customerId, PrintWriter clientWriter,PrintWriter rbiWriter)
    {
        try
        {
            int amountToWithdraw = Integer.parseInt(clientReader.readLine());

            if(Validation.isValidAmount(amountToWithdraw) && Validation.hasSufficientBalance(amountToWithdraw, customerId, database))
            {
                clientWriter.flush();

                double balance = database.getCustomer(customerId).getAccountDetails().withdrawAmount(amountToWithdraw);


                clientWriter.println(amountToWithdraw + " has been withdrawn from your account. Your updated balance is " + balance);

                rbiWriter.println(bankCode + "3");

                rbiWriter.println(customerId +" "+ amountToWithdraw);

            }
            else
            {
                clientWriter.println("Either you entered inappropriate amount or you do not have sufficient balance");

            }

        }catch(NumberFormatException e)
        {
            System.out.println("Enter number only");

            withdraw(clientReader,customerId,clientWriter,rbiWriter);


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public void transfer(BufferedReader clientReader, int customerId, PrintWriter clientWriter,PrintWriter rbiWriter)
    {
        try
        {
            String accountNoOfPayee =clientReader.readLine();

            if(Validation.PayeeExist(accountNoOfPayee, database) != 0)
            {
                int payeeCustomerId = Validation.PayeeExist(accountNoOfPayee, database);

                clientWriter.println(true);

                int amount = Integer.parseInt(clientReader.readLine());

                if(Validation.isValidAmount(amount) && Validation.hasSufficientBalance(amount, customerId, database))
                {
                    double balance = database.getCustomer(customerId).getAccountDetails().transfer(payeeCustomerId, amount, database);

                    clientWriter.println("Transfer done successfully! Your remaining balance is " + balance);

                    rbiWriter.println(bankCode + "4");

                    rbiWriter.println(customerId+ " " +amount+ " " + payeeCustomerId);



                }
                else
                {
                    clientWriter.println("Either you entered inappropriate amount or you do not have sufficient balance");


                }


            }
            else
            {
                clientWriter.println("Account Number is invalid");
            }


        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showBalance(int customerId, PrintWriter clientWriter)
    {
        double balance = database.getCustomer(customerId).getAccountDetails().getBalance();

        clientWriter.println("Your Balance is " + balance);

    }

    public void handleLogout(int customerId, PrintWriter clientWriter)
    {
        clientWriter.println("Logging out");

        System.out.println("Customer with customer Id " + customerId + " logged out");

    }

}


