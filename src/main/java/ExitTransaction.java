public class ExitTransaction implements ITransaction{
    private ATM atm;

    public ExitTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        atm.getSession().logout();
        return true;
    }
}
