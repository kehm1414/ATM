package atm;

import atm.accounttypes.Account;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Bank {
  final String name;
  private Set<Account> accounts;

  public Bank(String name) {
    this.name = name;
    this.accounts = new HashSet<>();
  }

  public void addAccount(Account account) {
    accounts.add(account);
  }

  public Account searchAccount(String accountNumber){
    for (Account account : getAccounts()) {
      if (account.getAccountNumber().equals(accountNumber)) return account;
    }
    return null;
  }
}
