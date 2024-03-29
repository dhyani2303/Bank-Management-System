package com.bankmanagement.bank.server.sbi;

import com.bankmanagement.bank.server.common.BankServer;
import com.bankmanagement.bank.server.common.Database;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SBIServer extends BankServer
{
    final static Database sbiDatabase = new Database();

    final static int SERVER_PORT = 3002;


    final static String BANK_CODE = "3";


    SBIServer()
    {

        super(sbiDatabase, "SBI",BANK_CODE);
    }

    public static void main(String[] args)
    {
        try
        {
            InetAddress authAddress = InetAddress.getByName("localhost");

            InetAddress rbiAddress = InetAddress.getByName("localhost");

            SBIServer server = new SBIServer();

            server.startServer(SERVER_PORT, authAddress,rbiAddress);

        } catch(UnknownHostException e)
        {
            e.printStackTrace();
        }

    }


}



