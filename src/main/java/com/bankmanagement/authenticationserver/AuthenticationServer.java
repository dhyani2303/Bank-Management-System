package com.bankmanagement.authenticationserver;


import com.bankmanagement.authenticationserver.controller.LoginController;
import com.bankmanagement.authenticationserver.database.UserCredentials;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class ClientHandler implements Runnable
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

            while(!Thread.currentThread().isInterrupted())
            {

                String operationCode = reader.readLine();

                if(operationCode != null)
                {

                    switch(operationCode)
                    {
                        case "1":
                        {

                            System.out.println("Entered registration");

                            String customerId = reader.readLine();

                            String password = reader.readLine();

                            System.out.println(customerId + " " + password);

                            UserCredentials.getInstance().add(customerId, password);

                            break;
                        }

                        case "2":
                        {
                            System.out.println("Entered login functionality");

                            String customerId = reader.readLine();

                            String password = reader.readLine();

                            boolean verificationResult = LoginController.verify(customerId, password);

                            System.out.println(verificationResult);

                            writer.println(verificationResult);

                            break;

                        }


                    }

                }


            }

        } catch(IOException e)
        {
            System.out.println(e.toString());
        }

    }
}

public class AuthenticationServer
{
    public static void main(String[] args)
    {
        ExecutorService executorService = null;

        try(ServerSocket serverSocket = new ServerSocket(6000);)
        {
            executorService = Executors.newCachedThreadPool();


            System.out.println("Server is waiting for bank to connect");

            while(true)
            {
                Socket clientSocket1 = serverSocket.accept();

                System.out.println("Bank got connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket1);

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
}


