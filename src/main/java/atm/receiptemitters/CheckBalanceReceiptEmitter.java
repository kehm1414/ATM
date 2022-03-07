package atm.receiptemitters;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;

public class CheckBalanceReceiptEmitter extends ReceiptEmitter {
  public CheckBalanceReceiptEmitter(
      String bankName,
      String clientName,
      String date,
      String transactionName,
      String accountNumber,
      String amount,
      String reference) {
    super(bankName, clientName, date, transactionName, accountNumber, amount, reference);
  }

  @Override
  public Paragraph getTransactionDetails() {
    Font transactionDetailsFont = new Font();
    transactionDetailsFont.setSize(10F);
    Paragraph transactionDetails =
        new Paragraph(
            "ATM.Account: "
                + getAccountNumber()
                + "\n"
                + "Balance: "
                + getAmount()
                + "\n"
                + "Transaction Reference: "
                + getReference(),
            transactionDetailsFont);
    transactionDetails.setIndentationLeft(170);
    transactionDetails.setSpacingAfter(30);
    return transactionDetails;
  }
}
