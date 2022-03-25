package atm.accounttypes;

import atm.ATM;
import atm.transactionscollection.BaseClientTransactionsCollection;
import atm.transactionscollection.ITransactionsCollection;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Account implements IATMTransactional {
  private String ownerName;
  private final String accountNumber;
  @Setter private String pin;
  private String email;
  @Setter private double balance;

  public Account(String ownerName, String accountNumber, String pin, String email, double balance) {
    this.ownerName = ownerName;
    this.accountNumber = accountNumber;
    this.pin = pin;
    this.email = email;
    this.balance = balance;
  }

  public ITransactionsCollection getATMTransactionsAvailable(ATM atm) {
    return new BaseClientTransactionsCollection(atm);
  }
}
