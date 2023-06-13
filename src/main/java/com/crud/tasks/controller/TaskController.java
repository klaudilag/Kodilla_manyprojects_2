package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    private final DbService service;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(DbService service, TaskMapper taskMapper) {
        this.service = service;
        this.taskMapper = taskMapper;
    }
    @GetMapping
    public List<TaskDto> getTasks(){
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }
    //@GetMapping(value = "{taskId}")
    //public TaskDto getTask(@PathVariable Long taskId){
    //    return new TaskDto(1L, "test title", "test_content");
    //}
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
    @GetMapping(value = "{taskId}")
    public TaskDto getTaskById(@PathVariable Long taskId){
        Task task = service.getTaskById(taskId);
        if(task == null){
            System.out.println("Brak zadania o takim id!");
        }
        return taskMapper.mapToTaskDto(task);
    }

}
