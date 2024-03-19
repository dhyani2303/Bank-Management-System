package com.bankmanagement.bank.server.hdfc;

import com.bankmanagement.bank.server.common.BankServer;
import com.bankmanagement.bank.server.common.Database;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HDFCServer extends BankServer
{
    final static Database hdfcDatabase = new Database();
    final static int SERVER_PORT =3000;

    final static String bankCode = "1";


    HDFCServer()
    {

        super(hdfcDatabase,"HDFC",bankCode);
    }

    public static void main(String[] args)
    {
        try
        {
            InetAddress authAddress = InetAddress.getByName("localhost");

            InetAddress rbiAddress = InetAddress.getByName("localhost");

            HDFCServer server = new HDFCServer();

            server.startServer(SERVER_PORT,authAddress,rbiAddress);

        } catch(UnknownHostException e)
        {

            LOGGER.warning(e.getMessage());
        }

    }


}


