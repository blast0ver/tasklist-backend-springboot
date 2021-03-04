package com.romansholokh.tasklist.backendspringboot.service;

import com.romansholokh.tasklist.backendspringboot.entity.Category;
import com.romansholokh.tasklist.backendspringboot.repo.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
/*
All methods of the class must be executed without errors for the transaction to complete.
If an exception occurs in the method, all the performed operations will be rolled back
*/
@Transactional
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll()
    {
        return categoryRepository.findAll();
    }

    public Category add(Category category)
    {
        return categoryRepository.save(category);
    }

    public Category update(Category category)
    {
        return categoryRepository.save(category);
    }

    public Boolean existById(Long id)
    {
        return categoryRepository.existsById(id);
    }

    public Optional<Category> getById(Long id)
    {
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id)
    {
        categoryRepository.deleteById(id);
    }

    public List<Category> search(String title)
    {
        return categoryRepository.findByTitle(title);
    }

}
