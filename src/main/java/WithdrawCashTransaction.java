public class WithdrawCashTransaction implements ITransaction{
    private ATM atm;

    public WithdrawCashTransaction(ATM atm) {
        this.atm = atm;
    }
    @Override
    public boolean process() {
        return true;
    }
}
