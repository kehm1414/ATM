package atm.atmcomponents; // El dispensador de dinero, acá se maneja el stock de dinero dentro del
// cajero.

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashDispenser {
  private double balance;

  public CashDispenser(double initialBalance) {
    this.balance = initialBalance;
  }

  public void increaseBalance(double amount) {
    balance += amount;
  }

  public void decreaseBalance(double amount) {
    if (amount < balance && amount > 0) balance -= amount;
  }
}
