import java.util.List;

/* La "pantalla" del cajero
*  Se utiliza para mostrar los mensajes en la aplicación */

public class Display {

    public void welcomeMessage(String bankName){
        System.out.println("================================================");
        System.out.println("WELCOME TO " + bankName.toUpperCase() + "'S BANK ATM");
        System.out.println("================================================");
    }


    public void showOptions(String petition, List<String> options){
        System.out.println(petition);
        for(int i=0; i<options.size(); i++){
            System.out.println((i+1) + "." + options.get(i));
        }
    }

    public void askAccountNumber(){
        System.out.println("Please insert your account number:");
    }

    public void askPinNumber(){
        System.out.println("Please insert your pin number:");
    }

    public void welcomeUser(String username){
        System.out.println("Welcome " + username + "!");
    }

    public void loginBadCredentials(){
        System.out.println("The credentials you've introduced are invalid , please try again.");
    }

    public void loggedOutMessage(){
        System.out.println("You have been logged out");
    }

    public void showBalance(double balance){
        System.out.println("Your current balance is: " + balance);
    }

    public void shutdownMessage(){
        System.out.println("The system will shutdown...");
        System.out.println("Thanks for preferring us. Until next time!");
    }


}
