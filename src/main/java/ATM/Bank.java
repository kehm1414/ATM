package ATM;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

// Esta clase almacena las cuentas de banco, funcionará como nuestra base de datos.

@Getter
public class Bank {
    final String name;
    private Set<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts =new HashSet<>();
    }

    public boolean addAccount(Account account){
        accounts.add(account);
        return true;
    }
}
