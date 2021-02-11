package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Priority;
import com.romansholokh.tasklist.backendspringboot.repo.PriorityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    public List<Priority> test()
    {
        List<Priority> list = priorityRepository.findAll();
        System.out.println("list = " + list);

        return list;
    }
}
