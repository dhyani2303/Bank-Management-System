package com.bankmanagement.authenticationserver;



import com.bankmanagement.bank.server.common.util.Logging;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;




public class AuthenticationServer
{

    private static final Logger LOGGER = Logging.getAuthServerLogger();


    public static void main(String[] args)
    {
        ExecutorService executorService = null;

        try(ServerSocket serverSocket = new ServerSocket(6000);)
        {
            executorService = Executors.newCachedThreadPool();

            LOGGER.info("Server is waiting for bank to connect");


            while(true)
            {
                Socket clientSocket1 = serverSocket.accept();

                LOGGER.info("Bank got Connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket1,LOGGER);

                executorService.submit(clientHandler);

          //      System.out.println(Thread.currentThread());
            }


        } catch(IOException e)
        {
            LOGGER.warning(e.getMessage());
        }
        finally
        {
            executorService.shutdown();
        }
    }
}


