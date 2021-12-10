package za.ac.ss.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.Category;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.repository.CategoryRepository;
import za.ac.ss.service.faces.CategoryService;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryDao;

	@Override
	public List<Category> getCategories() {
		return categoryDao.findAll();
	}

	@Override
	public Optional<Category> getCategory(Long id) throws ResourceNotFoundException {
		return Optional.ofNullable(categoryDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Catetory %s is not found", id))));
	}

	@Override
	public ResponseEntity<Void> deleteById(Long id) {
		try {
			this.getCategory(id);
			categoryDao.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public Category save(@Valid Category category) {
		return categoryDao.save(Category.builder().id(category.getId()).required(category.getRequired())
				.status(category.getStatus()).name(category.getName()).parent(category.getParent())
				.subCategories(category.getSubCategories()).build());
	}

	@Override
	public Category update(Long id, Category category) {
		Optional<Category> categoryData = this.categoryDao.findById(id);
		
			categoryData.get().setRequired(category.getRequired());
			categoryData.get().setStatus(category.getStatus());
			categoryData.get().setName(category.getName());
			categoryData.get().setParent(category.getParent());
			
		return this.categoryDao.save(categoryData.get());
		
	}

	@Override
	public List<Category> parentCategory() {
		return categoryDao.findAllByParentNull();
	}

	@Override
	public List<Category> childCategory(Long id) {
		log.info("parentId " + id);
		return categoryDao.findAllByParentId(id);
	}

	@Override
	public List<Category> getChildren() {
		return categoryDao.findAllByParentNotNull();
	}
}
