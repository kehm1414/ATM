package atm.transactionscollection;

import atm.transactions.ITransaction;

public interface ITransactionsCollection {
  ITransaction chooseTransaction();
}
