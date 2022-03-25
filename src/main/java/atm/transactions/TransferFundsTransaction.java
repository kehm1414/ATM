package atm.transactions;

import atm.ATM;
import atm.EmailSender;
import atm.accounttypes.Account;
import atm.receiptemitters.ReceiptEmitter;
import atm.receiptemitters.TransferFundsReceiptEmitter;

public class TransferFundsTransaction implements ITransaction {
  private ATM atm;

  public TransferFundsTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    Account fromAccount = atm.getSession().getActiveAccount();
    Account toAccount;

    atm.getDisplay().askAccountToDeposit();
    String accountNumber = atm.getKeyboard().getAccountNumber();
    toAccount = atm.getBank().searchAccount(accountNumber);
    if (!isValidDestinationAccount(toAccount, fromAccount.getAccountNumber())) process();

    atm.getDisplay().askTransferAmount();
    double amount = atm.getKeyboard().getAmount();

    if (amount > fromAccount.getBalance()) {
      atm.getDisplay().notEnoughAccountBalance();
    } else {
      process(fromAccount, toAccount, amount);
      atm.getDisplay().successfulTransaction();

      // Emit receipt
      String receiptLocation = "./src/main/resources/receipt.pdf";
      ReceiptEmitter receipt = emitReceipt(fromAccount, toAccount, amount);
      receipt.toPDF(receiptLocation);
      sendReceipt(fromAccount.getOwnerName(), receiptLocation, fromAccount.getEmail());
    }
    return true;
  }

  public void process(Account fromAccount, Account toAccount, double amount) {
    fromAccount.setBalance(fromAccount.getBalance() - amount);
    toAccount.setBalance(toAccount.getBalance() + amount);
  }

  public boolean isValidDestinationAccount(Account account, String originAccountNumber) {
    if (account == null) {
      atm.getDisplay().accountNotFound();
      return false;
    } else if (account.getAccountNumber().equals(originAccountNumber)) {
      atm.getDisplay().noTransferToSameAccount();
      return false;
    }
    return true;
  }

  public ReceiptEmitter emitReceipt(Account fromAccount, Account toAccount, double amount) {
    return new TransferFundsReceiptEmitter(
        atm.getBank().getName(),
        fromAccount.getOwnerName(),
        "Transfer of funds",
        toAccount.getAccountNumber(),
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
