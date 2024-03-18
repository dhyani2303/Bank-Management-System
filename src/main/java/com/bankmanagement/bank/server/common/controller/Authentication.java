package com.bankmanagement.bank.server.common.controller;


import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.common.util.Validation;
import com.bankmanagement.bank.server.common.models.CustomerDetails;
import com.google.gson.Gson;

import java.io.*;


public class Authentication implements Serializable
{
    Database database = null;

    String bankCode;


    public Authentication(Database database, String bankCode)
    {
        this.database = database;

        this.bankCode = bankCode;
    }


    final String NEXT_LINE = "\n";

    public void register(BufferedReader reader, PrintWriter authWriter, PrintWriter rbiWriter)
    {
        boolean stop = false;

        String firstName = "";

        String lastName = "";
        synchronized(Authentication.class)
        {

            try
            {
                authWriter.println("1");

                System.out.println(NEXT_LINE + "Enter the following details for registration process" + NEXT_LINE + "Name should at least consist of 3 letters" + NEXT_LINE);


                while(!stop)
                {

                    System.out.println(NEXT_LINE + "Enter the first name");

                    firstName = reader.readLine();

                    if(Validation.isValidString(firstName))
                    {
                        stop = true;

                        break;
                    }

                    System.out.println("Invalid first name " + NEXT_LINE);

                }

                stop = false;


                while(!stop)
                {

                    System.out.println(NEXT_LINE + "Enter the last name");

                    lastName = reader.readLine();

                    if(Validation.isValidString(lastName))
                    {

                        stop = true;

                        break;
                    }
                    System.out.println("Invalid last name " + NEXT_LINE);

                }
                CustomerDetails customerDetail = new CustomerDetails(firstName, lastName);

                int customerId = customerDetail.getAccountDetails().getCustomerId();

                database.addCustomer(customerDetail);

                System.out.println(NEXT_LINE + "Registration successful " + NEXT_LINE);

                System.out.println(NEXT_LINE + "Account Number is " + customerDetail.getAccountDetails().getAccountNo() + NEXT_LINE + "The customer number is " + customerDetail.getAccountDetails().getCustomerId() + NEXT_LINE + "The password is " + customerDetail.getAccountDetails().getPassword());

                authWriter.println(customerId);

                authWriter.println(customerDetail.getAccountDetails().getPassword());

                rbiWriter.println(bankCode + "1");

                Gson gson = new Gson();

                String jsonCustomerDetails = gson.toJson(customerDetail);

                rbiWriter.println(jsonCustomerDetails);


            } catch(IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    public String login(BufferedReader clientReader, PrintWriter authWriter)
    {
        String customerId = null;

        try
        {

            customerId = clientReader.readLine();

            String password = clientReader.readLine();

            if(Validation.CustomerExists(Integer.parseInt(customerId), database))
            {
                authWriter.println("2");

                authWriter.println(customerId);

                authWriter.println(password);

                return customerId;

            }


        } catch(IOException e)
        {
            e.printStackTrace();
        }


        return "-1";

    }

}




