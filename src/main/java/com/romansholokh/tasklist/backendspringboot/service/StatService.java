package com.romansholokh.tasklist.backendspringboot.service;

import com.romansholokh.tasklist.backendspringboot.entity.Stat;
import com.romansholokh.tasklist.backendspringboot.repo.StatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StatService
{
    private final StatRepository statRepository;

    public StatService(StatRepository statRepository)
    {
        this.statRepository = statRepository;
    }

    public Optional<Stat> getById1(Long defaultId)
    {
        return statRepository.findById(defaultId);
    }
}
