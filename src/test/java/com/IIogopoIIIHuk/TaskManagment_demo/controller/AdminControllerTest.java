package com.IIogopoIIIHuk.TaskManagment_demo.controller;

import com.IIogopoIIIHuk.TaskManagment_demo.entity.Task;
import com.IIogopoIIIHuk.TaskManagment_demo.repo.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest {

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final AdminController adminController = new AdminController(taskRepository);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();

    @Test
    void addTaskTest() throws Exception {
        mockMvc.perform(post("/api/admin/task/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "title": "Test Task",
                            "description": "Test Description",
                            "status": "New",
                            "priority": "1",
                            "comments": "First comment",
                            "authorTask": "Admin",
                            "executorTask": "User"
                        }
                        """))
                .andExpect(status().isOk()); // Ожидаем 200 OK

        verify(taskRepository, times(1)).save(any(Task.class)); // Проверяем вызов метода save()
    }

    @Test
    void getAllTaskTest() throws Exception {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(
                new Task(1L, "Task1", "Desc1", "New", "1", "Comment1", "Admin", "User"),
                new Task(2L, "Task2", "Desc2", "InProgress", "2", "Comment2", "Admin", "User2")
        ));

        mockMvc.perform(get("/api/admin/task/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTasksByAuthorTest() throws Exception {
        when(taskRepository.findAllByAuthorTask("Admin")).thenReturn(Collections.singletonList(
                new Task(1L, "Task1", "Desc1", "New", "1", "Comment1", "Admin", "User")
        ));

        mockMvc.perform(get("/api/admin/task/author-tasks")
                        .param("authorTask", "Admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(taskRepository, times(1)).findAllByAuthorTask("Admin");
    }

    @Test
    void updateTaskTest() throws Exception {
        Task task = new Task(1L, "Updated Task", "Updated Desc", "Done", "1", "Updated comment", "Admin", "User");
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/admin/task/update")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "id": 1,
                            "title": "Updated Task",
                            "description": "Updated Desc",
                            "status": "Done",
                            "priority": "1",
                            "comments": "Updated comment",
                            "authorTask": "Admin",
                            "executorTask": "User"
                        }
                        """))
                .andExpect(status().isOk());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTaskTest() throws Exception {
        doNothing().when(taskRepository).deleteById(1L);

        mockMvc.perform(delete("/api/admin/task/delete")
                        .param("id", "1"))
                .andExpect(status().isOk());

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
