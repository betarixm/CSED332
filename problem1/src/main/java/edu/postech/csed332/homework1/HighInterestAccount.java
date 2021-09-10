package edu.postech.csed332.homework1;

/**
 * An account with a high interest rate and a minimum balance.
 * The rate is 1% per day. E.g., if the balance is initially 100,
 * after 10 days (on the 11th day) the balance will be 100*(1.01)^10.
 * The balance should always be greater than or equal to 1000.
 */
class HighInterestAccount implements Account {
    double RATE = 0.01;
    double BOUND = 1000;

    int accountNumber;
    double balance;
    String ownerName;

    HighInterestAccount(int _accountNumber, String _ownerName, double _initial) {
        accountNumber = _accountNumber;
        balance = _initial;  // Ensure that _initial is greater than or equal to BOUND
        ownerName = _ownerName;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return ownerName;
    }

    public void updateBalance(int elapsedDate) {
        balance = balance * Math.pow(1 + RATE, elapsedDate);
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws IllegalOperationException {
        if(balance - BOUND < amount) {
            throw new IllegalOperationException("Not enough balance");
        }
        balance -= amount;
    }
}
