package atm.atmcomponents;

import atm.ATM;
import atm.accounttypes.Account;
import atm.transactionscollection.AdminTransactionsCollection;
import atm.transactionscollection.ITransactionsCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/* ATM.Session se encarga de manejar el inicio de sesión, lo que sucede durante, y la salida de sesión.*/

@Getter
@Setter
public class Session {
  private Account activeAccount;
  private ATM atm;
  private boolean isConnected;

  public Session() {
    this.activeAccount = null;
    this.atm = null;
    this.isConnected = false;
  }

  //     INGRESO DE SESIÓN PARA CLIENTES CON CUENTA BANCARIA

  // Cuando login() se llama sin parámetros, pide los datos (cuenta y pin) al usuario y
  // se lo pasamos al método sobrecargado login para que realice la conexión
  public void login() {
    atm.getDisplay().askAccountNumber();
    String accountNumber = atm.getKeyboard().getAccountNumber();
    atm.getDisplay().askPinNumber();
    String pinNumber = atm.getKeyboard().getPin();
    login(accountNumber, pinNumber);
  }

  // Cuando login se llama con numero de cuenta y pin, procede a intentar hacer la conexión.
  public void login(String accountNumber, String pin) {
    for (Account account : atm.getBank().getAccounts()) {
      if (account.getAccountNumber().equals(accountNumber) && account.getPin().equals(pin)) {
        this.activeAccount = account;
        this.isConnected = true;
        atm.getDisplay().welcomeUser(this.activeAccount.getOwnerName());
        ITransactionsCollection transactionsSpecificToAccount =
            this.activeAccount.getATMTransactionsAvailable(atm);
        process(transactionsSpecificToAccount);
        return;
      }
    }
    // Si no se encuentran la cuenta y el pin en el banco, muestra un mensaje de error y vuelve a
    // pedir
    // los datos al usuario.
    if (!isConnected) {
      atm.getDisplay().loginBadCredentials();
      login();
    }
  }

  //        INICIO DE SESIÓN PARA ADMINISTRADOR

  public void adminLogin() {
    atm.getDisplay().askPinNumber();
    String pinNumber = atm.getKeyboard().getPin();
    adminLogin(pinNumber);
  }

  public void adminLogin(String pinNumber) {
    if (pinNumber.equals(atm.getAdminPin())) {
      this.isConnected = true;
      ITransactionsCollection transactionsSpecificToAccount = new AdminTransactionsCollection(atm);
      process(transactionsSpecificToAccount);
    } else {
      atm.getDisplay().loginBadCredentials();
      adminLogin();
    }
  }

  // Ya habiendo iniciado session, muestra las opciones disponibles para el cliente, elige y
  // procesa.
  public void process(ITransactionsCollection transactionsSpecificToAccount) {
    transactionsSpecificToAccount.chooseTransaction().process();
    if (isConnected) {
      atm.getDisplay()
          .showOptions(
              "¿Would you like to try another transaction? (If you choose \"No\" you will be logged out)",
              Arrays.asList("Yes", "No"));
      int choice = atm.getKeyboard().getChoice(2);
      if (choice == 1) {
        process(transactionsSpecificToAccount);
      } else {
        logout();
      }
    }
  }

  public void logout() {
    atm.getDisplay().loggedOutMessage();
    this.activeAccount = null;
    this.isConnected = false;
    atm.start();
  }
}
