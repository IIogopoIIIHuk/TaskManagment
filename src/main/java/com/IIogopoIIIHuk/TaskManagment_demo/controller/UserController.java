package com.IIogopoIIIHuk.TaskManagment_demo.controller;

import com.IIogopoIIIHuk.TaskManagment_demo.DTO.UpdateTaskRequest;
import com.IIogopoIIIHuk.TaskManagment_demo.entity.Task;
import com.IIogopoIIIHuk.TaskManagment_demo.repo.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "user-methods")
@RequestMapping("/api/user/task")
@RequiredArgsConstructor
public class UserController {

    private final TaskRepository taskRepository;


    @Operation(
            summary = "user is getting his tasks"
    )
    @GetMapping("/my-tasks")
    @PreAuthorize("hasRole('USER')")
    public List<Task> getUserTasks(){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        return taskRepository.findAllByExecutorTask(currentUser);
    }


    @Operation(
            summary = "user can update status and comments of his task"
    )
    @PutMapping("/update-task")
    @PreAuthorize("hasRole('USER')")
    public Task updateTaskStatusAndComment(@RequestBody UpdateTaskRequest request){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Task task = taskRepository.findTaskByTitleAndExecutorTask(request.getTitle(), currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if(!task.getExecutorTask().equals(currentUser)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not permissions to update this task");
        }
        if (request.getComments() != null){
            task.setComments(request.getComments());
        }
        if (request.getStatus() != null){
            task.setStatus(request.getStatus());
        }

        return taskRepository.save(task);
    }

}
