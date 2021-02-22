package com.romansholokh.tasklist.backendspringboot.repo;

import com.romansholokh.tasklist.backendspringboot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>
{
}