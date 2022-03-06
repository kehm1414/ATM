package ATM.Transactions;

import ATM.*;
import ATM.ITransaction;
import ATM.ReceiptEmitters.PersonalTransactionReceiptEmitter;

import java.time.LocalDate;
import java.util.Arrays;

public class DepositCashTransaction implements ITransaction {
    private ATM atm;

    public DepositCashTransaction(ATM atm) {
        this.atm = atm;
    }
    @Override
    public boolean process() {
        Account account = atm.getSession().getActiveAccount();
        CashDispenser cashDispenser = atm.getCashDispenser();

        atm.getDisplay().askDepositAmount();
        double amount = atm.getKeyboard().getAmount();

        atm.getDisplay().showOptions("Amount: " + amount + ". This is correct?", Arrays.asList("Yes", "No"));
        int choice = atm.getKeyboard().getChoice(2);

        if(choice == 1){
            cashDispenser.setBalance(cashDispenser.getBalance() + amount);
            account.setBalance(account.getBalance()+amount);
            atm.getDisplay().successfulTransaction();

            // Emit receipt
            ReceiptEmitter receipt = new PersonalTransactionReceiptEmitter(
                    atm.getBank().getName(),
                    account.getOwnerName(),
                    LocalDate.now().toString(),
                    "Deposit Cash",
                    account.getAccountNumber(),
                    String.valueOf(amount),
                    String.valueOf((int) Math.ceil(Math.random()*1000000))
            );
            receipt.toPDF("d:/receipt.pdf");

            return true;
        } else {
            return process();
        }

    }
}
