package com.bankmanagement.bank.server.common;

import com.bankmanagement.bank.server.common.controller.Authentication;
import com.bankmanagement.bank.server.common.models.CustomerDetails;
import com.bankmanagement.bank.server.common.controller.Transaction;
import com.bankmanagement.bank.server.common.util.Logging;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class BankServer
{
    protected Database database;

    protected static  final Logger LOGGER = Logging.getBankServerLogger();

    protected String bankCode;

     ServerSocket serverSocket;

    protected String bankName;

    protected Socket authSocket;

    protected Socket clientSocket;

    protected Socket rbiConnectionSocket;

    protected ExecutorService executorService;



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

            LOGGER.info(bankName + " server started on port " + serverPort);


            while(true)
            {

                clientSocket = serverSocket.accept();

                LOGGER.info("Client connected " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket,authAddress, rbiAddress,LOGGER,database,bankName,bankCode);

                executorService.submit(clientHandler);


            }
        }
        catch(IOException e)
        {
            if (e.getMessage().equals("Socket closed"))
            {
                LOGGER.info("Socket has been closed.");
            }
            else
            {
                LOGGER.warning("An IOException occurred: " + e.getMessage());
            }

        }
        finally
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


        } catch(IOException e)
        {
            if (e.getMessage().equals("Socket closed"))
            {
                LOGGER.info("Socket has been closed.");
            }
            else
            {
                LOGGER.warning("An IOException occurred: " + e.getMessage());
            }
        }
    }

    }



