package atm.transactions;

import atm.*;
import atm.accounttypes.Account;
import atm.receiptemitters.ReceiptEmitter;
import atm.receiptemitters.TransferFundsReceiptEmitter;

import java.time.LocalDate;

public class TransferFundsTransaction implements ITransaction {
  private ATM atm;

  public TransferFundsTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    // Acceso a los valores de referencia
    Account fromAccount = atm.getSession().getActiveAccount();
    Account toAccount = null;

    atm.getDisplay().askAccountToDeposit();
    String accountNumber = atm.getKeyboard().getAccountNumber();

    // Checks if account number entered coincides with database then reassign "toAccount"
    for (Account account : atm.getBank().getAccounts()) {
      if (account.getAccountNumber().equals(accountNumber)) {
        toAccount = account;
      }
    }
    // If account not found, try again, else, continue
    if (toAccount == null) {
      atm.getDisplay().accountNotFound();
      return process();
    }
    if (toAccount.getAccountNumber().equals(fromAccount.getAccountNumber())) {
      atm.getDisplay().noTransferToSameAccount();
      return process();
    }

    // Pedir al usuario la cantidad a transferir
    atm.getDisplay().askTransferAmount();
    double amount = atm.getKeyboard().getAmount();

    // Verifique si el monto de la transferencia es posible para la cuenta activa
    if (amount > fromAccount.getBalance()) {
      atm.getDisplay().notEnoughAccountBalance();
    } else {
      fromAccount.setBalance(fromAccount.getBalance() - amount);
      toAccount.setBalance(toAccount.getBalance() + amount);
      atm.getDisplay().successfulTransaction();

      // Emit receipt
      ReceiptEmitter receipt =
          new TransferFundsReceiptEmitter(
              atm.getBank().getName(),
              fromAccount.getOwnerName(),
              LocalDate.now().toString(),
              "Transfer of funds",
              toAccount.getAccountNumber(),
              String.valueOf(amount),
              String.valueOf((int) Math.ceil(Math.random() * 1000000)));
      String receiptLocation = "./src/main/resources/receipt.pdf";
      receipt.toPDF(receiptLocation);
      EmailSender.send(
          "An operation has been made in one of our ATM's",
          "Hello "
              + fromAccount.getOwnerName()
              + ", we've attached a receipt according your last transaction in our ATM's. \n\n"
              + "If you don't recognize this transaction, please report it to one of our contact numbers. We're are pleased to serve you!",
          receiptLocation,
          fromAccount.getEmail());
    }
    return true;
  }
}
