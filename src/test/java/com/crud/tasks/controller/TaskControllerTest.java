package com.crud.tasks.controller;
import com.crud.tasks.controller.TaskController;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        // Przygotowanie przykładowych danych do testów
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

        // Weryfikacja, czy metoda serwisu została wywołana tylko raz
        verify(dbService, times(1)).getAllTasks();
    }

    @Test
    void shouldDeleteTaskById() throws Exception {
        Long taskIdToDelete = 1L;
        // Ustawienie zachowania mocka dla serwisu
        doNothing().when(dbService).deleteTaskById(taskIdToDelete);

        // Wywołanie metody kontrolera i weryfikacja statusu odpowiedzi
        mockMvc.perform(delete("/v1/tasks/{taskId}", taskIdToDelete))
                .andExpect(status().isOk());

        // Weryfikacja, czy metoda serwisu została wywołana tylko raz z odpowiednim ID zadania
        verify(dbService, times(1)).deleteTaskById(taskIdToDelete);
    }

    @Test
    void shouldCreateTask() throws Exception {
        TaskDto taskDtoToCreate = new TaskDto(null, "New Task", "New Content");
        Task createdTask = new Task(1L, "New Task", "New Content");

        // Ustawienie zachowania mocka dla mapper'a
        when(taskMapper.mapToTask(taskDtoToCreate)).thenReturn(createdTask);

        // Wywołanie metody kontrolera i weryfikacja statusu odpowiedzi
        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskDtoToCreate)))
                .andExpect(status().isOk());

        // Weryfikacja, czy metoda serwisu została wywołana tylko raz z odpowiednim zadaniem
        verify(dbService, times(1)).saveTask(createdTask);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Przygotowanie przykładowych danych do testu
        TaskDto taskDtoToUpdate = new TaskDto(1L, "Updated Task", "Updated Content");
        Task updatedTask = new Task(1L, "Updated Task", "Updated Content");

        // Konfiguracja zachowania mocka dla mapper'a
        when(taskMapper.mapToTask(taskDtoToUpdate)).thenReturn(updatedTask);
        // Konfiguracja zachowania mocka dla serwisu - wywołanie saveTask()
        when(dbService.saveTask(updatedTask)).thenReturn(updatedTask);

        // Wywołanie metody kontrolera i weryfikacja statusu odpowiedzi
        mockMvc.perform(put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskDtoToUpdate)))
                .andExpect(status().isOk());

        // Weryfikacja, czy metoda serwisu została wywołana tylko raz z odpowiednim zadaniem
        verify(dbService, times(1)).saveTask(updatedTask);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}