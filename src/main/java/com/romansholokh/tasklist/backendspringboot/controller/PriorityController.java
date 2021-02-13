package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Priority;
import com.romansholokh.tasklist.backendspringboot.repo.PriorityRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Priority> list = priorityRepository.findAll();

        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority)
    {
        if(priority.getId() != null && priority.getId() != 0)
        {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
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
}
