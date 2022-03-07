package atm.accounttypes;

import atm.ATM;
import atm.transactionscollection.ITransactionsCollection;
import atm.transactionscollection.OnlyCashTransactionsCollection;

public class OnlyCashAccount extends Account implements IATMTransactional {

  /* Cuenta de ejemplo para mostrar opciones distintas para distintos tipos de cuenta */
  /* Cuentas de tipo OnlyCash son cuentas que solo pueden retirar y depositar en efectivo
   * No pueden hacer transferencias */
  public OnlyCashAccount(
      String ownerName,
      String accountNumber,
      String pin,
      String type,
      String email,
      double balance) {
    super(ownerName, accountNumber, pin, type, email, balance);
  }

  @Override
  public ITransactionsCollection getATMTransactionsAvailable(ATM atm) {
    return new OnlyCashTransactionsCollection(atm);
  }
}
