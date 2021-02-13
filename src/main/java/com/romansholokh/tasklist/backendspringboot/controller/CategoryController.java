package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Category;
import com.romansholokh.tasklist.backendspringboot.repo.CategoryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController
{
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/getAll")
    public List<Category> getAll()
    {
        List<Category> list = categoryRepository.findAll();

        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category)
    {
        if (category.getId() != null && category.getId() != 0)
        {
            return new ResponseEntity("redundant param: id MUST NOT exist or MUST be null or 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (category.getTitle() == null || category.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category)
    {
        if (category.getId() == null || category.getId() <= 0)
        {
            return new ResponseEntity("missed param or invalid format: id MUST be greater than 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (category.getTitle() == null || category.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id)
    {
        Category category = null;
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent())
        {
            category = optional.get();
        }
        else
        {
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id)
    {
        try
        {
            categoryRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok("Category with id = " + id + " was deleted");
    }
}
