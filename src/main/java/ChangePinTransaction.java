public class ChangePinTransaction implements ITransaction{
    private ATM atm;

    public ChangePinTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        atm.getSession().getActiveAccount().getPin();
        return false;
    }
}
