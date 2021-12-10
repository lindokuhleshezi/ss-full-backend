package za.ac.ss.enums;

import za.ac.ss.service.MedicoNotificationService;

public enum MedicoWorkflow {

	APPROVED {
		@Override
		public MedicoState doProcessOperation(MedicoNotificationService mediWorkflowService) {
			mediWorkflowService.notify(MedicoState.APPROVED);
			return MedicoState.APPROVED;
		}
	},
	REJECTED {
		@Override
		public MedicoState doProcessOperation(MedicoNotificationService mediWorkflowService) {
			mediWorkflowService.notify(MedicoState.REJECTED);
			return MedicoState.REJECTED;
		}
	},
	PENDING {
		@Override
		public MedicoState doProcessOperation(MedicoNotificationService mediWorkflowService) {
			mediWorkflowService.notify(MedicoState.PENDING);
			return MedicoState.PENDING;
		}
	};
	
	public abstract MedicoState doProcessOperation(MedicoNotificationService mediWorkflowService);
}
