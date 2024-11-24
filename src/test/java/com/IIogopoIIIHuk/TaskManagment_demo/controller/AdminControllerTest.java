package com.IIogopoIIIHuk.TaskManagment_demo.controller;

import com.IIogopoIIIHuk.TaskManagment_demo.DTO.TaskDTO;
import com.IIogopoIIIHuk.TaskManagment_demo.entity.Task;
import com.IIogopoIIIHuk.TaskManagment_demo.repo.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void addTaskTest() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Task description");

        Task task = Task.builder()
                .title("Test Task")
                .description("Task description")
                .build();

        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/admin/task/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllTaskTest() throws Exception {
        Task task = Task.builder()
                .title("Task 1")
                .description("Description 1")
                .build();

        Mockito.when(taskRepository.findAll()).thenReturn(List.of(task));

        mockMvc.perform(get("/api/admin/task/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getTasksByAuthorTest() throws Exception {
        Task task = Task.builder()
                .authorTask("Author1")
                .title("Task 1")
                .build();

        Mockito.when(taskRepository.findAllByAuthorTask(eq("Author1"))).thenReturn(List.of(task));

        mockMvc.perform(get("/api/admin/task/author-tasks")
                        .param("authorTask", "Author1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].authorTask").value("Author1"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTaskTest() throws Exception {
        Task task = Task.builder()
                .id(1L)
                .title("Updated Task")
                .build();

        Mockito.when(taskRepository.existsById(eq(1L))).thenReturn(true);
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/admin/task/update")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(content().string(task.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteTaskTest() throws Exception {
        Mockito.doNothing().when(taskRepository).deleteById(eq(1L));

        mockMvc.perform(delete("/api/admin/task/delete")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }
}
