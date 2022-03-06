package ATM.Transactions;

import ATM.*;
import ATM.ITransaction;
import ATM.ReceiptEmitters.CheckBalanceReceiptEmitter;

import java.time.LocalDate;

public class CheckBalanceTransaction implements ITransaction {
    private ATM atm;

    public CheckBalanceTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        double balance = atm.getSession().getActiveAccount().getBalance();
        atm.getDisplay().showBalance(balance);

        // Emit receipt
        ReceiptEmitter receipt = new CheckBalanceReceiptEmitter(
                atm.getBank().getName(),
                atm.getSession().getActiveAccount().getOwnerName(),
                LocalDate.now().toString(),
                "Check Balance",
                atm.getSession().getActiveAccount().getAccountNumber(),
                String.valueOf(balance),
                String.valueOf((int) Math.ceil(Math.random()*1000000))
        );
        receipt.toPDF("d:/receipt.pdf");
        return true;
    }
}
