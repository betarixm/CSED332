package edu.postech.csed332.homework1;

/**
 * An account with a low interest rate. The rate is 0.5% per day.
 * E.g., if the balance is initially 100, after 10 days (on the 11th day)
 * the balance will be 100*(1.005)^10.
 */
class LowInterestAccount implements Account {
    double RATE = 0.005;

    int accountNumber;
    double balance;
    String ownerName;

    LowInterestAccount(int _accountNumber, String _ownerName, double _initial) {
        accountNumber = _accountNumber;
        balance = _initial;
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
        if(balance < amount) {
            throw new IllegalOperationException("Not enough balance");
        }
        balance -= amount;
    }
}
