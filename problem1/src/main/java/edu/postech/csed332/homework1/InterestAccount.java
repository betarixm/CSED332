package edu.postech.csed332.homework1;

public abstract class InterestAccount implements Account{

    int accountNumber;
    double balance;
    String ownerName;

    InterestAccount(int accountNumber, String ownerName, double initial) {
        this.accountNumber = accountNumber;
        this.balance = initial;
        this.ownerName = ownerName;
    }

    public abstract double getInterestRate();
    public abstract int getLowerBound();

    @Override
    public int getAccountNumber() {
        return accountNumber;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getOwner() {
        return ownerName;
    }

    @Override
    public void updateBalance(int elapsedDate) {
        balance = balance * Math.pow(1 + getInterestRate(), elapsedDate);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws IllegalOperationException {
        if(balance - getLowerBound() >= amount) {
            balance -= amount;
        } else {
            throw new IllegalOperationException("Not enough balance");
        }
    }
}
