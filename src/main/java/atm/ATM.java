package atm;

import atm.atmcomponents.CashDispenser;
import atm.atmcomponents.Display;
import atm.atmcomponents.Keyboard;
import atm.atmcomponents.Session;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
public class ATM {
    private final String id;
    private final Bank bank;
    private final Session session;
    private final Display display;
    private final Keyboard keyboard;
    private final CashDispenser cashDispenser;
    @Setter
    private String adminPin;


    public ATM(String id, Bank bank, String adminPin, Session session, Display display, Keyboard keyboard, CashDispenser cashDispenser) {
        this.id = id;
        this.bank = bank;
        this.adminPin = adminPin;
        this.session = session;
        this.session.setAtm(this);
        this.display = display;
        this.keyboard = keyboard;
        this.cashDispenser = cashDispenser;
    }

    public void start(){
        display.welcomeMessage(bank.getName());
        display.showOptions("Please, insert a number corresponding to a valid option",
                            Arrays.asList("Login", "Administrate", "Exit & Shutdown"));

        switch (keyboard.getChoice(3)) {
            case 1 -> session.login();
            case 2 -> session.adminLogin();
            case 3 -> shutdown();
            default -> System.out.println("Invalid choice");
        }
    }

    public void shutdown(){
        display.shutdownMessage();
    }
}
