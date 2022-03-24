package atm.transactions;

import atm.ATM;
import atm.EmailSender;
import atm.accounttypes.Account;
import atm.receiptemitters.ChangePinReceiptEmitter;
import atm.receiptemitters.ReceiptEmitter;

public class ChangePinTransaction implements ITransaction {
  private ATM atm;

  public ChangePinTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    Account account = atm.getSession().getActiveAccount();

    if (!checkIfUserRemembersOldPin(3, account.getPin())) {
      atm.getDisplay().pinForgottenLogout();
      atm.getSession().logout();
      return false;
    }

    atm.getDisplay().askNewPin();
    process(account, atm.getKeyboard().getPin());
    atm.getDisplay().successfulPinChange();
    System.out.println("Your new pin number is: " + account.getPin());

    // Emit receipt
    String receiptLocation = "./src/main/resources/receipt.pdf";
    ReceiptEmitter receipt = emitReceipt(account, account.getPin());
    receipt.toPDF(receiptLocation);
    sendReceipt(account.getOwnerName(), receiptLocation, account.getEmail());
    return true;
  }

  public void process(Account account, String newPin) {
    account.setPin(newPin);
  }

  public boolean checkIfUserRemembersOldPin(int tries, String pin){
    while (tries > 0) {
      atm.getDisplay().askPreviousPin();
      String oldPin = atm.getKeyboard().getPin();
      if (oldPin.equals(pin)) break;
      tries--;
      atm.getDisplay().pinDoesntMatch(tries);
    }
    return tries>0;
  }

  public ReceiptEmitter emitReceipt(Account account, String newPin) {
    return new ChangePinReceiptEmitter(
        atm.getBank().getName(),
        account.getOwnerName(),
        "Change Pin",
        account.getAccountNumber(),
        String.valueOf(newPin));
  }

  public void sendReceipt(String name, String receiptLocation, String email) {
    EmailSender.send(
        "An operation has been made in one of our ATM's",
        "Hello "
            + name
            + "      Dear Customer: Please be informed that your PIN has been updated. If it has not, please contact xxxx-xxxx immediately.",
        receiptLocation,
        email);
  }
}
