package atm;

import atm.accounttypes.Account;
import atm.accounttypes.OnlyCashAccount;
import atm.atmcomponents.CashDispenser;
import atm.atmcomponents.Display;
import atm.atmcomponents.Keyboard;
import atm.atmcomponents.Session;

public class Main {
  public static void main(String[] args) {

    // Creamos el banco y algunas cuentas de prueba
    Bank bank = new Bank("Mercantil");
    bank.addAccount(
        new Account(
            "Kevin Hernandez",
            "01234567890123456789",
            "1234",
            "Client",
            "kvkevinhz@gmail.com",
            20000));
    bank.addAccount(
        new OnlyCashAccount(
            "Jose Perez",
            "00110011001100110011",
            "0011",
            "OnlyCashClient",
            "joseperez@gmail.com",
            10000));


    // Inicializamos nuestro cajero
    Display display = new Display();
    Keyboard keyboard = new Keyboard();
    CashDispenser cashDispenser = new CashDispenser(1000000);
    Session session = new Session();
    ATM atm = new ATM("1", bank, "8888", session, display, keyboard, cashDispenser);
    atm.start();
  }
}
