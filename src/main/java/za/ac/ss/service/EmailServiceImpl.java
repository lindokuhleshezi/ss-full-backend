package za.ac.ss.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.config.MailConfiguration;
import za.ac.ss.dto.MailRequestDTO;
import za.ac.ss.entities.EmailTemplate;
import za.ac.ss.helper.MailProperties;
import za.ac.ss.repository.EmailRepository;
import za.ac.ss.service.faces.EmailService;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	public static final String DOCUMENT_OUTSTANDING_NOTIFICATION = "document_outstanding_notification";
	public static final String MEDICO_3DAYS_PRIOR_NOTIFICATION = "document_outstanding_notification";
	public static final String OTP_VERIFICATION_NOTIFICATION = "document_outstanding_notification";
	
	@Autowired private MailConfiguration sender;
	@Autowired private EmailRepository repository;
	@Autowired private MailProperties properties;
	

	public void sendEmail(String recipent, String message, MailRequestDTO mailRequestDTO) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			messageHelper.setFrom(properties.getFromAddress());
			messageHelper.setTo(mailRequestDTO.getTo());
			messageHelper.setSubject(mailRequestDTO.getSubject());
			messageHelper.setText(mailRequestDTO.getBody());
			messageHelper.addAttachment(mailRequestDTO.getAttachment().toString(), new ByteArrayResource(mailRequestDTO.getAttachment()));
		};
		try {
			sender.getJavaMailSender().send(messagePreparator);
		} catch (MailException e) {
			log.warn("Email failed to send, ", e);
		}
	}


	@Override
	public void sendEmail(MailRequestDTO mailRequestDTO, Map<String, Object> model) {
		sendEmail(mailRequestDTO, model, "medico-notification.ftl");
	}

	@Override
	public void sendEmail(MailRequestDTO mailRequestDTO, Map<String, Object> model, String template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEmail(MailRequestDTO mailRequestDTO, Map<String, Object> model, String template,
			String emailAddress) {
	}
	
	@Override
	public void save(EmailTemplate emailTemplate) {
		this.repository.save(EmailTemplate.builder().toAddress(emailTemplate.getToAddress()).body(emailTemplate.getBody()).subject(emailTemplate.getSubject()).attachment(emailTemplate.getAttachment()).build());
	}

	@Override
	public List<EmailTemplate> findAll() {
		return this.repository.findAll();
	}
	
}
