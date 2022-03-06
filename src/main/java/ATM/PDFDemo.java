package ATM;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFDemo {
    public static void main(String[] args) {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("d:/receipt.pdf"));
            document.open();

            // Header
            Font myHeaderFont = new Font();
            myHeaderFont.setSize(20F);
            myHeaderFont.setStyle("bold");
            Paragraph header = new Paragraph("Universal ATM.Bank", myHeaderFont);
            header.setAlignment(1);
            document.add(header);
            document.add(new Paragraph("  "));
            document.add(new LineSeparator());

            // General Data
            Font generalDataFont = new Font();
            generalDataFont.setSize(15F);
            Paragraph generalData = new Paragraph(
                    """
                            Name: Kevin Hernández
                            Date: 05/03/22
                            Transaction: Deposit""",
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
            Paragraph successMessage = new Paragraph(
                    "Your operation has been successfully completed."
            ,successMessageFont);
            successMessage.setAlignment(1);
            successMessage.setSpacingAfter(15);
            document.add(successMessage);

            // Transaction Details
            Font transactionDetailsFont = new Font();
            transactionDetailsFont.setSize(10F);
            Paragraph transactionDetails = new Paragraph(
                    """
                            Target account: 00110011001100110011
                            Amount: $10,00
                            Reference Number: 123456789
                            """
                    , transactionDetailsFont);
            transactionDetails.setIndentationLeft(170);
            transactionDetails.setSpacingAfter(30);
            //transactionDetails.setIndentationRight(150);
            document.add(transactionDetails);

            // Farewell Message
            Font farewellMessageFont = new Font();
            farewellMessageFont.setStyle(3);
            farewellMessageFont.setSize(12);
            Paragraph farewellMessage = new Paragraph(
                    "¡Thank you for preferring us! Hope to see you again.",
                    farewellMessageFont);
            farewellMessage.setAlignment(1);
            document.add(farewellMessage);
            document.close();
            System.out.println("Receipt emitted");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
