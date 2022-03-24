package atm.transactions;

import atm.ATM;
import atm.EmailSender;
import atm.accounttypes.Account;
import atm.receiptemitters.CheckBalanceReceiptEmitter;
import atm.receiptemitters.ReceiptEmitter;

public class CheckBalanceTransaction implements ITransaction {
  private ATM atm;

  public CheckBalanceTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    Account account = atm.getSession().getActiveAccount();
    atm.getDisplay().showBalance(account.getBalance());

    // Emit receipt
    String receiptLocation = "./src/main/resources/receipt.pdf";
    ReceiptEmitter receipt = emitReceipt(account, account.getBalance());
    receipt.toPDF(receiptLocation);
    sendReceipt(account.getOwnerName(), receiptLocation, account.getEmail());

    return true;
  }

  public ReceiptEmitter emitReceipt(Account account, double balance) {
    return new CheckBalanceReceiptEmitter(
        atm.getBank().getName(),
        account.getOwnerName(),
        "Check Balance",
        account.getAccountNumber(),
        String.valueOf(balance));
  }

  public void sendReceipt(String name, String receiptLocation, String email) {
    EmailSender.send(
        "An operation has been made in one of our ATM's",
        "Hello "
            + name
            + ", we've attached a receipt according your last transaction in our ATM's. \n\n"
            + "If you don't recognize this transaction, please report it to one of our contact numbers. We're are pleased to serve you!",
        receiptLocation,
        email);
  }
}
