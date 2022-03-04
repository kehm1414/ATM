import java.util.Scanner;

/* Clase que se encarga de procesar la entrada de texto */

public class Keyboard {
    Scanner scanner = new Scanner(System.in);

    public int getChoice(int amountOfOptions){
        int choice = 0;
        try{
            choice = Integer.parseInt(scanner.nextLine());
            if(choice<1 || choice>amountOfOptions){
                throw new Exception();
            }
        } catch (Exception e){
            System.out.println("Invalid Option. Please enter a valid option:");
            return getChoice(amountOfOptions);
        }

        return choice;
    }

    public String getAccountNumberInput(){
        String accountNumber = scanner.nextLine();
        if(!InputValidator.checkAccountNumber(accountNumber)){
            System.out.println("Invalid account number. Please enter a valid account number");
            accountNumber = getAccountNumberInput();
        }
        return accountNumber;
    }

    public String getPin(){
        String pinNumber = scanner.nextLine();
        if(!InputValidator.checkPinNumber(pinNumber)){
            System.out.println("Invalid pin number. Please enter a valid account number");
            getPin();
        }
        return pinNumber;
    }
}
