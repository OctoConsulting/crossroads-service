package gov.fbi.elabs.crossroads.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import gov.fbi.elabs.crossroads.exception.BaseApplicationException;

@Service
public class MailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	private static String fromAddress="no-reply-sam@gsa.gov";
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MailSenderService.class);
	
	
	/**
    *  Configuration, and creating the mailSender object, is done the first time the mail service is called.
    *
    *
    */
	public void sendMailMessage(final String toAddress, final String emailSubject, final String emailText, final String attachmentFilePath) throws BaseApplicationException{

		
		mailSender.send(new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				helper.addTo(toAddress);
				helper.setFrom(fromAddress);
				helper.setSubject(emailSubject);
				helper.setText(emailText);
				
				File file = new File(attachmentFilePath);
				FileSystemResource fileSystemResoure = new FileSystemResource(file);
				helper.addAttachment(file.getName(), fileSystemResoure);
			}
		});
		logger.info("Email successfully sent to " + toAddress);
	}
}