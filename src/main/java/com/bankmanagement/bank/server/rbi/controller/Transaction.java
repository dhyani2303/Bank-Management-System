package com.bankmanagement.bank.server.rbi.controller;

import com.bankmanagement.bank.server.common.Bank;
import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.common.models.CustomerDetails;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

public class Transaction
{
    Database database;
    int customerId;
    int amount;

   private Logger LOGGER ;

    public Transaction(Database database, Logger LOGGER)
    {
        this.database = database;

        this.LOGGER = LOGGER;

    }


    public void register(BufferedReader reader)

    {
        Gson gson = new Gson();
        try
        {
            String data = reader.readLine();

            CustomerDetails customerDetails = gson.fromJson(data, CustomerDetails.class);

            database.addCustomer(customerDetails);

            LOGGER.info("Customer with customer id "+ customerDetails.getAccountDetails().getCustomerId() + " is registered");

        } catch(IOException e)
        {
            LOGGER.warning(e.getMessage());
        }

    }

    public void deposit(BufferedReader reader)
    {
        try
        {
            String[] response = reader.readLine().split(" ");

            customerId = Integer.parseInt(response[0]);

            amount = Integer.parseInt(response[1]);

            database.getCustomer(customerId).getAccountDetails().depositAmount(amount);

            LOGGER.info("Customer with customer id "+ customerId + " deposited "+ amount);

        } catch(IOException e)
        {
            LOGGER.warning(e.getMessage());
        }


    }

    public void withdraw(BufferedReader reader)
    {
        try
        {
            String[] response = reader.readLine().split(" ");

             customerId = Integer.parseInt(response[0]);

             amount = Integer.parseInt(response[1]);

           database.getCustomer(customerId).getAccountDetails().withdrawAmount(amount);

       LOGGER.info("Customer with customer id "+ customerId + " withdrew "+ amount);

        } catch(IOException e)
        {
           LOGGER.warning(e.getMessage());
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

                database.getCustomer(customerId).getAccountDetails().transfer(customerIdOfPayee,amount,database);

                LOGGER.info("Customer with  customer id "+ customerId + " transferred " + amount + " to customer "+ customerIdOfPayee);


            } catch(IOException e)
            {
                LOGGER.warning(e.getMessage());
            }


        }
}
