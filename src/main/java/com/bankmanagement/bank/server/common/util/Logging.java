package com.bankmanagement.bank.server.common.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.*;

import java.util.logging.SimpleFormatter;

public class Logging
{
    private static final Logger AUTH_SERVER_LOGGER = Logger.getLogger("AuthServer");
    private static final Logger RBI_SERVER_LOGGER = Logger.getLogger("RBIServer");
    private static final Logger COMMON_BANK_SERVER_LOGGER = Logger.getLogger("BankServer");

    static
    {
        try
        {
            // Authentication server Logger.
            FileHandler authFileHandler = new FileHandler("auth_server.log",false);

            authFileHandler.setFormatter(new SimpleFormatter());

            AUTH_SERVER_LOGGER.addHandler(authFileHandler);

            AUTH_SERVER_LOGGER.setLevel(Level.ALL);


            //RBI Server Logger

            FileHandler rbiFileHandler = new FileHandler("rbi_server.log",false);

            rbiFileHandler.setFormatter(new SimpleFormatter());

            RBI_SERVER_LOGGER.addHandler(rbiFileHandler);

            RBI_SERVER_LOGGER.setLevel(Level.ALL);

            //Bank server logger

            FileHandler bankFileHandler = new FileHandler("bank_server.log",false);

            bankFileHandler.setFormatter(new SimpleFormatter());

            COMMON_BANK_SERVER_LOGGER.addHandler(bankFileHandler);

            COMMON_BANK_SERVER_LOGGER.setLevel(Level.ALL);


            Logger rootLogger = LogManager.getLogManager().getLogger("");

            Handler[] handlers = rootLogger.getHandlers();

            for (Handler handler : handlers)
            {
                rootLogger.removeHandler(handler);
            }

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static  Logger getAuthServerLogger()
    {
        return AUTH_SERVER_LOGGER;
    }

    public static  Logger getRbiServerLogger()
    {
        return RBI_SERVER_LOGGER;
    }
    public static  Logger getBankServerLogger()
    {
        return COMMON_BANK_SERVER_LOGGER;
    }

}
