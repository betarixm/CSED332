package edu.postech.csed332.homework1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Bank manages a collection of accounts. An account number is assigned
 * incrementally from 100000. E.g., the first account has 100000, the second
 * has 100001, etc. There are also functions for finding specific accounts.
 */
public class Bank {
    int BOUND = 100000;
    ArrayList<Account> accounts = new ArrayList<>();
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
        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accNum) {
                return acc;
            }
        }

        return null;
    }

    /**
     * Find all accounts owned by a given name
     *
     * @param name owner's name
     * @return a list of accounts sorted in ascending order by account number
     */
    List<Account> findAccountByName(String name) {
        List<Account> filtered = accounts.stream()
                .filter(a -> a.getOwner().equals(name))
                .sorted(Comparator.comparing(Account::getAccountNumber)).toList();

        return (filtered.size() == 0) ? null : filtered;
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
        if(accType == ACCTYPE.HIGH && initial < 1000) {
            return null;
        }

        Account acc = (accType == ACCTYPE.HIGH)
                ? (new HighInterestAccount(accounts.size() + BOUND, name, initial))
                : (new LowInterestAccount(accounts.size() + BOUND, name, initial));

        accounts.add(acc);

        return acc;
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
        src.withdraw(amount);
        dst.deposit(amount);
    }
}
