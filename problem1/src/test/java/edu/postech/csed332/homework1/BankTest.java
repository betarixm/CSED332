package edu.postech.csed332.homework1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    Bank wb;

    @BeforeEach
    void setup() {
        wb = new Bank();
    }

    @Test
    void testFindAccount() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        Account reqAccount = wb.findAccount(100000);
        assertEquals(reqAccount.getOwner(), "Thomas");
    }

    @Test
    void testHighInterestAccount() {
        wb.createAccount("Thomas", ACCTYPE.HIGH, 90000.);
        Account s = wb.findAccount(100000);
        s.deposit(10000.);
        s.updateBalance(20);
        assertEquals(s.getBalance(), 122019.00399479672);
    }
}

