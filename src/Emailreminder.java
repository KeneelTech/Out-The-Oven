/*import java.time.LocalDate;
import java.util.*;
import javax.mail.jar.*;
import javax.mail.internet.*;

public class Emailreminder {
    
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587"; 
    private static final String USERNAME = "out.th3.oven@gmail.com";
    private static final String PASSWORD = "Group1_2140"; 
    private static final String FROM_EMAIL = "out.th3.oven@gmail.com";
    
    public static void sendEmail(String toEmail, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);
            
           
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        for (User User : invoices.txt) {
            String toEmail = User.getEmail();
            String subject = "Order Reminder";
            if User.getDate()== LocalDate.now().plusDays(2){
                String body = "Dear " + User.getName() + ",\n\n"
                        + "This is a reminder about your recent order. "
                        + "Your order is to be delivered in 2 days.\n\n"
                        + "Thank you for ordering with us!\n"
                        +"Out the Oven!\n";
            }
            if User.getDate()== LocalDate.now().plusDays(1){
                String body = "Dear " + User.getName() + ",\n\n"
                        + "This is a reminder about your recent order. "
                        + "Your order is to be delivered tomorrow.\n\n"
                        + "Thank you for ordering with us!\n"
                        +"Out the Oven!\n";
            }
            if User.getDate()== LocalDate.now(){
                String body = "Dear " + User.getName() + ",\n\n"
                        + "This is a reminder about your recent order. "
                        + "Your order will be delivered today.\n\n"
                        + "Thank you for ordering with us!\n"
                        +"Out the Oven!\n";
            }
            
            sendEmail(toEmail, subject, body);
        }
    }
}
/*. */