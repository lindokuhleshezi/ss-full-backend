package za.ac.ss.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.FileStore;
import za.ac.ss.enums.DocumentProcess;

@Repository
public interface DocumentRepository  extends JpaRepository<FileStore, Long> {
	Optional<FileStore> findByName(String filename);
	List<FileStore> findAllByRoadAccidentFundId(Long id);
	List<FileStore> findByDocumentProcess(DocumentProcess pending);
	void deleteByName(String name);
}
