package atm.transactions;

import atm.ATM;
import atm.Bank;
import atm.accounttypes.Account;
import atm.atmcomponents.CashDispenser;
import atm.atmcomponents.Display;
import atm.atmcomponents.Keyboard;
import atm.atmcomponents.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangePinTransactionTest {
  ATM atm;
  Account account;

  @BeforeEach
  void initialize() {
    Bank bank = new Bank("Mercantil");
    account =
        new Account(
            "Kevin Hernandez",
            "01234567890123456789",
            "1234",
                "kvkevinhz@gmail.com",
            20000);

    atm =
        new ATM(
            "1",
            bank,
            "1111",
            new Session(),
            new Display(),
            new Keyboard(),
            new CashDispenser(10000));
  }

  @Test
  void changePin() {
    ChangePinTransaction changePin = new ChangePinTransaction(atm);
    changePin.process(account, "0000");
    Assertions.assertEquals("0000", account.getPin());
  }
}
