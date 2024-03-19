package com.bankmanagement.bank.server.common;

import com.bankmanagement.bank.server.common.controller.Authentication;
import com.bankmanagement.bank.server.common.controller.Transaction;
import com.bankmanagement.bank.server.common.models.CustomerDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;


class ClientHandler implements Runnable
{

    protected Socket authSocket;

    protected Socket rbiConnectionSocket;

    protected Socket clientSocket;

    InetAddress authAddress;

    InetAddress rbiAddress;

    Logger LOGGER;

    Database database;

    String bankCode;

    String bankName;

    Authentication authentication;


    protected final int RBI_PORT = 8000;

    protected final int AUTH_PORT = 6000;

    ClientHandler(Socket clientSocket, InetAddress authAddress, InetAddress rbiAddress, Logger LOGGER, Database database, String bankName, String bankCode)
    {

        this.clientSocket = clientSocket;

        this.authAddress = authAddress;

        this.rbiAddress = rbiAddress;

        this.bankCode = bankCode;

        this.LOGGER = LOGGER;

        this.database = database;

        this.bankName = bankName;

       authentication = new Authentication(LOGGER,database,bankCode);
    }



    public void run()
    {
        try
        {
            authSocket = new Socket(authAddress, AUTH_PORT);

            rbiConnectionSocket = new Socket(rbiAddress, RBI_PORT);

            System.out.println(database.CUSTOMER.values());

            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            BufferedReader authReader = new BufferedReader(new InputStreamReader(authSocket.getInputStream()));

            PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            PrintWriter authWriter = new PrintWriter(authSocket.getOutputStream(), true);

            PrintWriter rbiWriter = new PrintWriter(rbiConnectionSocket.getOutputStream(), true);

            final String NEXT_LINE = "\n";



            while(true)
            {
                String operationCode = clientReader.readLine();

                if(operationCode!=null)
                {
                    switch(operationCode)
                    {
                        case "1":
                        {
                            clientWriter.println("Welcome to " + bankName);

                            authentication.login(clientReader, clientWriter, new PrintWriter(authSocket.getOutputStream(), true), authReader, rbiWriter);

                            break;
                        }
                        case "2":
                    {
                        authentication.register(clientReader, authWriter, rbiWriter, clientWriter);

                        break;

                    }
                    }
                }

            }


            //            int customerId = -1;
            //
            //            boolean isRegistered = false;
            //
            //            customerId = Integer.parseInt(authentication.login(clientReader, new PrintWriter(authSocket.getOutputStream(), true)));
            //
            //            if(customerId != -1)
            //            {
            //                isRegistered = true;
            //            }
            //            else
            //            {
            //                clientWriter.println("Customer Id does not exists.! Please press Y to get your registration done in bank else press N");
            //
            //
            //                if(clientReader.readLine().equals("Y"))
            //                {
            //                    clientWriter.println("You will soon be registered! Thank u!");
            //
            //                    authentication.register(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())), authWriter, rbiWriter,clientWriter);
            //
            //                }
            //                else
            //                {
            //                    clientWriter.println("Thank you!");
            //                }
            //
            //            }
            //
            //            if(isRegistered)
            //            {
            //
            //
            //                String verificationResponse = authReader.readLine();
            //
            //                if(verificationResponse != null)
            //                {
            //
            //                    if(verificationResponse.equals("true"))
            //                    {
            //                        LOGGER.info("Entered logged in");
            //
            //                        clientWriter.println("Logged in successfully");
            //
            //                         LOGGER.info("Login successful of customer id "+ customerId);
            //
            //                        clientWriter.println(CustomerDetails.displayCustomerInfo(customerId, database));
            //
            //                        Transaction transactionController = new Transaction(LOGGER, database, bankCode);
            //
            //                        transactionController.getChoice(clientReader, customerId, clientWriter, rbiWriter);
            //
            //                    }
            //                    else
            //                    {
            //                        clientWriter.println("Wrong Credentials");
            //                    }
            //                }
            //
            //            }


        } catch(IOException e)
        {
            //            try
            //            {
            if(e.getMessage().equals("Socket closed"))
            {
                LOGGER.info("Socket has been closed.");
            }
            else
            {
                LOGGER.warning("An IOException occurred: " + e.getMessage());
            }
        }

        //    serverSocket.close();


        //            } catch(IOException ex)
        //            {
        //                System.out.println(e.getMessage());
        //                if(e.getMessage().equals("Socket closed"))
        //                {
        //                    LOGGER.info("Socket has been closed.");
        //                }
        //                else
        //                {
        //                    LOGGER.warning("An IOException occurred: " + e.getMessage());
        //                }
        //            }


        //     }
        //finally
        //        {
        //            closeClientHandlerSockets();
        //
        //
        //        }
    }
}





