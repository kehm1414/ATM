package atm.accounttypes;

import atm.ATM;
import atm.transactionscollection.ITransactionsCollection;

/* Contrato que oblica que las cuentas de banco tengan este método
para poder obtener las transacciones de ellas*/
public interface IATMTransactional {
  ITransactionsCollection getATMTransactionsAvailable(ATM atm);
}
