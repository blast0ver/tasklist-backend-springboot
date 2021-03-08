package com.romansholokh.tasklist.backendspringboot.service;

import com.romansholokh.tasklist.backendspringboot.entity.Task;
import com.romansholokh.tasklist.backendspringboot.repo.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService
{
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAll()
    {
        return taskRepository.findAll();
    }

    public Task add(Task task)
    {
        return taskRepository.save(task);
    }

    public Task update(Task task)
    {
        return taskRepository.save(task);
    }

    public Boolean existById(Long id)
    {
        return taskRepository.existsById(id);
    }

    public Optional<Task> getById(Long id)
    {
        return taskRepository.findById(id);
    }

    public void deleteById(Long id)
    {
        taskRepository.deleteById(id);
    }

    public List<Task> search(String title,
                             Integer completed,
                             Long priorityId,
                             Long categoryId,
                             Sort sort)
    {
        return taskRepository.findByParams(title, completed, priorityId, categoryId, sort);
    }

    public Page<Task> search(String title,
                             Integer completed,
                             Long priorityId,
                             Long categoryId,
                             Pageable pageable)
    {
        return taskRepository.findByParams(title, completed, priorityId, categoryId, pageable);
    }
}
