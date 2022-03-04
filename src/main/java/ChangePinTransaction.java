public class ChangePinTransaction implements ITransaction{
    private ATM atm;

    public ChangePinTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        return false;
    }
}
