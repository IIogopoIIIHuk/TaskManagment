package com.IIogopoIIIHuk.TaskManagment_demo.controller;

import com.IIogopoIIIHuk.TaskManagment_demo.DTO.UpdateTaskRequest;
import com.IIogopoIIIHuk.TaskManagment_demo.entity.Task;
import com.IIogopoIIIHuk.TaskManagment_demo.repo.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void getUserTasks_ShouldReturnTasksForCurrentUser() {
        // Arrange
        String currentUser = "testUser";
        when(authentication.getName()).thenReturn(currentUser);

        Task task1 = new Task(1L, "Task 1", "Description 1", "in progress", "low", "admin", currentUser, null);
        Task task2 = new Task(2L, "Task 2", "Description 2", "completed", "high", "admin", currentUser, null);
        when(taskRepository.findAllByExecutorTask(currentUser)).thenReturn(List.of(task1, task2));

        // Act
        List<Task> result = userController.getUserTasks();

        // Assert
        assertThat(result).containsExactly(task1, task2);
        verify(taskRepository, times(1)).findAllByExecutorTask(currentUser);
    }


    @Test
    void updateTaskStatusAndComment_ShouldThrowNotFound_WhenTaskDoesNotExist() {
        // Arrange
        String currentUser = "testUser";
        when(authentication.getName()).thenReturn(currentUser);

        UpdateTaskRequest request = new UpdateTaskRequest("Nonexistent Task", "Comment", "status");
        when(taskRepository.findTaskByTitleAndExecutorTask(request.getTitle(), currentUser))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userController.updateTaskStatusAndComment(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.toString());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTaskStatusAndComment_ShouldThrowForbidden_WhenTaskBelongsToAnotherUser() {
        // Arrange
        String currentUser = "testUser";
        when(authentication.getName()).thenReturn(currentUser);

        UpdateTaskRequest request = new UpdateTaskRequest("Task 1", "Comment", "status");

        // Задача, которая принадлежит другому пользователю
        Task existingTask = new Task(1L, "Task 1", "Description", "in progress", "low", "admin", "otherUser", "Some comment");

        when(taskRepository.findTaskByTitleAndExecutorTask(request.getTitle(), currentUser))
                .thenReturn(Optional.of(existingTask));

        // Act & Assert
        assertThatThrownBy(() -> userController.updateTaskStatusAndComment(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.FORBIDDEN.toString());

        verify(taskRepository, never()).save(any(Task.class));
    }



}
