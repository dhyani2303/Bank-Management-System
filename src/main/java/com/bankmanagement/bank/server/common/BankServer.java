package com.bankmanagement.bank.server.common;

import com.bankmanagement.bank.server.common.controller.Authentication;
import com.bankmanagement.bank.server.common.models.CustomerDetails;
import com.bankmanagement.bank.server.common.controller.Transaction;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankServer
{
    protected Database database;

    protected String bankCode;

    protected ServerSocket serverSocket;

    protected String bankName;

    protected Socket authSocket;

    protected Socket clientSocket;

    protected Socket rbiConnectionSocket;

    protected ExecutorService executorService;

    protected final int RBI_PORT = 8000;

    protected final int AUTH_PORT = 6000;

    public BankServer(Database database, String bankName, String bankCode)
    {
        this.database = database;

        this.bankName = bankName;

        this.bankCode = bankCode;
    }

    public void startServer(int serverPort, InetAddress authAddress, InetAddress rbiAddress)
    {
        try
        {
            serverSocket = new ServerSocket(serverPort);

            executorService = Executors.newCachedThreadPool();

            System.out.println(bankName + " server started on port " + serverPort);

            System.out.println(bankName + " server is waiting for client to connect");

            while(true)
            {

                clientSocket = serverSocket.accept();

                System.out.println("Client connected " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(authAddress, rbiAddress);

                executorService.submit(clientHandler);


            }
        } catch(IOException e)
        {
            System.out.println("Either Authentication or RBI server is down for a while");
        } finally
        {
            if(executorService != null)
            {
                executorService.shutdown();
            }
            closeClientHandlerSockets();
        }
    }

    private void closeClientHandlerSockets()
    {
        try
        {
                if(authSocket!=null)
                {
                    authSocket.close();
                }
                if(rbiConnectionSocket!=null)
                {
                    rbiConnectionSocket.close();
                }
                if(serverSocket!=null)
                {
                    serverSocket.close();
                }

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    class ClientHandler implements Runnable
    {
        InetAddress authAddress;

        InetAddress rbiAddress;

        ClientHandler(InetAddress authAddress, InetAddress rbiAddress)
        {
            this.authAddress = authAddress;

            this.rbiAddress = rbiAddress;
        }


        Authentication authentication = new Authentication(database, bankCode);

        public void run()
        {
            try
            {
                authSocket = new Socket(authAddress, AUTH_PORT);

                rbiConnectionSocket = new Socket(rbiAddress, RBI_PORT);

                BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                BufferedReader authReader = new BufferedReader(new InputStreamReader(authSocket.getInputStream()));

                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                PrintWriter authWriter = new PrintWriter(authSocket.getOutputStream(), true);

                PrintWriter rbiWriter = new PrintWriter(rbiConnectionSocket.getOutputStream(), true);

                final String NEXT_LINE = "\n";

                clientWriter.println("Welcome to " + bankName);

                int customerId = -1;

                boolean isRegistered = false;

                customerId = Integer.parseInt(authentication.login(clientReader, new PrintWriter(authSocket.getOutputStream(), true)));

                if(customerId != -1)
                {
                    isRegistered = true;
                }
                else
                {
                    clientWriter.println("Customer Id does not exists.! Please press Y to get your registration done in bank else press N");


                    if(clientReader.readLine().equals("Y"))
                    {
                        clientWriter.println("You will soon be registered! Thank u!");

                        authentication.register(new BufferedReader(new InputStreamReader((System.in))), authWriter, rbiWriter);

                    }
                    else
                    {
                        clientWriter.println("Thank you!");
                    }

                }

                if(isRegistered)
                {
                    System.out.println("Login Successful");

                    String verificationResponse = authReader.readLine();

                    if(verificationResponse!=null)
                    {

                        System.out.println(verificationResponse);

                        if(verificationResponse.equals("true"))
                        {
                            System.out.println("Entered logged in ");

                            clientWriter.println("Logged in successfully");

                            clientWriter.println(CustomerDetails.displayCustomerInfo(customerId, database));

                            Transaction transactionController = new Transaction(database, bankCode);

                            transactionController.getChoice(clientReader, customerId, clientWriter, rbiWriter);

                        }
                        else
                        {
                            clientWriter.println("Wrong Credentials");
                        }
                    }

                }

            } catch(IOException e)
            {
                System.out.println(e.getMessage());


            } finally
            {
                closeClientHandlerSockets();


            }
        }

    }
}


