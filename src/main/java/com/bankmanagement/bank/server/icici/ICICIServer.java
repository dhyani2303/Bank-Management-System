package com.bankmanagement.bank.server.icici;

import com.bankmanagement.bank.server.common.BankServer;
import com.bankmanagement.bank.server.common.Database;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class ICICIServer extends BankServer
{

    final static Database iciciDatabase = new Database();

    final static int SERVER_PORT = 3001;

    ;
    final static String BANK_CODE = "2";


    ICICIServer()
    {

        super(iciciDatabase, "ICICI", BANK_CODE);
    }

    public static void main(String[] args)
    {
        try
        {
            InetAddress authAddress = InetAddress.getByName("localhost");

            InetAddress rbiAddress = InetAddress.getByName("localhost");

            ICICIServer server = new ICICIServer();

            server.startServer(SERVER_PORT, authAddress,rbiAddress);

        } catch(UnknownHostException e)
        {
           LOGGER.warning(e.getMessage());
        }

    }


}



