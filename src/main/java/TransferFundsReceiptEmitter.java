import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

public class TransferFundsReceiptEmitter extends ReceiptEmitter {
    public TransferFundsReceiptEmitter(String bankName, String clientName, String date, String transactionName, String accountNumber, String amount, String reference) {
        super(bankName, clientName, date, transactionName, accountNumber, amount, reference);
    }

    @Override
    public Paragraph getTransactionDetails() {
        Font transactionDetailsFont = new Font();
        transactionDetailsFont.setSize(10F);
        Paragraph transactionDetails = new Paragraph(
                    "Transferred to Account: " + getAccountNumber() + "\n"
                        + "Amount Transferred: " + getAmount() + "\n"
                        + "Transaction Reference: " + getReference()
                , transactionDetailsFont);
        transactionDetails.setIndentationLeft(170);
        transactionDetails.setSpacingAfter(30);
        return transactionDetails;
    }
}
