package atm.transactionscollection;

import atm.*;
import atm.transactions.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Esta es la clase base para las TransactionsCollection
*  En estas clases se manejarán las opciones que pueden tener cada tipo de cuenta
*  Esta clase base tiene todas las opciones disponibles para un cliente con full acceso
*  Las clases que extienden a esta clase base deberán hacer overwrite de:
*  la lista "transactionOptions" y del método "chooseTransaction"*/

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
        switch (atm.getKeyboard().getChoice(transactionOptions.size())){
            case 1 -> {
                return new CheckBalanceTransaction(atm);
            }
            case 2 -> {
                return new DepositCashTransaction(atm);
            }
            case 3 -> {
                return new WithdrawCashTransaction(atm);
            }
            case 4 -> {
                return new TransferFundsTransaction(atm);
            }
            case 5 -> {
                return new ChangePinTransaction(atm);
            }
            case 6 -> {
                return new ExitTransaction(atm);
            }
            default -> {
                return null;
            }
        }
    }
}
