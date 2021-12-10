package za.ac.ss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.enums.MedicoState;
import za.ac.ss.service.faces.Notification;
import za.ac.ss.service.faces.RAFService;

@Slf4j
@Service
public class MedicoNotificationService implements Notification {
	
	@Autowired RAFService rafService;
	
	@Override
	public void process() {
		log.info("Medico is loaded");
	}

	public String notify(MedicoState state) {
		//this.rafService.statusUpdate(state);
		log.info("Medico state " + state);
		return state.name();
	}
}
