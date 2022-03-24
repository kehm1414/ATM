package atm.transactionscollection;

import atm.*;
import atm.transactions.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class BaseClientTransactionsCollection implements ITransactionsCollection {
    private ATM atm;
    List<String> transactionOptions = new ArrayList<>(Arrays.asList(
            "CheckBalance",
            "Deposit Cash",
            "Withdraw Cash",
            "Transfer funds to another account",
            "Change Pin",
            "Logout"
    ));

    public BaseClientTransactionsCollection(ATM atm) {
        this.atm = atm;
    }

    @Override
    public ITransaction chooseTransaction() {
        atm.getDisplay().showOptions("Transactions available: ", transactionOptions);
        return switch (atm.getKeyboard().getChoice(transactionOptions.size())){
            case 1 -> new CheckBalanceTransaction(atm);
            case 2 -> new DepositCashTransaction(atm);
            case 3 -> new WithdrawCashTransaction(atm);
            case 4 -> new TransferFundsTransaction(atm);
            case 5 -> new ChangePinTransaction(atm);
            case 6 -> new ExitTransaction(atm);
            default -> null;
        };
    }
}
