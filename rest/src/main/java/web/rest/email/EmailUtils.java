package web.rest.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_PORT = "587";
	private static final String emailMsgTxt = "Test Message Contents";
	private static final String emailSubjectTxt = "A test from gmail";
	private static final String[] sendTo = { "marcin.slowik95@gmail.com" };

	public static void sendSSLMessage() throws MessagingException {
		boolean debug = true;

		// Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		Properties props = new Properties();
		props.put("mail.smtp.user", "marcin.slowik95@gmail.com");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("marcin.slowik95@gmail.com", "ogame123");
			}
		});

		session.setDebug(debug);

		Message msg = new MimeMessage(session);

		InternetAddress[] addressTo = new InternetAddress[sendTo.length];
		for (int i = 0; i < sendTo.length; i++) {
			addressTo[i] = new InternetAddress(sendTo[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(emailSubjectTxt);
		msg.setContent(emailMsgTxt, "text/plain");
		Transport.send(msg);
	}

}
