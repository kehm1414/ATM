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

@Getter
public abstract class ReceiptEmitter {
  private String bankName;
  private String clientName;
  private String date;
  private String transactionName;
  private String accountNumber;
  private String amount;
  private String reference;

  public ReceiptEmitter(
      String bankName,
      String clientName,
      String date,
      String transactionName,
      String accountNumber,
      String amount,
      String reference) {
    this.bankName = bankName;
    this.clientName = clientName;
    this.date = date;
    this.transactionName = transactionName;
    this.accountNumber = accountNumber;
    this.amount = amount;
    this.reference = reference;
  }

  public void toPDF(String location) {
    try {
      Document document = new Document();
      PdfWriter.getInstance(document, new FileOutputStream(location));
      document.open();

      // Header
      Font myHeaderFont = new Font();
      myHeaderFont.setSize(20F);
      myHeaderFont.setStyle("bold");
      Paragraph header = new Paragraph(bankName + "'s ATM", myHeaderFont);
      header.setAlignment(1);
      document.add(header);
      document.add(new Paragraph("  "));
      document.add(new LineSeparator());

      // General Data
      Font generalDataFont = new Font();
      generalDataFont.setSize(15F);
      Paragraph generalData =
          new Paragraph(
              "Name: "
                  + clientName
                  + "\n"
                  + "Date: "
                  + date
                  + "\n"
                  + "Transaction: "
                  + transactionName,
              generalDataFont);
      document.add(generalData);
      document.add(new Paragraph("  "));
      document.add(new LineSeparator());

      // Transaction Results

      // Title
      Font resultsTitleFont = new Font();
      resultsTitleFont.setSize(18F);
      Paragraph resultsTitle = new Paragraph("Transaction Results", resultsTitleFont);
      resultsTitle.setSpacingAfter(15);
      resultsTitle.setAlignment(1);
      document.add(resultsTitle);

      // Success Message
      Font successMessageFont = new Font();
      successMessageFont.setSize(12F);
      Paragraph successMessage =
          new Paragraph("Your operation has been successfully completed.", successMessageFont);
      successMessage.setAlignment(1);
      successMessage.setSpacingAfter(15);
      document.add(successMessage);

      // Transaction Details

      document.add(getTransactionDetails());

      // Farewell Message
      Font farewellMessageFont = new Font();
      farewellMessageFont.setStyle(3);
      farewellMessageFont.setSize(12);
      Paragraph farewellMessage =
          new Paragraph(
              "¡Thank you for preferring us! Hope to see you again.", farewellMessageFont);
      farewellMessage.setAlignment(1);
      document.add(farewellMessage);
      document.close();
      System.out.println("Receipt emitted");
    } catch (DocumentException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public abstract Paragraph getTransactionDetails();
}
