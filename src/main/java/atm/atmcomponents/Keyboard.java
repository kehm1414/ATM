package atm.atmcomponents;

import atm.InputValidator;

import java.util.Scanner;

/* Clase que se encarga de procesar la entrada de texto */

public class Keyboard {
  private final Scanner scanner = new Scanner(System.in);

  public int getChoice(int amountOfOptions) {
    int choice;
    try {
      choice = Integer.parseInt(scanner.nextLine());
      if (InputValidator.checkValidChoice(choice, amountOfOptions)) {
        return choice;
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      System.out.println("Invalid Option. Please enter a valid option:");
      return getChoice(amountOfOptions);
    }
  }

  public double getAmount() {
    double amount;
    try {
      amount = Double.parseDouble(scanner.nextLine());
      if (!InputValidator.checkValidAmount(amount)) {
        throw new Exception();
      }
    } catch (Exception e) {
      System.out.println("Invalid input. Please enter a valid amount:");
      amount = getAmount();
    }
    return amount;
  }

  public String getAccountNumber() {
    String accountNumber = scanner.nextLine();
    if (!InputValidator.checkAccountNumber(accountNumber)) {
      System.out.println("Invalid account number. Please enter a valid account number");
      accountNumber = getAccountNumber();
    }
    return accountNumber;
  }

  public String getPin() {
    String pinNumber = scanner.nextLine();
    if (!InputValidator.checkPinNumber(pinNumber)) {
      System.out.println("Invalid pin number. Please enter a valid pin number");
      pinNumber = getPin();
    }
    return pinNumber;
  }
}
