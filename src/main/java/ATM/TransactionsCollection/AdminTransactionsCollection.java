package ATM.TransactionsCollection;

import ATM.ATM;
import ATM.ITransaction;
import ATM.ITransactionsCollection;
import ATM.Transactions.CheckATMBalanceTransaction;
import ATM.Transactions.ExitTransaction;
import ATM.Transactions.SupplyCashTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminTransactionsCollection implements ITransactionsCollection {
    // Opciones disponibles para Administrador
    private ATM atm;
    List<String> transactionOptions = new ArrayList<>(Arrays.asList(
            "Check ATM Balance",
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
                return new CheckATMBalanceTransaction(atm);
            }
            case 2 -> {
                return new SupplyCashTransaction(atm);
            }
            case 3 -> {
                return new ExitTransaction(atm);
            }
            default -> {
                return null;
            }
        }
    }
}
