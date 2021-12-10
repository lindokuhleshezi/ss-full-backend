package za.ac.ss.service.faces;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import za.ac.ss.entities.Category;
import za.ac.ss.exception.ResourceNotFoundException;


@Secured ({"ROLE_Admin", "ROLE_Employee", "ROLE_Customer"})
public interface CategoryService {

	public List<Category> getCategories();
	public Optional<Category> getCategory(Long id) throws ResourceNotFoundException;
	public ResponseEntity<Void> deleteById(Long id);
	public Category save(@Valid Category category);
	public Category update(Long id, Category category);
	public List<Category> parentCategory();
	public List<Category> childCategory(Long id);
	public List<Category> getChildren();

}
