package atm.transactions;

import atm.ATM;

public class CheckATMBalanceTransaction implements ITransaction {
  private ATM atm;

  public CheckATMBalanceTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    double balance = atm.getCashDispenser().getBalance();
    atm.getDisplay().showATMBalance(balance);
    return true;
  }
}
