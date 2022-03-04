public class CheckBalanceTransaction implements ITransaction{
    private ATM atm;

    public CheckBalanceTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        atm.getDisplay().showBalance(atm.getSession().getActiveAccount().getBalance());
        return true;
    }
}
