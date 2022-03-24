package atm.transactions;

import atm.ATM;
import atm.EmailSender;
import atm.accounttypes.Account;
import atm.atmcomponents.CashDispenser;
import atm.receiptemitters.PersonalTransactionReceiptEmitter;
import atm.receiptemitters.ReceiptEmitter;

import java.util.Arrays;

public class DepositCashTransaction implements ITransaction {
  private ATM atm;

  public DepositCashTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    Account account = atm.getSession().getActiveAccount();
    CashDispenser cashDispenser = atm.getCashDispenser();

    atm.getDisplay().askDepositAmount();
    double amount = atm.getKeyboard().getAmount();

    atm.getDisplay()
        .showOptions("Amount: " + amount + ". This is correct?", Arrays.asList("Yes", "No"));
    int choice = atm.getKeyboard().getChoice(2);

    if (choice == 1) {
      process(amount, cashDispenser, account);
      atm.getDisplay().successfulTransaction();

      // Emit receipt
      String receiptLocation = "./src/main/resources/receipt.pdf";
      ReceiptEmitter receipt = emitReceipt(account, amount);
      receipt.toPDF(receiptLocation);
      sendReceipt(account.getOwnerName(), receiptLocation, account.getEmail());
      return true;
    } else {
      return process();
    }
  }

  public void process(double amount, CashDispenser cashDispenser, Account account) {
    cashDispenser.increaseBalance(amount);
    account.setBalance(account.getBalance() + amount);
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

  public ReceiptEmitter emitReceipt(Account account, double amount) {
    return new PersonalTransactionReceiptEmitter(
        atm.getBank().getName(),
        account.getOwnerName(),
        "Deposit Cash",
        account.getAccountNumber(),
        String.valueOf(amount));
  }
}
