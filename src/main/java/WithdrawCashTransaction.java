import java.time.LocalDate;

public class WithdrawCashTransaction implements ITransaction{
    private ATM atm;

    public WithdrawCashTransaction(ATM atm) {
        this.atm = atm;
    }
    @Override
    public boolean process() {
        // Easy data access to reference values
        Account account = atm.getSession().getActiveAccount();
        CashDispenser cashDispenser = atm.getCashDispenser();

        // Ask user for amount to withdraw
        atm.getDisplay().askWithdrawAmount();
        double amount = atm.getKeyboard().getAmount();

        // Verify if withdrawal amount is possible for atm and active account
        if(amount > cashDispenser.getBalance()){
            atm.getDisplay().notEnoughATMBalance();
        } else if(amount > account.getBalance()) {
            atm.getDisplay().notEnoughAccountBalance();
        } else {
            cashDispenser.setBalance(cashDispenser.getBalance() - amount);
            account.setBalance(account.getBalance() - amount);

            // Emit receipt
            ReceiptEmitter receipt = new PersonalTransactionReceiptEmitter(
                    atm.getBank().getName(),
                    account.getOwnerName(),
                    LocalDate.now().toString(),
                    "Withdraw Cash",
                    account.getAccountNumber(),
                    String.valueOf(amount),
                    String.valueOf((int) Math.ceil(Math.random()*1000000))
            );
            receipt.toPDF("d:/receipt.pdf");
        }
        return true;
    }
}
