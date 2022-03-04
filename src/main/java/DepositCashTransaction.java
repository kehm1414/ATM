public class DepositCashTransaction implements ITransaction{
    private ATM atm;

    public DepositCashTransaction(ATM atm) {
        this.atm = atm;
    }
    @Override
    public boolean process() {
        return true;
    }
}
