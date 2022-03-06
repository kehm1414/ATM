package ATM.Transactions;

import ATM.*;
import ATM.ITransaction;
import ATM.ReceiptEmitters.TransferFundsReceiptEmitter;

import java.time.LocalDate;

public class TransferFundsTransaction implements ITransaction {
    private ATM atm;

    public TransferFundsTransaction(ATM atm) {
        this.atm = atm;
    }

    @Override
    public boolean process() {
        // Easy data access to reference values
        Account fromAccount = atm.getSession().getActiveAccount();
        CashDispenser cashDispenser = atm.getCashDispenser();
        Account toAccount = null;

        atm.getDisplay().askAccountToDeposit();
        String accountNumber = atm.getKeyboard().getAccountNumberInput();

        //Checks if account number entered coincides with database then reassign "toAccount"
        for(Account account: atm.getBank().getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                toAccount = account;
            }
        }
        // If account not found, try again, else, continue
        if(toAccount == null) {
            atm.getDisplay().accountNotFound();
            return process();
        }
        if(toAccount.getAccountNumber().equals(fromAccount.getAccountNumber())){
            atm.getDisplay().noTransferToSameAccount();
            return process();
        }

        // Ask for amount to transfer
        atm.getDisplay().askTransferAmount();
        double amount = atm.getKeyboard().getAmount();

        // Check if active account has enough funds to transfer
        // If it has, process the transfer
        if(amount > fromAccount.getBalance()){
            atm.getDisplay().notEnoughAccountBalance();
        } else {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            atm.getDisplay().successfulTransaction();

            // Emit receipt
            ReceiptEmitter receipt = new TransferFundsReceiptEmitter(
                    atm.getBank().getName(),
                    fromAccount.getOwnerName(),
                    LocalDate.now().toString(),
                    "Transfer of funds",
                    toAccount.getAccountNumber(),
                    String.valueOf(amount),
                    String.valueOf((int) Math.ceil(Math.random()*1000000))
            );
            receipt.toPDF("d:/receipt.pdf");

        }
        return true;
    }
}
