package za.ac.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.ac.ss.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByParentId(Long id);
	List<Category> findAllByParentNull();
	List<Category> findAllByParentNotNull();

}
