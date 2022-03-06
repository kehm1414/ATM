package ATM.Transactions;

import ATM.ATM;
import ATM.ITransaction;

public class SupplyCashTransaction implements ITransaction {
    private ATM atm;

    public SupplyCashTransaction(ATM atm) {
        this.atm = atm;
    }
    @Override
    public boolean process() {
        return false;
    }
}
