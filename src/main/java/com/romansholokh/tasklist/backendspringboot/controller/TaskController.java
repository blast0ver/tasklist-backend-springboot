package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Task;
import com.romansholokh.tasklist.backendspringboot.repo.TaskRepository;
import com.romansholokh.tasklist.backendspringboot.search.TaskSearchValues;
import com.romansholokh.tasklist.backendspringboot.util.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController
{
    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/getAll")
    public List<Task> getAll()
    {
        Logger.printClassMethodName(Thread.currentThread());
        List<Task> list = taskRepository.findAll();

        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task)
    {
        Logger.printClassMethodName(Thread.currentThread());
        if (task.getId() != null && task.getId() != 0)
        {
            return new ResponseEntity("redundant param: id MUST NOT exist or MUST be null or 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (task.getTitle() == null || task.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskRepository.save(task));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Task task)
    {
        Logger.printClassMethodName(Thread.currentThread());
        if (task.getId() == null || task.getId() <= 0)
        {
            return new ResponseEntity("missed param or invalid format: id MUST be greater than 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (task.getTitle() == null || task.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        taskRepository.save(task);

        return ResponseEntity.ok("Update was successful");
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id)
    {
        Logger.printClassMethodName(Thread.currentThread());
        Task task = null;
        Optional<Task> optional = taskRepository.findById(id);
        if (optional.isPresent())
        {
            task = optional.get();
        }
        else
        {
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id)
    {
        Logger.printClassMethodName(Thread.currentThread());
        try
        {
            taskRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok("Task with id = " + id + " was deleted");
    }

    //    Search by any parameters TaskSearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Task>> search(@RequestBody TaskSearchValues taskSearchValues)
    {
        Logger.printClassMethodName(Thread.currentThread());
//        Exclude NullPointerException
        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        return ResponseEntity.ok(taskRepository.findByParams(title, completed, priorityId, categoryId));
    }
}