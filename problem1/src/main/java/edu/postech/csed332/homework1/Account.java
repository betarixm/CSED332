package edu.postech.csed332.homework1;

/**
 * This interface contains the information needed to represent an account.
 */
interface Account {
    /**
     * Return the account number
     *
     * @return the account number
     */
    int getAccountNumber();

    /**
     * Return the current balance of the account
     *
     * @return the balance
     */
    double getBalance();

    /**
     * Return the name of the owner
     *
     * @return the owner name
     */
    String getOwner();

    /**
     * Update the balance according to the interest rate and elapsed date.
     *
     * @param elapsedDate
     */
    void updateBalance(int elapsedDate);

    /**
     * Deposit a given amount of money
     *
     * @param amount of money
     */
    void deposit(double amount);

    /**
     * Withdraw a given amount of money
     *
     * @param amount of money
     * @throws IllegalOperationException if not possible
     */
    void withdraw(double amount) throws IllegalOperationException;
}

