public class TransferFundsTransaction implements ITransaction{
    private ATM atm;

    public TransferFundsTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        return false;
    }
}
