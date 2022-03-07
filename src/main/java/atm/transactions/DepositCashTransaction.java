package atm.transactions;

import atm.ATM;
import atm.EmailSender;
import atm.accounttypes.Account;
import atm.atmcomponents.CashDispenser;
import atm.receiptemitters.PersonalTransactionReceiptEmitter;
import atm.receiptemitters.ReceiptEmitter;

import java.time.LocalDate;
import java.util.Arrays;

public class DepositCashTransaction implements ITransaction {
  private ATM atm;

  public DepositCashTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    // acceso a los valores de referencia
    Account account = atm.getSession().getActiveAccount();
    CashDispenser cashDispenser = atm.getCashDispenser();

    // pregunta al usuario el monto a depositar
    atm.getDisplay().askDepositAmount();
    double amount = atm.getKeyboard().getAmount();

    atm.getDisplay()
        .showOptions("Amount: " + amount + ". This is correct?", Arrays.asList("Yes", "No"));
    int choice = atm.getKeyboard().getChoice(2);

    if (choice == 1) {
      //Metodo para el deposito en cajero y cuenta del usuario
      cashDispenser.setBalance(cashDispenser.getBalance() + amount);
      account.setBalance(account.getBalance() + amount);
      atm.getDisplay().successfulTransaction();

      // Emit receipt
      ReceiptEmitter receipt =
          new PersonalTransactionReceiptEmitter(
              atm.getBank().getName(),
              account.getOwnerName(),
              LocalDate.now().toString(),
              "Deposit Cash",
              account.getAccountNumber(),
              String.valueOf(amount),
              String.valueOf((int) Math.ceil(Math.random() * 1000000)));
      String receiptLocation = "./src/main/resources/receipt.pdf";
      receipt.toPDF(receiptLocation);
      EmailSender.send(
          "An operation has been made in one of our ATM's",
          "Hello "
              + account.getOwnerName()
              + ", we've attached a receipt according your last transaction in our ATM's. \n\n"
              + "If you don't recognize this transaction, please report it to one of our contact numbers. We're are pleased to serve you!",
          receiptLocation,
          account.getEmail());
      return true;
    } else {
      return process();
    }
  }
}
