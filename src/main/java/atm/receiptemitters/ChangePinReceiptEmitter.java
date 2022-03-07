package atm.receiptemitters;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;

public class ChangePinReceiptEmitter extends ReceiptEmitter {
  public ChangePinReceiptEmitter(
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
            "Target Account : "
                + getAccountNumber()
                + "\n"
                + "Transaction Reference: "
                + getReference()
                + "\n"
                + "Estimado Cliente:  Le informamos que se ha actualizado su PIN."
                + "\n"
                + "Si usted no lo ha realizado, por favor "
                + "comuníquese de inmediato al xxxx-xxxx.",
            transactionDetailsFont);
    transactionDetails.setIndentationLeft(170);
    transactionDetails.setSpacingAfter(30);
    return transactionDetails;
  }
}
