package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Priority;
import com.romansholokh.tasklist.backendspringboot.service.PriorityService;
import com.romansholokh.tasklist.backendspringboot.search.PrioritySearchValues;
import com.romansholokh.tasklist.backendspringboot.util.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/priority")
@CrossOrigin(origins = "http://localhost:4200") //Allow this resource to receive data from the backend
public class PriorityController
{
    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService)
    {
        this.priorityService = priorityService;
    }

    @GetMapping("/getAll")
    public List<Priority> getAll()
    {
        Logger.printClassMethodName(Thread.currentThread());
        List<Priority> list = priorityService.getAll();

        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority)
    {
        Logger.printClassMethodName(Thread.currentThread());
        if(priority.getId() != null && priority.getId() != 0)
        {
            return new ResponseEntity("redundant param: id MUST NOT exist or MUST be null or 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (priority.getTitle() == null || priority.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (priority.getColor() == null
                || priority.getColor().trim().length() == 0)
        {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (!(priority.getColor().startsWith("#")))
        {
            return new ResponseEntity("invalid format: color code must starts with '#' sign",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityService.add(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> update(@RequestBody Priority priority)
    {
        Logger.printClassMethodName(Thread.currentThread());
        if (priority.getId() == null || priority.getId() <= 0)
        {
            return new ResponseEntity("missed param or invalid format: id MUST be greater than 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (!priorityService.existById(priority.getId()))
        {
            return new ResponseEntity("id = " + priority.getId() + " does not exist", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (priority.getTitle() == null || priority.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (priority.getColor() != null && !(priority.getColor().startsWith("#")))
        {
            return new ResponseEntity("invalid format: color code must starts with '#' sign",
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityService.update(priority));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> getById(@PathVariable Long id)
    {
        Logger.printClassMethodName(Thread.currentThread());
        Priority priority = null;
        Optional<Priority> optional = priorityService.getById(id);
        if (optional.isPresent())
        {
            priority = optional.get();
        }
        else
        {
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priority);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id)
    {
        Logger.printClassMethodName(Thread.currentThread());
        try
        {
            priorityService.deleteById(id);
        }
        catch (EmptyResultDataAccessException e)
        {
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok("Priority with id = " + id + " was deleted");
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues)
    {
        Logger.printClassMethodName(Thread.currentThread());
        return ResponseEntity.ok(priorityService.search(prioritySearchValues.getTitle()));
    }
}
