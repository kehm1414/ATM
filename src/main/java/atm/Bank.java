package atm;

import atm.accounttypes.Account;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

// Esta clase almacena las cuentas de banco, funcionar√° como nuestra base de datos.

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
}
