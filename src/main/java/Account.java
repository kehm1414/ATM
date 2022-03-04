import lombok.Getter;

@Getter
public class Account implements IATMTransactional {
    private String ownerName;
    private final String accountNumber;
    private String pin;
    private String type;
    private double balance;

    public Account(String ownerName, String accountNumber, String pin, String type, double balance) {
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.type = type;
        this.balance = balance;
    }

    // Este método nos devolverá la colección de transacciones disponibles para este tipo de cuenta
    public ITransactionsCollection getATMTransactionsAvailable(ATM atm){
        return new BaseClientTransactionsCollection(atm);
    }
}
