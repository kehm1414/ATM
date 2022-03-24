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

class TransferFundsTransactionTest {
  ATM atm;
  Account fromAccount;
  Account toAccount;

  @BeforeEach
  void initialize() {
    Bank bank = new Bank("Mercantil");
    fromAccount =
        new Account(
            "Jose Perez", "01234567890123456789", "1234", "joseperez@gmail.com", 20000);
    toAccount =
        new Account(
            "Maria Mendez",
            "00110011001100110011",
            "0011",
                "mariamendez@gmail.com",
            5000);

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
  void transfer() {
    TransferFundsTransaction transfer = new TransferFundsTransaction(atm);
    transfer.process(fromAccount, toAccount, 5000);
    Assertions.assertEquals(15000, fromAccount.getBalance());
    Assertions.assertEquals(10000, toAccount.getBalance());
  }
}
