package com.bankmanagement.client.utils;

public class Validation
{
    public static boolean checkLength(String data, int length)
    {
        return  (data.length()==length && data.matches("[0-9]+"));
    }
}
