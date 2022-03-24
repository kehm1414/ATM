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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DepositCashTransactionTest {
  ATM atm;
  Account account;
  CashDispenser cashDispenser;

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

    cashDispenser = new CashDispenser(10000);
    atm = new ATM("1", bank, "1111", new Session(), new Display(), new Keyboard(), cashDispenser);
  }

  // Se prueba solo con valores positivos, en este punto los valores ya están validados por clases
  // auxiliares
  @ParameterizedTest
  @ValueSource(doubles = {1000, 5000, 8000})
  void depositTest(double amount) {
    DepositCashTransaction deposit = new DepositCashTransaction(atm);
    deposit.process(amount, cashDispenser, account);
    Assertions.assertEquals(20000 + amount, account.getBalance());
    Assertions.assertEquals(10000 + amount, atm.getCashDispenser().getBalance());
  }
}