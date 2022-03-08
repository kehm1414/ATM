package atm.transactionscollection;

import atm.*;
import atm.transactions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Colección de transacciones disponibles para el tipo de cuenta OnlyCash */

public class OnlyCashTransactionsCollection implements ITransactionsCollection{
    private ATM atm;
    List<String> octransactionOptions = new ArrayList<>(Arrays.asList(
            "CheckBalance",
            "Deposit Cash",
            "Withdraw Cash",
            "Change Pin",
            "Logout"
    ));

    public OnlyCashTransactionsCollection(ATM atm) {
        this.atm = atm;
    }

    @Override
    public ITransaction chooseTransaction() {
        atm.getDisplay().showOptions("Transactions available: ", octransactionOptions);
        return switch (atm.getKeyboard().getChoice(octransactionOptions.size())){
            case 1 -> new CheckBalanceTransaction(atm);
            case 2 -> new DepositCashTransaction(atm);
            case 3 -> new WithdrawCashTransaction(atm);
            case 4 -> new ChangePinTransaction(atm);
            case 5 -> new ExitTransaction(atm);
            default -> null;
        };
    }
}
