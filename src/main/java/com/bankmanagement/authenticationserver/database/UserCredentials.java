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

    public boolean add(String customerId,String password) {

       CREDENTIAL.put(Integer.parseInt(customerId),Integer.parseInt(password));

       return  true;




    }



    public void remove(int customerId) {

        CREDENTIAL.remove(customerId);
    }


}



