package atm;

import javax.mail.Session;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailSender {
  private static final Logger LOGGER = Logger.getLogger(EmailSender.class.getName());

  private EmailSender() {
    throw new IllegalStateException("Utility class");
  }

  public static void send(String subject, String text, String attachmentLocation, String toEmail) {
    // Auth info
    final String username = "testemailapi99@gmail.com";
    final String password = "TestEmail99-";

    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    javax.mail.Session session =
        Session.getInstance(
            properties,
            new javax.mail.Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
              }
            });

    // Email Message
    MimeMessage msg = new MimeMessage(session);
    try {
      msg.setFrom(new InternetAddress(username));
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
      msg.setSubject(subject);

      Multipart emailContent = new MimeMultipart();

      // Text Body Part
      MimeBodyPart textBodyPart = new MimeBodyPart();
      textBodyPart.setText(text);

      // Attachment body part
      MimeBodyPart pdfAttachment = new MimeBodyPart();
      pdfAttachment.attachFile(attachmentLocation);

      // Attach Body parts
      emailContent.addBodyPart(textBodyPart);
      emailContent.addBodyPart(pdfAttachment);

      // Attach Multipart to message
      msg.setContent(emailContent);

      Transport.send(msg);
      LOGGER.info("A notification has been set to user's email.");
    } catch (MessagingException | IOException e) {
      e.printStackTrace();
    }
  }
}
