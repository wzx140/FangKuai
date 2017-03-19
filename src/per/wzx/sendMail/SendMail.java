package per.wzx.sendMail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * this static class is used to send mails for proposal
 */
public class SendMail {
    public static void send(String str,String sender,String password) throws IOException,
            MessagingException {
        //setting properties which are about smpt sever
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", "smtp.163.com");
        properties.setProperty("mail.smtp.auth", "true");
//        System.out.println(sender);
//        System.out.println(password);
//        System.out.println(str);
        Session session = Session.getDefaultInstance(properties);
        //session.setDebug(true);
        MimeMessage message = createMail(session, sender, "wzx140@qq.com", str);
        Transport transport = session.getTransport();
        transport.connect(sender,password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private static MimeMessage createMail(Session session, String sendPer, String receivePer, String text)
            throws UnsupportedEncodingException, MessagingException {
//        System.out.println(sendPer);
//        System.out.println(text);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(
                sendPer, "反应问题的人", "utf-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(
                receivePer));
        message.setSubject("别人的意见", "utf-8");
        message.setText(text, "utf-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

//    public static void main(String[] args) {
//        try {
//            SendMail.send("nihao");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
}
