package atm.transactions;

import atm.ATM;
import atm.atmcomponents.CashDispenser;

import java.util.Arrays;

public class SupplyCashTransaction implements ITransaction {
  private ATM atm;

  public SupplyCashTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    CashDispenser cashDispenser = atm.getCashDispenser();

    atm.getDisplay().askDepositAmount();
    double amount = atm.getKeyboard().getAmount();

    atm.getDisplay()
        .showOptions("Amount: " + amount + ". This is correct?", Arrays.asList("Yes", "No"));
    int choice = atm.getKeyboard().getChoice(2);

    if (choice == 1) {
      process(amount, cashDispenser);
      atm.getDisplay().successfulTransaction();
      return true;
    } else {
      return process();
    }
  }

  public void process(double amount, CashDispenser cashDispenser) {
    cashDispenser.increaseBalance(amount);
  }
}
