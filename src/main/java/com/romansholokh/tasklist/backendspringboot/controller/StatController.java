package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Stat;
import com.romansholokh.tasklist.backendspringboot.repo.StatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class StatController
{
    private StatRepository statRepository;

    public StatController(StatRepository statRepository)
    {
        this.statRepository = statRepository;
    }

    @GetMapping("/stat")
    public ResponseEntity<Stat> getById1()
    {
        Long defaultId = 1L;

        return ResponseEntity.ok(statRepository.findById(defaultId).get());

    }
}
