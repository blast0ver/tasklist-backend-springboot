package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Priority;
import com.romansholokh.tasklist.backendspringboot.repo.PriorityRepository;
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
    public Priority add(@RequestBody Priority priority)
    {
        return priorityRepository.save(priority);
    }
}
