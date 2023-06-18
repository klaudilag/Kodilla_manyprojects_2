package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findAll();
    Task findTasksById(@Param("ID") Long id);
    Task save(Task task);
    Optional<Task> findById(Long id);
    @Transactional
    void deleteTaskById(Long id);

}
