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
            if(serverReader.readLine() != null)
            {
                System.out.println(NEXT_LINE + serverReader.readLine() + NEXT_LINE);

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

                writer.println(customerId);

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

                writer.println(password);

                String response = serverReader.readLine();

                if(response != null)
                {

                    System.out.println(response + NEXT_LINE);


                    if(response.equals("Logged in successfully"))
                    {
                        System.out.println(serverReader.readLine() + NEXT_LINE);

                        Transaction transactionController = new Transaction();

                        transactionController.giveChoice(consoleReader, writer, serverReader);
                    }
                    else
                    {
                        writer.println(consoleReader.readLine());

                        System.out.println(serverReader.readLine());

                    }
                }
                else
                {
                    System.out.println("Disconnected from server");
                }
            }



        } catch(IOException e)
        {
            e.printStackTrace();
        }


    }
}
