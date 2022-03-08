package atm;

public class InputValidator {

  private InputValidator(){}

  public static boolean checkAccountNumber(String accountNumber) {
    if(accountNumber.length() == 20){
      for(int i = 0; i<accountNumber.length(); i++){
        char character = accountNumber.charAt(i);
        if(character < 48 || character > 57){
          return false;
        }
      }
      return true;
    }
    return false;
  }

  public static boolean checkPinNumber(String pinNumber) {
    if (pinNumber.length() == 4) {
      for (int i = 0; i < pinNumber.length(); i++) {
        char caracter = pinNumber.toUpperCase().charAt(i);
        if ( caracter < 48 || caracter > 57){ // 48 a 57 son los carácteres numéricos 0-9
          return false; // Se ha encontrado un caracter que no es numero
        }
      }
      return true;
    }
    return false;
  }
}
