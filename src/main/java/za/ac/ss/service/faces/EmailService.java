package za.ac.ss.service.faces;

import java.util.List;
import java.util.Map;

import za.ac.ss.dto.MailRequestDTO;
import za.ac.ss.entities.EmailTemplate;

public interface EmailService {
	public void sendEmail(MailRequestDTO mailRequestDTO, Map<String, Object> model);
	public void sendEmail(MailRequestDTO mailRequestDTO, Map<String, Object> model, String template);
	public void sendEmail(MailRequestDTO mailRequestDTO, Map<String, Object> model, String template,
			String emailAddress);
	public void save(EmailTemplate emailTemplate);
	public List<EmailTemplate> findAll();
}