package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {
    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }
    public Task getTaskById(Long taskId){
        return repository.findTasksById(taskId);
    }
    public Task saveTask(final Task task){
        return repository.save(task);
    }
    public Task getTask(final Long id) throws TaskNotFoundException{
        return repository.findById(id).orElseThrow(TaskNotFoundException::new);
    }
    public void deleteTaskById(final Long id){
        repository.deleteTaskById(id);
    }

}
