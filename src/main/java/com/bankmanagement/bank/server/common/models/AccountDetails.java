package com.bankmanagement.bank.server.common.models;

import com.bankmanagement.bank.server.common.Database;

public class AccountDetails
{
    private final int accountNo;

    private final int customerId;

    private final int password;

    private double balance;

    AccountDetails(int accountNo, int customerId, int password)
    {
        this.accountNo = accountNo;

        this.customerId = customerId;

        this.password = password;
    }


    public int getCustomerId()
    {
        return customerId;
    }

    public int getAccountNo()
    {
        return accountNo;
    }

    public int getPassword()
    {
        return password;
    }

    public double getBalance()
    {
        return balance;
    }

    public double depositAmount(int amount)
    {
        return balance += amount;
    }

    public double withdrawAmount(int amount)
    {
        return balance -= amount;
    }

    public double transfer(int customerId, int amount, Database database)
    {
        double updatedBalance = withdrawAmount(amount);

        database.getCustomer(customerId).getAccountDetails().depositAmount(amount);

        return updatedBalance;

    }

}

//Account details like Account No,CustomerId,Password,Balance,getAccBalance,getAccNo.




