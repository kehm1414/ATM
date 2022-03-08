package atm.transactions;

import atm.*;
import atm.accounttypes.Account;
import atm.atmcomponents.CashDispenser;
import atm.receiptemitters.PersonalTransactionReceiptEmitter;
import atm.receiptemitters.ReceiptEmitter;

import java.time.LocalDate;
import java.util.Random;

public class WithdrawCashTransaction implements ITransaction {
  private ATM atm;

  public WithdrawCashTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    // Easy data access to reference values
    Account account = atm.getSession().getActiveAccount();
    CashDispenser cashDispenser = atm.getCashDispenser();

    // Ask user for amount to withdraw
    atm.getDisplay().askWithdrawAmount();
    double amount = atm.getKeyboard().getAmount();

    // Verify if withdrawal amount is possible for atm and active account
    if (amount > cashDispenser.getBalance()) {
      atm.getDisplay().notEnoughATMBalance();
    } else if (amount > account.getBalance()) {
      atm.getDisplay().notEnoughAccountBalance();
    } else {
      cashDispenser.decreaseBalance(amount);
      account.setBalance(account.getBalance() - amount);

      // Emit receipt
      Random reference = new Random();
      ReceiptEmitter receipt =
          new PersonalTransactionReceiptEmitter(
              atm.getBank().getName(),
              account.getOwnerName(),
              LocalDate.now().toString(),
              "Withdraw Cash",
              account.getAccountNumber(),
              String.valueOf(amount),
              String.valueOf(reference.nextInt(1000000)));
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
    }
    return true;
  }
}
