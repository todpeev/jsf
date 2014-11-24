package validation;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mail { 

 private String username ="";
 private String password ="";
 private String from = "";
 private String to = "";
 private String mailMessage = "";
 private String subject = "Registration for Wellness month Sucessfull";
 private String mailServer ="";
 private String port = "";
 
 public Mail (String recepient, String activity) 
 {	 
	 username ="";
	 password ="";
	 from = "";
	 mailServer ="";
	 port = "";
     to = recepient;
     mailMessage = "You have sucessfully registered for wellness activity: " + activity +
    		 ". You will be contacted by our team with further infomration when activity commences.";
     subject = "Registration for Wellness month Sucessfull";
    
 } 
 

 public void send()
 {
 
  Properties props = new Properties();
  props.put("mail.smtp.auth", "true");
  props.put("mail.smtp.starttls.enable", "true");
  props.put("mail.smtp.host", mailServer);
  props.put("mail.smtp.port", port);
  Session session = Session.getInstance(props,
 
  new javax.mail.Authenticator() 
  {
	  protected PasswordAuthentication getPasswordAuthentication() 
	  {
		  return new PasswordAuthentication(username, password);
	  }
   });
 
  try {
 
   Message message = new MimeMessage(session);
   message.setFrom(new InternetAddress(from));
   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
   message.setSubject(subject);
   message.setText(mailMessage);
   Transport.send(message);
   
  } catch (MessagingException e) {
	  
  }
 }
 
}
 