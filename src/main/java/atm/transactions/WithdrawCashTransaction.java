package atm.transactions;

import atm.ATM;
import atm.EmailSender;
import atm.accounttypes.Account;
import atm.atmcomponents.CashDispenser;
import atm.receiptemitters.PersonalTransactionReceiptEmitter;
import atm.receiptemitters.ReceiptEmitter;

public class WithdrawCashTransaction implements ITransaction {
  private ATM atm;

  public WithdrawCashTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    Account account = atm.getSession().getActiveAccount();
    CashDispenser cashDispenser = atm.getCashDispenser();

    atm.getDisplay().askWithdrawAmount();
    double amount = atm.getKeyboard().getAmount();

    if (!checkIfTransactionIsPossible(amount, account)) return false;
    process(amount, cashDispenser, account);

    // Emit receipt
    ReceiptEmitter receipt = emitReceipt(account, amount);
    String receiptLocation = "./src/main/resources/receipt.pdf";
    receipt.toPDF(receiptLocation);
    sendReceipt(account.getOwnerName(), receiptLocation, account.getEmail());

    return true;
  }

  public void process(double amount, CashDispenser cashDispenser, Account account) {
    cashDispenser.decreaseBalance(amount);
    account.setBalance(account.getBalance() - amount);
  }

  public boolean checkIfTransactionIsPossible(double amount, Account account) {
    if (amount > atm.getCashDispenser().getBalance()) {
      atm.getDisplay().notEnoughATMBalance();
      return false;
    } else if (amount > account.getBalance()) {
      atm.getDisplay().notEnoughAccountBalance();
      return false;
    }
    return true;
  }

  public ReceiptEmitter emitReceipt(Account account, double amount) {
    return new PersonalTransactionReceiptEmitter(
        atm.getBank().getName(),
        account.getOwnerName(),
        "Withdraw Cash",
        account.getAccountNumber(),
        String.valueOf(amount));
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
