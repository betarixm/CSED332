package edu.postech.csed332.homework1;

import java.util.List;
import java.util.Map;

/**
 * Bank manages a collection of accounts. An account number is assigned
 * incrementally from 100000. E.g., the first account has 100000, the second
 * has 100001, etc. There are also functions for finding specific accounts.
 */
public class Bank {

    // TODO: add more fields to implement this class
    // (hint: use Java Collection Framework, including List, Map, Set, etc.)

    /**
     * Create a bank. Initially, there is no account.
     */
    Bank() {
        // TODO implement this
    }

    /**
     * Find an account by a given account number.
     *
     * @param accNum an account number
     * @return the account with number accNum; null if no such account exists
     */
    Account findAccount(int accNum) {
        // TODO implement this
        return null;
    }

    /**
     * Find all accounts owned by a given name
     *
     * @param name owner's name
     * @return a list of accounts sorted in ascending order by account number
     */
    List<Account> findAccountByName(String name) {
        // TODO implement this
        return null;
    }

    /**
     * Create a new account and register it to the bank.
     *
     * @param name    owner name
     * @param accType HIGH or LOW
     * @param initial initial balance
     * @return the newly created account; null if not possible
     */
    Account createAccount(String name, ACCTYPE accType, double initial) {
        // TODO implement this
        return null;
    }

    /**
     * Transfer a given amount of money from src account to dst account.
     *
     * @param src    source account
     * @param dst    destination acount
     * @param amount of money
     * @throws IllegalOperationException if not possible
     */
    void transfer(Account src, Account dst, double amount) throws IllegalOperationException {
        // TODO implement this
    }
}
