package atm.receiptemitters;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.logging.Logger;

@Getter
public abstract class ReceiptEmitter {
  private static final Logger LOGGER = Logger.getLogger(ReceiptEmitter.class.getName());
  private final String bankName;
  private final String clientName;
  private final String date;
  private final String transactionName;
  private final String accountNumber;
  private final String amount;
  private final String reference;

  protected ReceiptEmitter(
          String bankName,
          String clientName,
          String transactionName,
          String accountNumber,
          String amount) {
    this.bankName = bankName;
    this.clientName = clientName;
    this.date = LocalDate.now().toString();
    this.transactionName = transactionName;
    this.accountNumber = accountNumber;
    this.amount = amount;
    this.reference = String.valueOf((int) Math.ceil(Math.random() * 1000000));
  }

  public void toPDF(String location) {
    try {
      Document document = new Document();
      PdfWriter.getInstance(document, new FileOutputStream(location));
      document.open();
      buildReceipt(document);
      document.close();
      LOGGER.info("Receipt emitted and saved in PDF format");
    } catch (DocumentException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void buildReceipt(Document document) throws DocumentException {
      // Header
      document.add(getHeaderSection());
      document.add(new Paragraph("  "));
      document.add(new LineSeparator());

      // General Data
      document.add(getGeneralDataSection());
      document.add(new Paragraph("  "));
      document.add(new LineSeparator());

      // Transaction Results
      document.add(getResultsTitleSection());
      document.add(getSuccessMessageSection());
      document.add(getTransactionDetails()); //Abstract
      document.add(getFarewellMessageSection());
  }

  private Paragraph getHeaderSection(){
    Font myHeaderFont = new Font();
    myHeaderFont.setSize(20F);
    myHeaderFont.setStyle("bold");
    Paragraph header = new Paragraph(bankName + "'s ATM", myHeaderFont);
    header.setAlignment(1);
    return header;
  }

  private Paragraph getGeneralDataSection(){
    Font generalDataFont = new Font();
    generalDataFont.setSize(15F);
    return new Paragraph(
                    "Name: "
                            + clientName
                            + "\n"
                            + "Date: "
                            + date
                            + "\n"
                            + "Transaction: "
                            + transactionName,
                    generalDataFont);
  }

  private Paragraph getResultsTitleSection(){
    Font resultsTitleFont = new Font();
    resultsTitleFont.setSize(18F);
    Paragraph resultsTitle = new Paragraph("Transaction Results", resultsTitleFont);
    resultsTitle.setSpacingAfter(15);
    resultsTitle.setAlignment(1);
    return resultsTitle;
  }

  private Paragraph getSuccessMessageSection(){
    Font successMessageFont = new Font();
    successMessageFont.setSize(12F);
    Paragraph successMessage =
            new Paragraph("Your operation has been successfully completed.", successMessageFont);
    successMessage.setAlignment(1);
    successMessage.setSpacingAfter(15);
    return successMessage;
  }

  private Paragraph getFarewellMessageSection(){
    Font farewellMessageFont = new Font();
    farewellMessageFont.setStyle(3);
    farewellMessageFont.setSize(12);
    Paragraph farewellMessage =
            new Paragraph(
                    "¡Thank you for preferring us! Hope to see you again.", farewellMessageFont);
    farewellMessage.setAlignment(1);
    return farewellMessage;
  }

  protected abstract Paragraph getTransactionDetails();
}
