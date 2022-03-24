package atm;

public class InputValidator {

  private InputValidator() {}

  public static boolean checkAccountNumber(String accountNumber) {
    if (accountNumber.length() != 20) return false;
    for (int i = 0; i < accountNumber.length(); i++) {
      char character = accountNumber.charAt(i);
      if (character < '0' || character > '9') return false;
    }
    return true;
  }

  public static boolean checkPinNumber(String pinNumber) {
    if (pinNumber.length() != 4) return false;
    for (int i = 0; i < pinNumber.length(); i++) {
      char character = pinNumber.toUpperCase().charAt(i);
      if (character < '0' || character > '9') return false;
    }
    return true;
  }

  public static boolean checkValidChoice(int choiceNumber, int amountOfOptions) {
    return choiceNumber >= 1 && choiceNumber <= amountOfOptions;
  }

  public static boolean checkValidAmount(double amount) {
    return amount > 0;
  }
}
