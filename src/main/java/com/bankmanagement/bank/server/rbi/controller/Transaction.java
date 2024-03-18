package com.bankmanagement.bank.server.rbi.controller;

import com.bankmanagement.bank.server.common.Bank;
import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.common.models.CustomerDetails;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;

public class Transaction
{
    Database database;
    int customerId;
    int amount;

    public Transaction(Database database)
    {
        this.database = database;

    }


    public void register(BufferedReader reader)

    {
        Gson gson = new Gson();
        try
        {
            String data = reader.readLine();

            CustomerDetails customerDetails = gson.fromJson(data, CustomerDetails.class);

            database.addCustomer(customerDetails);

            double balance = database.getCustomer(customerId).getAccountDetails().getBalance();

            System.out.println("balance = " + balance);



        } catch(IOException e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void deposit(BufferedReader reader)
    {
        try
        {
            String[] response = reader.readLine().split(" ");

            customerId = Integer.parseInt(response[0]);

            amount = Integer.parseInt(response[1]);

            double balance = database.getCustomer(customerId).getAccountDetails().depositAmount(amount);

            System.out.println("Updated user with customer id " + customerId + " Balance is " + balance);

        } catch(IOException e)
        {
            System.out.println(e.getMessage());
        }


    }

    public void withdraw(BufferedReader reader)
    {
        try
        {
            String[] response = reader.readLine().split(" ");

             customerId = Integer.parseInt(response[0]);

             amount = Integer.parseInt(response[1]);

            double balance = database.getCustomer(customerId).getAccountDetails().withdrawAmount(amount);

            System.out.println("Updated user with customer id " + customerId + " Balance is " + balance);

        } catch(IOException e)
        {
            System.out.println(e.getMessage());
        }


    }

        public void transfer(BufferedReader reader)
        {
            try
            {

                String[] response = reader.readLine().split(" ");

                customerId = Integer.parseInt(response[0]);

                 amount  = Integer.parseInt(response[1]);

                int customerIdOfPayee = Integer.parseInt(response[2]);

                double balance = database.getCustomer(customerId).getAccountDetails().transfer(customerIdOfPayee,amount,database);

                System.out.println("Updated user with customer id "+ customerId + " Balance is "+ balance);

                System.out.println("Updated balance of payee is "+ database.getCustomer(customerIdOfPayee).getAccountDetails().getBalance());

            } catch(IOException e)
            {
                System.out.println(e.getMessage());
            }


        }
}
