package com.bankmanagement.client.utils;

public class Validation
{
    public static boolean checkLength(String data, int length)
    {
        return  (data.length()==length && data.matches("[0-9]+"));
    }

    public static boolean isValidString(String name)
    {
        return (name!=null && name.matches("[a-zA-Z]{3,}"));
    }
}
