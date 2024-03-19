package com.bankmanagement.client.controller;

import com.bankmanagement.client.utils.Validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Login
{
    static String NEXT_LINE = "\n";

    public static void login(BufferedReader consoleReader, PrintWriter writer, BufferedReader serverReader)

    {
        boolean stop = false;

        String customerId = "";

        String password = "";
        try
        {

            writer.println("1");

            String response = serverReader.readLine();

            if(response != null)
            {
                System.out.println(NEXT_LINE + response + NEXT_LINE);

                while(!stop)
                {
                    System.out.println(NEXT_LINE + "Enter the customerId: ");

                    customerId = consoleReader.readLine();

                    if(Validation.checkLength(customerId, 5))
                    {
                        stop = true;

                        break;

                    }

                    System.out.println("Invalid Customer Id" + NEXT_LINE);

                }


                stop = false;

                while(!stop)
                {

                    System.out.println(NEXT_LINE + "Enter the Password: ");

                    password = consoleReader.readLine();

                    if(Validation.checkLength(password, 4))
                    {
                        stop = true;

                        break;
                    }
                    System.out.println("Invalid Password " + NEXT_LINE);


                }

                writer.println(customerId + " "+ password);

                String serverResponse = serverReader.readLine();

                if(serverResponse != null)
                {

                    if(serverResponse.equals("true"))
                    {
                        System.out.println("Logged in successfully " + NEXT_LINE);

                        System.out.println(serverReader.readLine() + NEXT_LINE);

                        Transaction transactionController = new Transaction();

                        transactionController.giveChoice(consoleReader, writer, serverReader);
                    }
                    else
                    {
                        System.out.println("Wrong Credentials");

                    }
                }
                else
                {
                    System.out.println("Disconnected from server");
                }
            }
            else
            {
                System.out.println("Server is down");
            }


        } catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public static void register(BufferedReader consoleReader, PrintWriter writer, BufferedReader serverReader)
    {
        boolean stop = false;

        String firstName = "";

        String lastName = "";

        try
        {

            writer.println("2");


            System.out.println(NEXT_LINE + "Enter the following details for registration process" + NEXT_LINE + "Name should at least consist of 3 letters" + NEXT_LINE);

            while(!stop)
            {

                System.out.println(NEXT_LINE + "Enter the first name");

                firstName = consoleReader.readLine();

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

                lastName = consoleReader.readLine();

                if(Validation.isValidString(lastName))
                {

                    stop = true;

                    break;
                }
                System.out.println("Invalid last name " + NEXT_LINE);
            }

            writer.println(firstName);

            writer.println(lastName);

         //   writer.println(firstName + " " + lastName);

            String[] serverResponse = serverReader.readLine().split(":");

            System.out.println(serverResponse[0]+NEXT_LINE+serverResponse[1]+NEXT_LINE+serverResponse[2]+NEXT_LINE+serverResponse[3]);


        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
