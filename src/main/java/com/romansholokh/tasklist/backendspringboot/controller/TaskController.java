package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Task;
import com.romansholokh.tasklist.backendspringboot.service.TaskService;
import com.romansholokh.tasklist.backendspringboot.search.TaskSearchValues;
import com.romansholokh.tasklist.backendspringboot.util.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController
{
    private TaskService taskService;

    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }

    @GetMapping("/getAll")
    public List<Task> getAll()
    {
        Logger.printClassMethodName(Thread.currentThread());
        List<Task> list = taskService.getAll();

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
        else if (task.getCompleted() == null || task.getCompleted() < 0 || task.getCompleted() > 1)
        {
            return new ResponseEntity("missed param or invalid format: completed must be 0 or 1", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskService.add(task));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Task task)
    {
        Logger.printClassMethodName(Thread.currentThread());
        if (task.getId() == null || task.getId() <= 0)
        {
            return new ResponseEntity("missed param or invalid format: id MUST be greater than 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (!taskService.existById(task.getId()))
        {
            return new ResponseEntity("id = " + task.getId() + " does not exist", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (task.getTitle() == null || task.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (task.getCompleted() == null || task.getCompleted() < 0 || task.getCompleted() > 1)
        {
            return new ResponseEntity("missed param or invalid format: completed must be 0 or 1", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskService.update(task));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id)
    {
        Logger.printClassMethodName(Thread.currentThread());
        Task task = null;
        Optional<Task> optional = taskService.getById(id);
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
            taskService.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok("Task with id = " + id + " was deleted");
    }

    //    Search by any parameters TaskSearchValues
    @PostMapping("/search")
    public ResponseEntity search(@RequestBody TaskSearchValues taskSearchValues)
    {
        Logger.printClassMethodName(Thread.currentThread());
//        Exclude NullPointerException
        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;
        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;
        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;
        Sort.Direction direction = sortDirection == null ||
                sortDirection.trim().equals("") ||
                sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

//        Sorting object
        /*
        If sortDirection is EMPTY or NULL, the default sorting direction is "ASC".
        If sortColumn is NOT EMPTY or NOT NULL, sorting is performed by sortColumn and sortDirection.
        If sortColumn is EMPTY or NULL, then sorting IS NOT USED at all
        */
        Sort sort = null;
//        If sortColumn EXIST in Task.class, sorting is performed. If it DOES NOT EXIST - sorting is not used.
        boolean isSortColumnExist = Arrays.stream(Task.class.getDeclaredFields()).anyMatch(field -> field.getName().equals(sortColumn));
        if (sortColumn != null && isSortColumnExist)
        {
            sort = Sort.by(direction, sortColumn);
        }
        else
        {
            sort = Sort.unsorted();
        }

//        Pagination object
        /*
        If pageNumber is EMPTY or NULL, the default page number is 0.
        If pageSize is NOT EMPTY or NOT NULL, pagination is performed by pageNumber and pageSize.
        If pageSize is EMPTY or NULL, then pagination IS NOT USED at all
        */
        PageRequest pageRequest = null;
        if (pageSize != null)
        {
            if (pageNumber == null)
            {
                pageNumber = 0;
            }
            pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        }
        else
        {
//            Sorting request result
            return ResponseEntity.ok(taskService.search(title, completed, priorityId, categoryId, sort));
        }

//        Paginated request result
        Page<Task> result = taskService.search(title, completed, priorityId, categoryId, pageRequest);

        return ResponseEntity.ok(result);
    }
}