package com.romansholokh.tasklist.backendspringboot.repo;

import com.romansholokh.tasklist.backendspringboot.entity.Category;
import com.romansholokh.tasklist.backendspringboot.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long>
{
    List<Priority> findAllByOrderByIdAsc();
}
