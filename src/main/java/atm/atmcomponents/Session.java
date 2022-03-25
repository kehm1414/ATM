package atm.atmcomponents;

import atm.ATM;
import atm.accounttypes.Account;
import atm.transactionscollection.AdminTransactionsCollection;
import atm.transactionscollection.ITransactionsCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.logging.Logger;

@Getter
@Setter
public class Session {
  private static final Logger LOGGER = Logger.getLogger(Session.class.getName());
  private Account activeAccount;
  private ATM atm;
  private boolean isConnected;

  public Session() {
    this.activeAccount = null;
    this.atm = null;
    this.isConnected = false;
  }

  //     INGRESO DE SESIÓN PARA CLIENTES CON CUENTA BANCARIA

  public void login() {
    atm.getDisplay().askAccountNumber();
    String accountNumber = atm.getKeyboard().getAccountNumber();
    atm.getDisplay().askPinNumber();
    String pinNumber = atm.getKeyboard().getPin();
    login(accountNumber, pinNumber);
  }

  private void login(String accountNumber, String pin) {
    Account account = atm.getBank().searchAccount(accountNumber);
    if (account != null && account.getPin().equals(pin)) {
      this.activeAccount = account;
      this.isConnected = true;
      LOGGER.info("Session started by: " + account.getOwnerName());
      atm.getDisplay().welcomeUser(this.activeAccount.getOwnerName());
      process(this.activeAccount.getATMTransactionsAvailable(atm));
    } else {
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

  private void adminLogin(String pinNumber) {
    if (pinNumber.equals(atm.getAdminPin())) {
      this.isConnected = true;
      LOGGER.info("Session started by Admin");
      process(new AdminTransactionsCollection(atm));
    } else {
      atm.getDisplay().loginBadCredentials();
      adminLogin();
    }
  }

  private void process(ITransactionsCollection transactionsSpecificToAccount) {
    transactionsSpecificToAccount.chooseTransaction().process();
    if (isConnected) {
      if (userWantsAnotherTransaction()) {
        process(transactionsSpecificToAccount);
      } else {
        logout();
      }
    }
  }

  private boolean userWantsAnotherTransaction() {
    atm.getDisplay()
        .showOptions(
            "¿Would you like to try another transaction? (If \"No\" you will be logged out)",
            Arrays.asList("Yes", "No"));
    int choice = atm.getKeyboard().getChoice(2);
    return choice == 1;
  }

  public void logout() {
    atm.getDisplay().loggedOutMessage();
    this.activeAccount = null;
    this.isConnected = false;
    LOGGER.info("Session finished");
    atm.start();
  }
}
