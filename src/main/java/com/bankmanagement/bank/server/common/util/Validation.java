package com.bankmanagement.bank.server.common.util;

import com.bankmanagement.bank.server.common.Database;
import com.bankmanagement.bank.server.common.models.CustomerDetails;

public class Validation
{
    public static  boolean isValidAmount(int amount)
    {

        return amount > 0;
    }

    public static boolean hasSufficientBalance(int amount, int customerId, Database database)
    {
        double balance = database.getCustomer(customerId).getAccountDetails().getBalance();

        if(amount<=balance)

            return true;

        return false;
    }
    public  static int PayeeExist(String accountNo,Database database)
    {


        if  (accountNo.length()==10 && database.getCustomerByAccountNo(Integer.parseInt(accountNo))!=null)
        {
            return  database.getCustomerByAccountNo(Integer.parseInt(accountNo)).getAccountDetails().getCustomerId();
        }
        return 0;

    }

    public static boolean CustomerExists(int customerId,Database database)

    {
        CustomerDetails customerDetails= database.getCustomer(customerId);

        if(customerDetails!=null)
            return true;

        return false;
    }



}
