package com.bankmanagement.bank.server.rbi;

import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.common.util.Logging;
import com.bankmanagement.bank.server.rbi.controller.Transaction;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class RBIServer
{
    final static HashMap<String, Database> bankDatabases = new HashMap<>();
    final static Logger LOGGER = Logging.getRbiServerLogger();

    final static Database hdfc = new Database();

    final static Database icici = new Database();

    final static Database sbi = new Database();

    static
    {
        bankDatabases.put("1", hdfc); //HDFC
        bankDatabases.put("2", icici); //ICICI
        bankDatabases.put("3", sbi); //SBI
    }


    public static void main(String[] args)
    {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try(ServerSocket serverSocket = new ServerSocket(8000))
        {

            LOGGER.info("RBI waiting for other banks to connect");

            while(true)
            {
                Socket clientSocket = serverSocket.accept();

                LOGGER.info("Bank got connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket);

                executorService.submit(clientHandler);


            }

        } catch(IOException e)
        {
            LOGGER.warning(e.getMessage());
        } finally
        {
            executorService.shutdown();
        }
    }


    static class ClientHandler implements Runnable
    {
        private Socket clientSocket = null;

        ClientHandler(Socket socket)
        {
            this.clientSocket = socket;

        }

        public void run()
        {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true))
            {
                while(true)
                {
                    String operationCode = reader.readLine();

                    if(operationCode != null)
                    {
                        String bankCode = operationCode.substring(0, 1);

                        String operation = operationCode.substring(1, 2);

                        Database database = bankDatabases.get(bankCode);

                        Transaction transaction = new Transaction(database,LOGGER);

                        switch(operation)
                        {


                            case "1":
                            {

                                transaction.register(reader);

                                break;
                            }
                            case "2":
                            {

                                transaction.deposit(reader);

                                break;

                            }
                            case "3":
                            {
                                transaction.withdraw(reader);

                                break;
                            }
                            case "4":
                            {

                                transaction.transfer(reader);

                                break;
                            }



                        }
                    }
                }
            } catch(IOException e)
            {
               LOGGER.warning(e.getMessage());
            }


        }
    }
}


