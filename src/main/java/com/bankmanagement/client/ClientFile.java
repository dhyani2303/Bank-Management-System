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
        Socket socket=null;
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
                    Login.login(consoleReader, serverWriter, serverReader);

                }
            }
            catch(IOException e)
            {
                System.out.println("Server is down as of now! Try again later");
            }

        } catch(UnknownHostException e)
        {
            System.out.println(e.getMessage());

        }
        finally
        {
            if(socket!=null)
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
