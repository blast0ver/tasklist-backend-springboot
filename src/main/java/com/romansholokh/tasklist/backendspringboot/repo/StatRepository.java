package com.romansholokh.tasklist.backendspringboot.repo;

import com.romansholokh.tasklist.backendspringboot.entity.Stat;
import org.springframework.data.repository.CrudRepository;

public interface StatRepository extends CrudRepository<Stat, Long>
{
}
