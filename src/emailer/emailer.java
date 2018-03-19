/**
* This class handles the process of gathering necessary data towards
* composing and sending the email.
* @author  Thomas Power
* @version 1.0
* @since   2018-03-19 
*/

package emailer;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class emailer{
	public static void emailAll(){
		/**
		 * Gathers necessary data and email addresses from database and inputs to sendEmail method
		 */
		localData.loadData();
		Client temp;
		boolean sent;
		
		File folder = new File(".\\to send");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			sent = false;
			if (listOfFiles[i].isFile()) {
				temp = localData.getClient(listOfFiles[i].getName());
				if(temp != null){
					sendEmail(temp, listOfFiles[i].getName());
					sent = true;
				}
				if(sent == true){ //Moves processed files out of the way indicating their status.
					moveFile(listOfFiles[i], ".\\to send\\sent\\" + listOfFiles[i].getName());
				}
				else if(sent == false){
					moveFile(listOfFiles[i], ".\\To send\\not sent\\" + listOfFiles[i].getName());
				}
			}
		}
	}
	
	public static void moveFile(File curFile, String thisPath){
		/**
		 * Moves file to desired directory
		 */
		try {
			Files.move(curFile.toPath(), Paths.get(thisPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendEmail(Client temp, String attachment){
		   /**
		    *Composes and sends email to specific client
		    **/
		  //recipients email
	      String to = temp.email;

	      // Sender's email ID needs to be mentioned
	      String from = "...";
	      final String username = "...";//change accordingly
	      final String password = "...";//change accordingly

	      // Assuming you are sending email through smtp.live.com
	      String host = "smtp.live.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "25");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
		   }
	         });

	      try {
			   Message message = new MimeMessage(session);
			   message.setFrom(new InternetAddress(from));
			   message.setRecipients(Message.RecipientType.TO,
		               InternetAddress.parse(to));
			
			   // Set Subject: header field
			   message.setSubject("...");
			   String date = new SimpleDateFormat("MM-yy").format(new Date());
			   BodyPart messageBodyPart = new MimeBodyPart();
		       DataSource source = new FileDataSource(".\\to send\\" + attachment);
		       messageBodyPart.setDataHandler(new DataHandler(source));
		       messageBodyPart.setFileName(temp.name + date);
		       Multipart multipart = new MimeMultipart();
		       BodyPart textBodyPart = new MimeBodyPart();
		       textBodyPart.setText("..."); // Your actual message goes here
		       multipart.addBodyPart(messageBodyPart);
		       multipart.addBodyPart(textBodyPart);
			   message.setContent(multipart);
			   
			   // Send message
			   Transport.send(message);
	
			   System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	   }
}