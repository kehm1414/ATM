import lombok.Getter;

import java.util.Arrays;

/* Clase ATM
*  Se le pasa un banco como argumento para tener acceso a su "base de datos" de cuentas
*  Está compuesto por:
*  Un Controlador de sesiones "session" que maneja el inicio, ejecución (lo que se muestra al entrar) y salida de sesion
*  Un display que se encargará de mostrar los mensajes en pantalla (en general son métodos simples pero algunos son
*       repetitivos así que por eso se trasladó a otra clase para reutilizar código.
*  Un teclado o procesador de entrada que se encarga de esperar y procesar la entrada de datos del usuario
*  CashDispenser (hasta ahora no implementado) para manejar (entrada, salida y balance) la cantidad de dinero que hay en el cajero
*  */
@Getter
public class ATM {
    final private String ID;
    final private Bank bank;
    final private String adminPin;
    final private Session session;
    final private Display display;
    final private Keyboard keyboard;
    final private CashDispenser cashDispenser;


    public ATM(String ID, Bank bank, String adminPin,double initialCash) {
        this.ID = ID;
        this.bank = bank;
        this.adminPin = adminPin;
        this.session = new Session(bank, this);
        this.display = new Display();
        this.keyboard = new Keyboard();
        this.cashDispenser = new CashDispenser(initialCash);
    }

    // Inicia el cajero, muestra un mensaje de inicio y el menú inicial: Iniciar sesión, Administrar, Salir
    // Si el usuario presiona "1", procede al inicio de sesión como cliente.
    // Si el usuario presiona "2", procede al inicio de sesión como administrador
    // Si el usuario presiona "3", el programa termina
    public void start(){
        display.welcomeMessage(bank.getName());
        display.showOptions("Please, insert a number corresponding to a valid option",
                            Arrays.asList("Login", "Administrate", "Exit & Shutdown"));

        switch (keyboard.getChoice(3)) {
            case 1 -> session.login();
            case 2 -> session.adminLogin();
            case 3 -> shutdown();
        }
    }

    public void shutdown(){
        display.shutdownMessage();
    }
}
