package za.ac.ss.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.ss.entities.Category;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired private CategoryService categoryService;

    @GetMapping("/child")
    public List<Category> getChildrenCategories() {
        return categoryService.getChildren().stream().collect(Collectors.toList());
    }
    
    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }
    
    @GetMapping("/child/{id}")
    public List<Category> getChildCategories(@PathVariable(name="id") Long id) {
        return categoryService.childCategory(id).stream().collect(Collectors.toList());
    }
    
    @GetMapping("/parent")
    public List<Category> getParentCategories() {
        return categoryService.parentCategory();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategory(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return this.categoryService.getCategory(id);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) throws ResourceNotFoundException {
        this.categoryService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody Category category) {   
    	this.categoryService.update(id,category);
    }

    @PostMapping
    public Category save(@Valid @RequestBody Category category) {
        return categoryService.save(category);
    }
}
