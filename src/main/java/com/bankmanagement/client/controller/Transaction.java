package com.bankmanagement.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Transaction
{
    String NEXT_LINE = "\n";

    public void giveChoice(BufferedReader consoleReader, PrintWriter serverWriter, BufferedReader serverReader)
    {
        while(true)
        {
            System.out.println("Select the option to perform the operation" + NEXT_LINE +
                    "1.Deposit" + NEXT_LINE + "2.Withdraw " + NEXT_LINE + "3.Transfer" +NEXT_LINE +"4.Get Balance" +NEXT_LINE + "5.Logout"+ NEXT_LINE);

            try
            {
                String operationCode = consoleReader.readLine();

                switch(operationCode)
                {
                    case "1":
                    {
                        serverWriter.println("1");

                        deposit(consoleReader, serverWriter, serverReader);

                        break;

                    }
                    case "2":
                    {
                        serverWriter.println("2");

                        withdraw(consoleReader,serverWriter,serverReader);

                        break;
                    }
                    case "3":
                    {
                        serverWriter.println("3");

                        transfer(consoleReader,serverWriter,serverReader);

                        break;
                    }
                    case "4":
                    {
                        serverWriter.println("4");

                        getBalance(serverReader);

                        break;
                    }

                    case "5":
                    {
                        serverWriter.println("5");

                        handleLogout(serverReader,serverWriter);

                        return;
                    }
                    default:
                    {
                        System.out.println("Invalid Choice "+ NEXT_LINE);
                    }
                }


            } catch(IOException e)
            {
                throw new RuntimeException(e);
            }

        }


    }

    public void deposit(BufferedReader consoleReader,PrintWriter serverWriter,BufferedReader serverReader)
    {
        try
        {
            System.out.println(NEXT_LINE + "Enter the amount to be deposited");

            int amountToDeposit=  Integer.parseInt(consoleReader.readLine());

            serverWriter.println(amountToDeposit);

            String response = serverReader.readLine();

            System.out.println(NEXT_LINE+ response + NEXT_LINE);

        }
        catch(NumberFormatException e)
        {
            System.out.println("Enter number only");

            deposit(consoleReader,serverWriter,serverReader);


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public void withdraw(BufferedReader consoleReader,PrintWriter serverWriter,BufferedReader serverReader)
    {

        try
        {
            System.out.println(NEXT_LINE + "Enter the amount  to be withdrawn");

            int amountToWithDraw = Integer.parseInt(consoleReader.readLine());

            serverWriter.println(amountToWithDraw);

            String response = serverReader.readLine();

            System.out.println(NEXT_LINE+response + NEXT_LINE);


        }
        catch(NumberFormatException e)
        {
            System.out.println("Enter number only");

            withdraw(consoleReader,serverWriter,serverReader);


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void transfer(BufferedReader consoleReader,PrintWriter serverWriter,BufferedReader serverReader)
    {

        try
        {
            System.out.println(NEXT_LINE + "Enter the account number of payee ");

            int accountNoOfPayee = Integer.parseInt(consoleReader.readLine());

            serverWriter.println(accountNoOfPayee);

            if(serverReader.readLine().equals("true"))
            {
                System.out.println(NEXT_LINE +"Enter the amount to be transmitted");

                int amountToTransfer = Integer.parseInt(consoleReader.readLine());

                serverWriter.println(amountToTransfer);

                String response = serverReader.readLine();

                System.out.println(NEXT_LINE+ response + NEXT_LINE);
            }
            else
            {
                String response = serverReader.readLine();

                System.out.println(response);
            }
        }
        catch(NumberFormatException e)
        {
            System.out.println("Enter number only");

            transfer(consoleReader,serverWriter,serverReader);


        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void getBalance(BufferedReader serverReader)
    {
        try
        {
            String response = (serverReader.readLine());

            System.out.println(NEXT_LINE+response+ NEXT_LINE);

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void handleLogout(BufferedReader serverReader,PrintWriter serverWriter)
    {
        try
        {
            String response = serverReader.readLine();

            System.out.println(response);


        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                serverReader.close();

                serverWriter.close();

            } catch(IOException e)
            {

                e.printStackTrace();
            }

        }
    }
}
