package atm.transactions;

import atm.ATM;
import atm.EmailSender;
import atm.accounttypes.Account;
import atm.receiptemitters.ChangePinReceiptEmitter;
import atm.receiptemitters.ReceiptEmitter;

import java.time.LocalDate;

public class ChangePinTransaction implements ITransaction {
  private ATM atm;

  public ChangePinTransaction(ATM atm) {
    this.atm = atm;
  }

  @Override
  public boolean process() {
    Account account = atm.getSession().getActiveAccount();

    // almacenamos el  pin de la session actual
    String pin = account.getPin();

    // Se le pide a usuario colocar su pin antiguo, tiene 3 intentos, si falla se le sacará de sesión
    int tries = 3;

    while(tries > 0){
      // Pide ingresar pin
      atm.getDisplay().askChangePin();
      // almacenamos el pin ingresado
      String oldPin = atm.getKeyboard().getPin();
      // validamos que el pin = al de la session actual
      if(oldPin.equals(pin)){
        break;
      }
      tries--;
      atm.getDisplay().pinDoesntMatch(tries);
    }

    // Si el usuario no introduce bien su antigua contraseña en los tres intentos, sacar de sesión
    if(tries == 0) {
      atm.getDisplay().pinForgottenLogout();
      atm.getSession().logout();
      return false;
    }

    // pedimos ingresar nuevo pin
    atm.getDisplay().changePin();
    // almacenamos el pin nuevo ingresado
    // Ya el keyboard implementa validación de datos utilizando la clase InputValidator
    String newPin = atm.getKeyboard().getPin();


    // asignamos pin por medio de setPin
    account.setPin(newPin);
    atm.getDisplay().okChangePin();
    // Imprimir mensaje de cambio de pin
    System.out.println("su nuevo pin es: " + atm.getSession().getActiveAccount().getPin());

    // Emit receipt
    ReceiptEmitter receipt =
            new ChangePinReceiptEmitter(
                atm.getBank().getName(),
                account.getOwnerName(),
                LocalDate.now().toString(),
                "Change Pin",
                account.getAccountNumber(),
                String.valueOf(newPin),
                String.valueOf((int) Math.ceil(Math.random() * 1000000)));
    String receiptLocation = "./src/main/resources/receipt.pdf";
    receipt.toPDF(receiptLocation);
    EmailSender.send(
            "An operation has been made in one of our ATM's",
            "Hello " + account.getOwnerName()  +
                    "      Dear Customer: Please be informed that your PIN has been updated. If it has not, please contact xxxx-xxxx immediately."  ,
            receiptLocation,
            account.getEmail());

    return true;
  }

}
