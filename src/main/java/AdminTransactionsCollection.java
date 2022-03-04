import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminTransactionsCollection implements ITransactionsCollection{
    // Opciones disponibles para Administrador
    private ATM atm;
    List<String> transactionOptions = new ArrayList<>(Arrays.asList(
            "Supply Cash",
            "Exit"
    ));

    public AdminTransactionsCollection(ATM atm) {
        this.atm = atm;
    }

    // Muestra en consola las transacciones disponibles, y retorna la escogida según lo indicado por el usuario
    @Override
    public ITransaction chooseTransaction() {
        atm.getDisplay().showOptions("Transactions available: ", transactionOptions);
        switch (atm.getKeyboard().getChoice(transactionOptions.size())){
            case 1 -> {
                return new SupplyCashTransaction(atm);
            }
            case 2 -> {
                return new ExitTransaction(atm);
            }
            default -> {
                return null;
            }
        }
    }
}
