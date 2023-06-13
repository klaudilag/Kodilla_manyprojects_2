package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @GetMapping
    public List<TaskDto> getTasks(){
        return new ArrayList<>();
    }
    @GetMapping
    public TaskDto getTask(@PathVariable Long taskid){
        return new TaskDto(1L, "test title", "test_content");
    }
    @DeleteMapping
    public void deleteTask(Long taskid){
    }
    @PutMapping
    public TaskDto updateTask(TaskDto taskDto){
        return new TaskDto(1L, "edited title", "edited content");
    }
    @PostMapping
    public void createTask(TaskDto taskDto){

    }

}
