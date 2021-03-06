package com.romansholokh.tasklist.backendspringboot.service;

import com.romansholokh.tasklist.backendspringboot.entity.Priority;
import com.romansholokh.tasklist.backendspringboot.repo.PriorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PriorityService
{
    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository)
    {
        this.priorityRepository = priorityRepository;
    }

    public List<Priority> getAll()
    {
        return priorityRepository.findAllByOrderByIdAsc();
    }

    public Priority add(Priority priority)
    {
        return priorityRepository.save(priority);
    }

    public Priority update(Priority priority)
    {
        return priorityRepository.save(priority);
    }

    public Boolean existById(Long id)
    {
        return priorityRepository.existsById(id);
    }

    public Optional<Priority> getById(Long id)
    {
        return priorityRepository.findById(id);
    }

    public void deleteById(Long id)
    {
        priorityRepository.deleteById(id);
    }

    public List<Priority> search(String title)
    {
        return priorityRepository.findByTitle(title);
    }
}
