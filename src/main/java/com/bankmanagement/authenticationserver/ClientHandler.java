package com.bankmanagement.authenticationserver;

import com.bankmanagement.authenticationserver.controller.LoginController;
import com.bankmanagement.authenticationserver.database.UserCredentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler implements Runnable
{
    private Socket clientSocket = null;

    private Logger LOGGER ;

    ClientHandler(Socket socket,Logger LOGGER)
    {
        this.clientSocket = socket;

        this.LOGGER= LOGGER;

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

                    switch(operationCode)
                    {
                        case "1":
                        {

                            String[] dataFromBank = reader.readLine().split(" ");

                            String customerId = dataFromBank[0];

                            String password = dataFromBank[1];

                            System.out.println(customerId + " " + password);

                            boolean result= UserCredentials.getInstance().add(customerId, password);


                            if(result)
                            {
                                LOGGER.info("User with customer Id "+ customerId + " is registered");
                            }

                            break;
                        }

                        case "2":
                        {

                            String[] dataFromBank = reader.readLine().split(" ");

                            String customerId = dataFromBank[0];

                            String password = dataFromBank[1];

                            boolean verificationResult = LoginController.verify(customerId, password);

                            if(verificationResult)
                            {
                                LOGGER.info("Verification of user with customer Id "+ customerId + " is successful");
                            }

                            writer.println(verificationResult);

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

