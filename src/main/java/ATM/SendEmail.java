package ATM;

import javax.mail.*;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class SendEmail {
    public static void main(String[] args) {
        //Auth info
        final String username = "testemailapi99@gmail.com";
        final String password = "TestEmail99-";
        String toEmail = "testemailapi99@gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Email Message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(username));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Practicando envío de correos");
            //msg.setText("Email body text");

            Multipart emailContent = new MimeMultipart();

            //Text Body Part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Texto texto texto texto");

            //Attachment body part
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile("D:\\Downloads\\Shedule Bootcamp CSF - Sheet1.pdf");

            //Attach Body parts
            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfAttachment);

            //Attach Multipart to message
            msg.setContent(emailContent);

            Transport.send(msg);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }


    }
}
