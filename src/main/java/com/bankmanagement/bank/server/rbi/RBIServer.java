package com.bankmanagement.bank.server.rbi;

import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.rbi.controller.Transaction;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RBIServer
{
    final static HashMap<String, Database> bankDatabases = new HashMap<>();

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
            System.out.println("RBI waiting for other banks to connect");

            while(true)
            {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client got connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket);

                executorService.submit(clientHandler);


            }

        } catch(IOException e)
        {
            System.out.println(e.getMessage());
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

                        Transaction transaction = new Transaction(database);

                        switch(operation)
                        {


                            case "1":
                            {

                                System.out.println("entered registration");

                                transaction.register(reader);

                                System.out.println("Completed registration");

                                break;
                            }
                            case "2":
                            {

                                System.out.println("Entered deposit");

                                transaction.deposit(reader);

                                System.out.println("Completed deposit");

                                break;

                            }
                            case "3":
                            {
                                transaction.withdraw(reader);

                                break;
                            }
                            case "4":
                            {
                                System.out.println("Entered transfer");

                                transaction.transfer(reader);

                                System.out.println("Completed transfer");

                                break;
                            }



                        }
                    }
                }
            } catch(IOException e)
            {
                e.printStackTrace();
            }


        }
    }
}


