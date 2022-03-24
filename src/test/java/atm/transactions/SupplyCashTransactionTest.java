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

class SupplyCashTransactionTest {
  CashDispenser cashDispenser;
  ATM atm;

  @BeforeEach
  void initialize() {
    cashDispenser = new CashDispenser(1000);
    Bank bank = new Bank("Banesco");
    atm = new ATM("1", bank, "1111", new Session(), new Display(), new Keyboard(), cashDispenser);
  }

  @Test
  void supplyCash() {
    SupplyCashTransaction supplyTransaction = new SupplyCashTransaction(atm);
    supplyTransaction.process(1000, cashDispenser);
    Assertions.assertEquals(2000, cashDispenser.getBalance());
  }
}
