package za.ac.ss.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.HistoryJobs;

@Repository
public interface DailyJobsRepository extends JpaRepository<HistoryJobs, UUID> {

}
