package com.bankmanagement.bank.server.common.util;

import java.util.Random;

public class DataGeneration
{
    static Random random = new Random();
    public static int generateAccountNumber()
    {


        int randomDigits = 10000000 + random.nextInt(90000000);

        return Integer.parseInt("10" + randomDigits);
    }

    public static int generateCustomerId()
    {
        int randomDigits = 100 + random.nextInt(900);

        return Integer.parseInt("20"+randomDigits);

    }

    public static int generatePin()
    {
        return 1000  + random.nextInt(9000);
    }


}
