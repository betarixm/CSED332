package edu.postech.csed332.homework1;

/**
 * An account with a high interest rate and a minimum balance.
 * The rate is 1% per day. E.g., if the balance is initially 100,
 * after 10 days (on the 11th day) the balance will be 100*(1.01)^10.
 * The balance should always be greater than or equal to 1000.
 */
class HighInterestAccount extends InterestAccount {
    HighInterestAccount(int accountNumber, String ownerName, double initial) {
        super(accountNumber, ownerName, initial);
    }

    @Override
    public double getInterestRate() {
        return 0.01;
    }

    @Override
    public int getLowerBound() {
        return 1000;
    }
}
