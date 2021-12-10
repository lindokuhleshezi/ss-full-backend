package za.ac.ss.service;

import org.springframework.stereotype.Component;

import za.ac.ss.enums.NOTIFICATION_TYPE;
import za.ac.ss.service.faces.Notification;

@Component
public class NotificatonFactory {
	
	public Notification createNotification(String type) {
		if (type == null || type.isEmpty()) {
			return null;
		}
		if(type == NOTIFICATION_TYPE.MEDICO_NOTIFY.name()) {
			return new MedicoNotificationService();
		} else if (type == NOTIFICATION_TYPE.MEDICO_REMINDER.name()) {
			return new MedicoNotificationService();
		}
		return null;
	}

}
