package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Category;
import com.romansholokh.tasklist.backendspringboot.repo.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Category add(@RequestBody Category category)
    {
        return categoryRepository.save(category);
    }
}
