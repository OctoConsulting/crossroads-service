package gov.fbi.elabs.crossroads.config;
import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import gov.fbi.elabs.crossroads.exception.BaseApplicationException;

@Configuration
public class MailConfiguration {
	
	@Resource
    Environment environment;
	
	@Bean
	public JavaMailSender mailSender() throws BaseApplicationException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		try{
			mailSender.setHost( environment.getProperty("mail.server"));
			mailSender.setPort(25);
			mailSender.setProtocol(environment.getProperty("mail.protocol"));
		} catch(Exception e){
			throw new BaseApplicationException(e);
		}
		
		return mailSender;
	}
}