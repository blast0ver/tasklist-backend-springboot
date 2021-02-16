package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Priority;
import com.romansholokh.tasklist.backendspringboot.repo.PriorityRepository;
import com.romansholokh.tasklist.backendspringboot.search.PrioritySearchValues;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/priority")
public class PriorityController
{
    private PriorityRepository priorityRepository;

    public PriorityController(PriorityRepository priorityRepository)
    {
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("/getAll")
    public List<Priority> getAll()
    {
        List<Priority> list = priorityRepository.findAllByOrderByIdAsc();

        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority)
    {
        if(priority.getId() != null && priority.getId() != 0)
        {
            return new ResponseEntity("redundant param: id MUST NOT exist or MUST be null or 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (priority.getTitle() == null || priority.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (priority.getColor() == null
                || priority.getColor().trim().length() == 0
                || !(priority.getColor().startsWith("#")))
        {
            return new ResponseEntity("missed param or invalid format: color", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> update(@RequestBody Priority priority)
    {
        if (priority.getId() == null || priority.getId() <= 0)
        {
            return new ResponseEntity("missed param or invalid format: id MUST be greater than 0", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (priority.getTitle() == null || priority.getTitle().trim().length() == 0)
        {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        else if (priority.getColor() == null
                || priority.getColor().trim().length() == 0
                || !(priority.getColor().startsWith("#")))
        {
            return new ResponseEntity("missed param or invalid format: color", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> getById(@PathVariable Long id)
    {
        Priority priority = null;
        Optional<Priority> optional = priorityRepository.findById(id);
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
        try
        {
            priorityRepository.deleteById(id);
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
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValues.getText()));
    }
}
