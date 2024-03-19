package com.bankmanagement.bank.server.common.controller;


import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.common.util.Logging;
import com.bankmanagement.bank.server.common.util.Validation;
import com.bankmanagement.bank.server.common.models.CustomerDetails;
import com.google.gson.Gson;

import java.io.*;
import java.util.logging.Logger;


public class Authentication implements Serializable
{
    Database database = null;

 //   private Logger LOGGER;

    protected static  final Logger LOGGER = Logging.getBankServerLogger();

    String bankCode;


    public Authentication(Logger LOGGER, Database database, String bankCode)
    {
        this.database = database;

        this.bankCode = bankCode;

     //   this.LOGGER = LOGGER;
    }


    final String NEXT_LINE = "\n";

    public void register(BufferedReader clientReader, PrintWriter authWriter, PrintWriter rbiWriter,PrintWriter clientWriter)
    {

        try
        {

            authWriter.println("1");
//            String[] data = clienteReader.readLine().split(" ");
//
//            String firstName = data[0];
//
//            String lastName = data[1];

            String firstName = clientReader.readLine();

            String lastName = clientReader.readLine();

            System.out.println(firstName + " " + lastName);

          //  LOGGER.info(firstName + " " + lastName);

            CustomerDetails customerDetail = new CustomerDetails(firstName, lastName);

            int customerId = customerDetail.getAccountDetails().getCustomerId();

            database.addCustomer(customerDetail);

           clientWriter.println("Registration successful " + ":" + "Account Number is " + customerDetail.getAccountDetails().getAccountNo() + ":"+ "The customer number is " + customerDetail.getAccountDetails().getCustomerId() + ":" + "The password is " + customerDetail.getAccountDetails().getPassword());

            LOGGER.info("Registered client with account number " + customerDetail.getAccountDetails().getAccountNo() + " and customer id " + customerId + " password is "+ customerDetail.getAccountDetails().getPassword());

            authWriter.println(customerId + " " + customerDetail.getAccountDetails().getPassword());

            rbiWriter.println(bankCode + "1");

            Gson gson = new Gson();

            String jsonCustomerDetails = gson.toJson(customerDetail);

            rbiWriter.println(jsonCustomerDetails);


        } catch(IOException e)
        {
            LOGGER.warning(e.getMessage());
        }
    }


    public void login(BufferedReader clientReader,PrintWriter clientWriter, PrintWriter authWriter,BufferedReader authReader,PrintWriter rbiWriter)
    {
        String customerId = null;

        try
        {
           String[] data = clientReader.readLine().split(" ");

           customerId = data[0];

           String password = data[1];

            if(Validation.CustomerExists(Integer.parseInt(customerId), database))
            {
                authWriter.println("2");

                authWriter.println(customerId + " " + password);


                String verificationResponse = authReader.readLine();


                if(verificationResponse!=null)
                {

                    LOGGER.info("Verification response from authentication server " + verificationResponse);

                    if(verificationResponse.equals("true"))
                    {
                        clientWriter.println("true");

                        LOGGER.info("Customer with customer Id "+ customerId + " logged in");

                        clientWriter.println(CustomerDetails.displayCustomerInfo(Integer.parseInt(customerId),database));

                        Transaction transactionController = new Transaction(LOGGER, database, bankCode);

                        transactionController.getChoice(clientReader, Integer.parseInt(customerId), clientWriter, rbiWriter);

                    }
                    else
                    {
                        LOGGER.info("Customer with customer Id "+ customerId + " entered wrong in");

                        clientWriter.println("false");
                    }

                }


            }
            else
            {
                clientWriter.println(false);
            }


        } catch(IOException e)
        {
            LOGGER.warning(e.getMessage());
        }

    }

}







