package com.bankmanagement.bank.server.common;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface Bank
{
    void getChoice(BufferedReader clientReader, int customerId, PrintWriter clientWriter);

    void deposit(BufferedReader clientReader, int customerId, PrintWriter clientWriter);

    void withdraw(BufferedReader clientReader, int customerId, PrintWriter clientWriter);

    void transfer(BufferedReader clientReader, int customerId, PrintWriter clientWriter);

    void showBalance(int customerId, PrintWriter clientWriter);

    void handleLogout(int customerId, PrintWriter clientWriter);


}
