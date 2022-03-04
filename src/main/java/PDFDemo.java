import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFDemo {
    public static void main(String[] args) {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("d:/helloitext.pdf"));
            document.open();
            document.add(new Paragraph("Example text Example text Example text Example text Example text"));
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("iTextPDF Program executed");
    }
}
