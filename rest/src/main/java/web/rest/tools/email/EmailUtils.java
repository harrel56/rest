package web.rest.tools.email;

import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {

	@Autowired
	private MessageSource messageSource;

	@Value("${mail.smtp.host}")
	private String smtpHost;

	@Value("${mail.smtp.port}")
	private String smtpPort;

	@Value("${mail.login}")
	private String login;

	@Value("${mail.password}")
	private String password;

	@Async
	public void sendActivationEmailAsync(String recipient, String login, String activationString, Locale locale) throws MessagingException {

		Message msg = this.prepareMimeMessage();
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

		msg.setSubject(this.messageSource.getMessage("userManagement.activationMailSubject", null, locale));
		msg.setContent(this.messageSource.getMessage("userManagement.activationMailBody", new Object[] { login, activationString }, locale),
				"text/plain");
		Transport.send(msg);
	}

	private MimeMessage prepareMimeMessage() {
		Properties props = new Properties();
		props.put("mail.smtp.user", this.login);
		props.put("mail.smtp.host", this.smtpHost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", this.smtpPort);
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EmailUtils.this.login, EmailUtils.this.password);
			}
		});

		return new MimeMessage(session);
	}

}
