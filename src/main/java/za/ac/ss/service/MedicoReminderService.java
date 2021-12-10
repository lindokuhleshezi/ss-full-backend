package za.ac.ss.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.HistoryJobs;
import za.ac.ss.entities.DoctorAppointment;
import za.ac.ss.entities.RoadAccidentFund;
import za.ac.ss.repository.DailyJobsRepository;
import za.ac.ss.service.faces.AppointmentService;
import za.ac.ss.service.faces.Notification;
import za.ac.ss.service.faces.RAFService;

@Slf4j
@Component
public class MedicoReminderService implements Notification {

	@Autowired private AppointmentService service;
	@Autowired private DailyJobsRepository repository;
	@Autowired private RAFService rafService;

	@Override
	@Scheduled(cron = "0 0 10 1/31 * ?")
	public void process() throws InterruptedException {
		log.info("running process on thread {}", Thread.currentThread());
		ExecutorService executorService = Executors.newWorkStealingPool();
		CompletableFuture.supplyAsync(() -> service.checkAllMedicoDueIn3Days(), executorService).thenApply((raf) -> sendNotifications(raf)).thenApply((raf) -> initProcess(raf));
		CompletableFuture.runAsync(() -> log.info("Completed"));
        executorService.shutdown();
	}

	private String initProcess(RoadAccidentFund raf) {
		try {
			HistoryJobs dailyJobs = HistoryJobs.builder().id(UUID.randomUUID()).jobContent(raf.toString()).name("Medico_Reminder").status(true).executionTime(LocalDateTime.now()).build();
			repository.save(dailyJobs);
			this.removeJobs();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "save data to database";
	}

	private void removeJobs() {
		log.info("removed yesterday's data from the application");
	}

	private RoadAccidentFund sendNotifications(Stream<DoctorAppointment> doctorAppointment) {
		Optional<RoadAccidentFund> raf = null;
		for (DoctorAppointment appointments : doctorAppointment.collect(Collectors.toList())) {
			raf = this.rafService.findByDoctorAppointmentId(appointments.getId());
			log.info("sending email to {} ", raf.get().getPerson().getEmail());
		}
		return raf.get();
	}
}
