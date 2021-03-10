package com.romansholokh.tasklist.backendspringboot.controller;

import com.romansholokh.tasklist.backendspringboot.entity.Stat;
import com.romansholokh.tasklist.backendspringboot.service.StatService;
import com.romansholokh.tasklist.backendspringboot.util.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "http://localhost:4200") //Allow this resource to receive data from the backend
public class StatController
{
    private StatService statService;

    public StatController(StatService statService)
    {
        this.statService = statService;
    }

    @GetMapping("/stat")
    public ResponseEntity<Stat> getById1()
    {
        Logger.printClassMethodName(Thread.currentThread());
        Long defaultId = 1L;
        Stat stat = null;
        Optional<Stat> optional = statService.getById1(defaultId);
        if (optional.isPresent())
        {
            stat = optional.get();
        }
        else
        {
            return new ResponseEntity("An error occurred. Stat not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(stat);
    }
}
