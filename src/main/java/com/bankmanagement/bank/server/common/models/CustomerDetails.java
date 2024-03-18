package com.bankmanagement.bank.server.common.models;

import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.common.util.DataGeneration;

import java.io.Serializable;

public class CustomerDetails implements Serializable
{
    private String firstName = null;

    private String lastName = null;

    AccountDetails accountDetails = new AccountDetails(DataGeneration.generateAccountNumber(), DataGeneration.generateCustomerId(), DataGeneration.generatePin());

    public CustomerDetails(String firstName, String lastName)
    {
        this.firstName = firstName;

        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public static String displayCustomerInfo(int customerId, Database database)
    {
        String firstName =  database.getCustomer(customerId).getFirstName();

        String lastName =  database.getCustomer(customerId).getLastName();

        double balance = database.getCustomer(customerId).getAccountDetails().getBalance();

        return "Welcome " + firstName + " "+ lastName + " " + "Your balance is "+ balance;


    }


    public AccountDetails getAccountDetails()
    {
        return accountDetails;
    }
}





