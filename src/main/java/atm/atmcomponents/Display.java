package atm.atmcomponents;

import java.util.List;

/* La "pantalla" del cajero
 *  Se utiliza para mostrar los mensajes en la aplicación */

public class Display {

  public void welcomeMessage(String bankName) {
    System.out.println("================================================");
    System.out.println("WELCOME TO " + bankName.toUpperCase() + "'S BANK ATM");
    System.out.println("================================================");
  }

  public void welcomeUser(String username) {
    System.out.println("Welcome " + username + "!");
  }

  public void showOptions(String petition, List<String> options) {
    System.out.println(petition);
    for (int i = 0; i < options.size(); i++) {
      System.out.println((i + 1) + "." + options.get(i));
    }
  }

  // INPUT ASKING MESSAGES

  public void askAccountNumber() {
    System.out.println("Please insert your account number:");
  }

  public void askTransferAmount() {
    System.out.println("Please, introduce the amount to transfer: ");
  }

  public void askAccountToDeposit() {
    System.out.println("Insert the account number to deposit:");
  }

  public void askPinNumber() {
    System.out.println("Please insert your pin number:");
  }

  public void askDepositAmount() {
    System.out.println("Please, introduce the amount to deposit: ");
  }

  public void askWithdrawAmount() {
    System.out.println("Please, introduce the amount to withdraw: ");
  }

  public void askPreviousPin() {
    System.out.println("Please enter previous pin: ");
  }

  public void askNewPin() {
    System.out.println("Please enter new pin: ");
  }

  // ERROR MESSAGES

  public void accountNotFound() {
    System.out.println("The account number you've introduced hasn't been found");
    System.out.println("Please, try again");
  }

  public void loginBadCredentials() {
    System.out.println("The credentials you've introduced are invalid , please try again.");
  }

  public void notEnoughATMBalance() {
    System.out.println(
        "We're sorry, at the moment the ATM doesn't have enough cash to process your transaction");
    System.out.println("Please, try another time or withdraw a smaller amount");
  }

  public void notEnoughAccountBalance() {
    System.out.println("You don't have enough funds to make this transaction");
  }

  public void noTransferToSameAccount() {
    System.out.println("You can't transfer to the same account you're transferring from");
  }

  public void pinDoesntMatch(int tries){
    System.out.println("The pin number you've introduced does not match your actual pin.");
    System.out.println("You have " + tries + " tries left.");
  }

  public void pinForgottenLogout(){
    System.out.println("It seems you've forgotten your pin.");
    System.out.println("For security reasons you will be logged out.");
  }

  // NOTIFICATION MESSAGES

  public void loggedOutMessage() {
    System.out.println("You have been logged out");
  }

  public void showBalance(double balance) {
    System.out.println("Your current balance is: $" + String.format("%.2f", balance));
  }

  public void showATMBalance(double balance) {
    System.out.println("Current balance of this ATM is: $" + String.format("%.2f", balance));
  }

  public void shutdownMessage() {
    System.out.println("The system will shutdown...");
    System.out.println("Thanks for preferring us. Until next time!");
  }

  public void successfulTransaction() {
    System.out.println("Your transaction has been made successfully");
  }

  public void successfulPinChange() {
    System.out.println("Your pin change is successful");
  }
}
