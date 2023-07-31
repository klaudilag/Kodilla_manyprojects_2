package com.crud.tasks.controller;
import com.crud.tasks.controller.TaskController;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;
    @InjectMocks
    private TaskController taskController;
    private List<Task> mockTasks;

    @BeforeEach
    void setUp() {
        mockTasks = List.of(
                new Task(1L, "Task 1", "Content 1"),
                new Task(2L, "Task 2", "Content 2")
        );
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        // Ustawienie zachowania mocka dla serwisu
        when(dbService.getAllTasks()).thenReturn(mockTasks);

        // Ustawienie zachowania mocka dla mapper'a
        when(taskMapper.mapToTaskDtoList(mockTasks)).thenReturn(List.of(
                new TaskDto(1L, "Task 1", "Content 1"),
                new TaskDto(2L, "Task 2", "Content 2")
        ));

        // Wywołanie metody kontrolera i weryfikacja wyników
        mockMvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(mockTasks.size()))
                .andExpect(jsonPath("$[0].id").value(mockTasks.get(0).getId()))
                .andExpect(jsonPath("$[0].title").value(mockTasks.get(0).getTitle()))
                .andExpect(jsonPath("$[0].content").value(mockTasks.get(0).getContent()))
                .andExpect(jsonPath("$[1].id").value(mockTasks.get(1).getId()))
                .andExpect(jsonPath("$[1].title").value(mockTasks.get(1).getTitle()))
                .andExpect(jsonPath("$[1].content").value(mockTasks.get(1).getContent()));
    }

    @Test
    void shouldDeleteTaskById() throws Exception {
        Long taskIdToDelete = 1L;
        mockMvc.perform(delete("/v1/tasks/{taskid}",taskIdToDelete))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateTask() throws Exception {
        TaskDto taskDtoToCreate = new TaskDto(null, "New Task", "New Content");
        Task createdTask = new Task(1L, "New Task", "New Content");


        when(taskMapper.mapToTask(taskDtoToCreate)).thenReturn(createdTask);

        Gson gson = new Gson();
        String jsonString = gson.toJson(taskDtoToCreate);

        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());

    }

    @Test
    void shouldUpdateTask() throws Exception {

        TaskDto taskDtoToUpdate = new TaskDto(1L, "Task Task", "Task Content");
        Task taskToUpdate = new Task(1L, "Task Task", "Task Content");

        TaskDto taskDtoUpdating = new TaskDto(1L, "Updated!", "Updated!");
        Task taskUpdating = new Task(1L, "Updated!", "Updated!");

        when(taskMapper.mapToTask(taskDtoUpdating)).thenReturn(taskUpdating);

        when(dbService.saveTask(taskUpdating)).thenReturn(taskUpdating);

        when(taskMapper.mapToTask(taskDtoToUpdate)).thenReturn(taskToUpdate);

        when(dbService.saveTask(taskToUpdate)).thenReturn(taskToUpdate);

        Gson gson = new Gson();
        String jsonUpdating = gson.toJson(taskUpdating);
        String jsonUpdated = gson.toJson(taskToUpdate);

        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdated))
                .andExpect(status().isOk());
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdating))
                .andExpect(status().isOk());

    }

}