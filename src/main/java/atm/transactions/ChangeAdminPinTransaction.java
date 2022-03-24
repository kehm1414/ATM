package atm.transactions;

import atm.ATM;

import java.util.logging.Logger;

public class ChangeAdminPinTransaction implements ITransaction {
  private static final Logger LOGGER = Logger.getLogger(ChangeAdminPinTransaction.class.getName());

  private ATM atm;

  public ChangeAdminPinTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {

    if(!checkIfUserRemembersOldPin(3)){
      atm.getDisplay().pinForgottenLogout();
      atm.getSession().logout();
      return false;
    }

    atm.getDisplay().askNewPin();
    process(atm.getKeyboard().getPin());
    atm.getDisplay().successfulPinChange();
    System.out.println("The new ATM's pin number is: " + atm.getAdminPin());
    return true;
  }

  public void process(String newPin) {
    atm.setAdminPin(newPin);
    LOGGER.info("Admin Pin has been changed.");
  }

  public boolean checkIfUserRemembersOldPin(int tries){
    while (tries > 0) {
      atm.getDisplay().askPreviousPin();
      String oldPin = atm.getKeyboard().getPin();
      if (oldPin.equals(atm.getAdminPin())) break;
      tries--;
      atm.getDisplay().pinDoesntMatch(tries);
    }
    return tries>0;
  }

}
