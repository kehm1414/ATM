package ATM.Transactions;


import ATM.ATM;
import ATM.ITransaction;

public class CheckATMBalanceTransaction implements ITransaction {
    private ATM atm;

    public CheckATMBalanceTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        double balance = atm.getCashDispenser().getBalance();
        atm.getDisplay().showATMBalance(balance);
        return true;
    }
}
