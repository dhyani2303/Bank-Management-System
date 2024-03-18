package com.bankmanagement.bank.server.common;

import com.bankmanagement.bank.server.common.models.CustomerDetails;

import java.util.HashMap;

public class Database
{
    final HashMap<Integer, CustomerDetails> CUSTOMER = new HashMap<>();

    public void addCustomer(CustomerDetails  customer) {

        if(!CUSTOMER.containsKey(customer.getAccountDetails().getCustomerId()))
        {
            CUSTOMER.put(customer.getAccountDetails().getCustomerId(), customer);

            System.out.println("Addition to database is successful");


        }


    }

    public CustomerDetails getCustomerByAccountNo(double accountNo)
    {
        for(CustomerDetails customerDetails : CUSTOMER.values())
        {
            if(customerDetails.getAccountDetails().getAccountNo() == accountNo)
            {
                return customerDetails;
            }

        }
        return null;
    }

    public CustomerDetails getCustomer(int customerId) {



        return CUSTOMER.get(customerId);
    }

}
