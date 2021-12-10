package za.ac.ss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.ss.enums.MedicoState;
import za.ac.ss.enums.MedicoWorkflow;

@Service
public class MedicoWorkflowFactory {
	
	@Autowired MedicoNotificationService service;
	
	public MedicoState run(MedicoWorkflow medicoWorkflow, Long id) {
		return medicoWorkflow.doProcessOperation(service);
	}

}
