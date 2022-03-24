package atm.transactionscollection;

import atm.ATM;
import atm.transactions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminTransactionsCollection implements ITransactionsCollection {
    private ATM atm;
    List<String> transactionOptions = new ArrayList<>(Arrays.asList(
            "Check ATM Balance",
            "Supply Cash",
            "Change ATM's Admin Pin",
            "Exit"
    ));

    public AdminTransactionsCollection(ATM atm) {
        this.atm = atm;
    }

    @Override
    public ITransaction chooseTransaction() {
        atm.getDisplay().showOptions("Transactions available: ", transactionOptions);
        return switch (atm.getKeyboard().getChoice(transactionOptions.size())){
            case 1 -> new CheckATMBalanceTransaction(atm);
            case 2 -> new SupplyCashTransaction(atm);
            case 3 -> new ChangeAdminPinTransaction(atm);
            case 4 -> new ExitTransaction(atm);
            default -> null;
        };
    }
}
