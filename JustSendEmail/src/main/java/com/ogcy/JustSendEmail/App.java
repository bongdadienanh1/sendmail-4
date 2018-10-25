package com.ogcy.JustSendEmail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import com.ogcy.JustSendEmail.*;

public class App 
{
    public static void main( String[] args )
    {
    	final String fromEmail = "obeikanvisitors@gmail.com"; //requires valid gmail id
		final String password = "Obeikan@2018"; // correct password for gmail id
		final String toEmail = "talhamdi@gmail.com"; // can be any email id 
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		
		EmailUtil.sendEmail(session, toEmail,"TLSEmail Testing Subject", "TLSEmail Testing Body");
		
	
		
	}

	}


    	
    	
 

