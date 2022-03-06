package ATM.TransactionsCollection;

import ATM.*;
import ATM.Transactions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Colección de transacciones disponibles para el tipo de cuenta OnlyCash */

public class OnlyCashTransactionsCollection extends BaseClientTransactionsCollection{
    public OnlyCashTransactionsCollection(ATM atm) {
        super(atm);
    }

    List<String> transactionOptions = new ArrayList<>(Arrays.asList(
            "CheckBalance",
            "Deposit Cash",
            "Withdraw Cash",
            "Change Pin",
            "Logout"
    ));

    @Override
    public ITransaction chooseTransaction() {
        getAtm().getDisplay().showOptions("Transactions available: ", transactionOptions);
        switch (getAtm().getKeyboard().getChoice(transactionOptions.size())){
            case 1 -> {
                return new CheckBalanceTransaction(getAtm());
            }
            case 2 -> {
                return new DepositCashTransaction(getAtm());
            }
            case 3 -> {
                return new WithdrawCashTransaction(getAtm());
            }
            case 4 -> {
                return new ChangePinTransaction(getAtm());
            }
            case 5 -> {
                return new ExitTransaction(getAtm());
            }
            default -> {
                return null;
            }
        }
    }
}
