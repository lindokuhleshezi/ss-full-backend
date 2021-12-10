package za.ac.ss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.ac.ss.service.faces.EmailService;

import za.ac.ss.entities.EmailTemplate;

@RequestMapping("/email")
@RestController
public class EmailController {

	@Autowired private EmailService emailService;
	
	@PostMapping("/create/template")
	public EmailTemplate createTemplate(EmailTemplate emailTemplate) {
		if (emailTemplate != null) {
			this.emailService.save(emailTemplate);
		}
		return emailTemplate;
	}
	
	@GetMapping("/list")
	public List<EmailTemplate> listTemplates() {
		return this.emailService.findAll();
	}
}
