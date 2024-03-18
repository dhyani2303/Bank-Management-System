package com.bankmanagement.authenticationserver.controller;

import com.bankmanagement.authenticationserver.database.UserCredentials;


public class LoginController
{

    public static boolean verify(String customerId,String password) {

        var CREDENTIAL = UserCredentials.getInstance().getCredential();


        if(CREDENTIAL.containsKey(Integer.parseInt(customerId)))
        {

            if(CREDENTIAL.get(Integer.parseInt(customerId))==Integer.parseInt(password))
            {
                return true;
            }
        }



        return false;
    }
}

