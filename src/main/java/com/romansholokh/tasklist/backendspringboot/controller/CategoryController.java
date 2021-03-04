package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Category;
import com.romansholokh.tasklist.backendspringboot.service.CategoryService;
import com.romansholokh.tasklist.backendspringboot.search.CategorySearchValues;
import com.romansholokh.tasklist.backendspringboot.util.Logger;
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
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public List<Category> getAll()
    {
        Logger.printClassMethodName(Thread.currentThread());
        List<Category> list = categoryService.getAll();

        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category)
    {
        Logger.printClassMethodName(Thread.currentThread());
        if (category.getId() != null && category.getId() != 0)
        {
            return new ResponseEntity("redundant param: id MUST NOT exist or MUST be null or 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (category.getTitle() == null || category.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryService.add(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category)
    {
        Logger.printClassMethodName(Thread.currentThread());
        if (category.getId() == null || category.getId() <= 0)
        {
            return new ResponseEntity("missed param or invalid format: id MUST be greater than 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (!categoryService.existById(category.getId()))
        {
            return new ResponseEntity("id = " + category.getId() + " does not exist", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (category.getTitle() == null || category.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        
        return ResponseEntity.ok(categoryService.update(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id)
    {
        Logger.printClassMethodName(Thread.currentThread());
        Category category = null;
        Optional<Category> optional = categoryService.getById(id);
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
        Logger.printClassMethodName(Thread.currentThread());
        try
        {
            categoryService.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok("Category with id = " + id + " was deleted");
    }

//    Search by any parameters CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues)
    {
        Logger.printClassMethodName(Thread.currentThread());
//        If the text is empty or null instead of the text, will be returned all categories
        return ResponseEntity.ok(categoryService.search(categorySearchValues.getTitle()));
    }

}
