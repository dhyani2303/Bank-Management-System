package com.bankmanagement.client;

import com.bankmanagement.client.controller.Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientFile
{

    public static void main(String[] args)
    {
        Socket socket = null;

        final String NEXT_LINE = "\n";
        try
        {
            InetAddress address = InetAddress.getByName("localhost");

            try
            {
                socket = new Socket(address, 3002);

                try(BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

                    PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

                    BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
                {

                    System.out.println("Already Existing Customer? Press 1" + NEXT_LINE + "Create a new Account now. Press 2");

                    String operationCode = consoleReader.readLine();

                    switch(operationCode)
                    {
                        case "1":
                        {
                            Login.login(consoleReader, serverWriter, serverReader);

                            break;

                        }

                        case "2":
                        {
                            Login.register(consoleReader, new PrintWriter(socket.getOutputStream(), true), serverReader);

                            Login.login(consoleReader, serverWriter, serverReader);

                            break;
                        }
                        default:
                        {
                            System.out.println("Invalid choice");
                        }
                    }


                }
            } catch(IOException e)
            {
                System.out.println("Server is down as of now! Try again later");
            }

        } catch(UnknownHostException e)
        {
            System.out.println(e.getMessage());

        } finally
        {
            if(socket != null)
            {
                try
                {
                    socket.close();

                } catch(IOException e)
                {
                    System.out.println(e.getMessage());
                }
            }

        }
    }
}
