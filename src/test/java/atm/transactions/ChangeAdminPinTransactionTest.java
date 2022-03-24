package atm.transactions;

import atm.ATM;
import atm.Bank;
import atm.atmcomponents.CashDispenser;
import atm.atmcomponents.Display;
import atm.atmcomponents.Keyboard;
import atm.atmcomponents.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangeAdminPinTransactionTest {
  ATM atm;

  @BeforeEach
  void initialize() {
    Bank bank = new Bank("Mercantil");
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
  void changeAdminPin() {
    ChangeAdminPinTransaction changeAdminPin = new ChangeAdminPinTransaction(atm);
    changeAdminPin.process("1234");
    Assertions.assertEquals("1234", atm.getAdminPin());
  }
}
