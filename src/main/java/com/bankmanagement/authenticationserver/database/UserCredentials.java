package com.bankmanagement.authenticationserver.database;


import java.util.HashMap;

public class UserCredentials
{
    final static HashMap<Integer, Integer> CREDENTIAL = new HashMap<>();

    private static UserCredentials singleInstance = null;


    private UserCredentials()
    {

    }

    public static UserCredentials getInstance()
    {

        if (singleInstance == null)
        {

            singleInstance = new UserCredentials();

        }

        return singleInstance;
    }

    public HashMap<Integer,Integer> getCredential()
    {
        return  CREDENTIAL;
    }

    public void add(String customerId,String password) {

        CREDENTIAL.put(Integer.parseInt(customerId),Integer.parseInt(password));

        System.out.println("Successful addition to authentication server database");

        System.out.println(CREDENTIAL);

    }



    public void remove(int customerId) {

        CREDENTIAL.remove(customerId);
    }


}



