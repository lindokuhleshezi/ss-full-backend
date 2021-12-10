package za.ac.ss.service.faces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OutstandingDocumentNotificationService implements Notification {

	@Autowired EmailService emailService;
	@Autowired CustomerService customerService;
	@Autowired RAFService rafService;
	@Autowired FileService fileService;
	
	@Override
	@Scheduled(cron = "0 0 10 1-31 * ?")
	public void process() {
		log.info("Document Reminder executed");
		customerService.findAll().forEach((data) -> {
			rafService.findRoadAccidentFundByPersonId(data.getId()).forEach((accidentFundRecord) -> { 
				log.info("accidentFundRecord " + accidentFundRecord.getReferenceNumber()); 
				if (data.getId() != null) {
					log.info("send email to " + data.getEmail());
				}
			});
		});
	}
}
