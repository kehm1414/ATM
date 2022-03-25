package atm.accounttypes;

import atm.ATM;
import atm.transactionscollection.ITransactionsCollection;
import atm.transactionscollection.OnlyCashTransactionsCollection;

public class OnlyCashAccount extends Account implements IATMTransactional {

  public OnlyCashAccount(
      String ownerName, String accountNumber, String pin, String email, double balance) {
    super(ownerName, accountNumber, pin, email, balance);
  }

  @Override
  public ITransactionsCollection getATMTransactionsAvailable(ATM atm) {
    return new OnlyCashTransactionsCollection(atm);
  }
}
