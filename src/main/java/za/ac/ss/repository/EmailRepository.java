package za.ac.ss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.EmailTemplate;

@Repository
public interface EmailRepository extends JpaRepository<EmailTemplate, Long> {

}
