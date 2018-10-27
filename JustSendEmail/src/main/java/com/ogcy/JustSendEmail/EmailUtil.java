package com.ogcy.JustSendEmail;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

	      msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	  Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	   public static void sendImageEmail(Session session, String toEmail, String subject, String body) throws IOException{
	    	try{
	             MimeMessage msg = new MimeMessage(session);
	             msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	    	     msg.addHeader("format", "flowed");
	    	     msg.addHeader("Content-Transfer-Encoding", "8bit");
	    	      
	    	     msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

	    	     msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

	    	     msg.setSubject(subject, "UTF-8");

	    	     msg.setSentDate(new Date());

	    	     msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	    	      
	             // Create the message body part
	             BodyPart messageBodyPart = new MimeBodyPart();

	             messageBodyPart.setText(body);
	             
	             // Create a multipart message for attachment
	             Multipart multipart = new MimeMultipart();

	             // Set text message part
	             multipart.addBodyPart(messageBodyPart);

	             // Second part is image attachment
	             messageBodyPart = new MimeBodyPart();
	             String filename = "logo.png";
	             BufferedImage combined = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
	 		     Graphics2D g = (Graphics2D) combined.getGraphics();
	 		     g.drawLine(0, 0, 100, 100);
	 		     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	 		     ImageIO.write(combined, "png", bytes);
	 		     
	 		     byte[] bytes2 = bytes.toByteArray();
	 		     
	             DataSource source = new ByteArrayDataSource(bytes2, "image/png");
	             
	             messageBodyPart.setDataHandler(new DataHandler(source));
	             messageBodyPart.setFileName(filename);
	             //Trick is to add the content-id header here
	             messageBodyPart.setHeader("Content-ID", "image_id");
	             multipart.addBodyPart(messageBodyPart);

	             //third part for displaying image in the email body
	             messageBodyPart = new MimeBodyPart();
	             messageBodyPart.setContent("<h1>Attached Image</h1>" +
	            		     "<img src='cid:image_id'>", "text/html");
	             multipart.addBodyPart(messageBodyPart);
	             
	             //Set the multipart message to the email message
	             msg.setContent(multipart);

	             // Send message
	             Transport.send(msg);
	             System.out.println("EMail Sent Successfully with image!!");
	          }catch (MessagingException e) {
	             e.printStackTrace();
	          } catch (UnsupportedEncodingException e) {
	    		 e.printStackTrace();
	    	}
	    }
}
