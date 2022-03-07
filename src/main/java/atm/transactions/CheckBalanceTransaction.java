package atm.transactions;

import atm.*;
import atm.receiptemitters.CheckBalanceReceiptEmitter;
import atm.receiptemitters.ReceiptEmitter;

import java.time.LocalDate;

public class CheckBalanceTransaction implements ITransaction {
  private ATM atm;

  public CheckBalanceTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    double balance = atm.getSession().getActiveAccount().getBalance();
    atm.getDisplay().showBalance(balance);

    // Emit receipt
    ReceiptEmitter receipt =
        new CheckBalanceReceiptEmitter(
            atm.getBank().getName(),
            atm.getSession().getActiveAccount().getOwnerName(),
            LocalDate.now().toString(),
            "Check Balance",
            atm.getSession().getActiveAccount().getAccountNumber(),
            String.valueOf(balance),
            String.valueOf((int) Math.ceil(Math.random() * 1000000)));
    String receiptLocation = "./src/main/resources/receipt.pdf";
    receipt.toPDF(receiptLocation);
    EmailSender.send(
        "An operation has been made in one of our ATM's",
        "Hello "
            + atm.getSession().getActiveAccount().getOwnerName()
            + ", we've attached a receipt according your last transaction in our ATM's. \n\n"
            + "If you don't recognize this transaction, please report it to one of our contact numbers. We're are pleased to serve you!",
        receiptLocation,
        atm.getSession().getActiveAccount().getEmail());

    return true;
  }
}
