import lombok.Getter;

import java.util.Arrays;

/* Session se encarga de manejar el inicio de sesión, lo que sucede durante, y la salida de sesión.*/

@Getter
public class Session {
    private Account activeAccount;
    private Bank bank;
    private ATM atm;
    private boolean isConnected;


    public Session(Bank bank, ATM atm) {
        this.activeAccount = null;
        this.bank = bank;
        this.atm = atm;
        this.isConnected = false;
    }

    // Cuando login() se llama sin parámetros, pide los datos (cuenta y pin) al usuario y
    // se lo pasamos al método sobrecargado login para que realice la conexión
    public void login(){
        atm.getDisplay().askAccountNumber();
        String accountNumber = atm.getKeyboard().getAccountNumberInput();
        atm.getDisplay().askPinNumber();
        String pinNumber = atm.getKeyboard().getPin();
        login(accountNumber, pinNumber);
    }


    // Cuando login se llama con numero de cuenta y pin, procede a intentar hacer la conexión.
    public void login(String accountNumber, String pin){
        for(Account account: this.bank.getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber) && account.getPin().equals(pin)) {
                this.activeAccount = account;
                this.isConnected = true;
                atm.getDisplay().welcomeUser(this.activeAccount.getOwnerName());
                process();
                return;
            }
        }
        // Si no se encuentran la cuenta y el pin en el banco, muestra un mensaje de error y vuelve a pedir
        // los datos al usuario.
        if(!isConnected){
            atm.getDisplay().loginBadCredentials();
            login();
        }

    }

    // Ya habiendo iniciado session, muestra las opciones disponibles para el cliente, elige y procesa.
    public void process(){
        this.activeAccount
                .getATMTransactionsAvailable(atm)
                .chooseTransaction()
                .process();
        if(isConnected){
            atm.getDisplay().showOptions("¿Would you like to try another transaction? (If you choose \"No\" you will be logged out)", Arrays.asList("Yes", "No"));
            int choice = atm.getKeyboard().getChoice(2);
            if(choice == 1){
                process();
            } else {
                logout();
            }
        }

    }

    public void logout(){
        atm.getDisplay().loggedOutMessage();
        this.activeAccount = null;
        this.isConnected = false;
        atm.start();
    }

    public void adminLogin(){
        atm.getDisplay().askPinNumber();
        String pinNumber = atm.getKeyboard().getPin();
        adminLogin(pinNumber);
    }

    public void adminLogin(String pinNumber){
        if(pinNumber.equals(atm.getAdminPin())){
            new AdminTransactionsCollection(atm).chooseTransaction().process();
        } else {
            atm.getDisplay().loginBadCredentials();
            adminLogin();
        }
    }
}

//Considerar separar los administradores de las cuentas de clientes.
//El menú de inicio podría incluir al final una opción relacionada al administrador
//Quien elija esa opción deberá ingresar un código para poder acceder a dichas funciones
//De esta manera, las cuentas y los diferentes tipos (si aplican: ahorro, corriente)
//        podrán tener el mismo tipo de opciones pero con diferente implementación (comisiones, límites, etc)
//De haber diferencias en transacciones (algun tipo de cuenta limitada), identificar si las interfaces ayudarían
