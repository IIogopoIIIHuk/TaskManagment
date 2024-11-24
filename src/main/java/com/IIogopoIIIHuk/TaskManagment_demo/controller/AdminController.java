package com.IIogopoIIIHuk.TaskManagment_demo.controller;

import com.IIogopoIIIHuk.TaskManagment_demo.DTO.TaskDTO;
import com.IIogopoIIIHuk.TaskManagment_demo.entity.Task;
import com.IIogopoIIIHuk.TaskManagment_demo.repo.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "admin-methods")
@RequestMapping("/api/admin/task")
public class AdminController {

    private final TaskRepository taskRepository;

    @Autowired
    public AdminController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    //Post Requests
    @Operation(
            summary = "in this method admin adding task in database",
            description = "getting service DTO and Builder create and save in database"
    )
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public void addTask(@RequestBody TaskDTO taskDTO){

        log.info("New row: " + taskRepository.save(
                Task.builder()
                        .title(taskDTO.getTitle())
                        .description(taskDTO.getDescription())
                        .status(taskDTO.getStatus())
                        .priority(taskDTO.getPriority())
                        .comments(taskDTO.getComments())
                        .authorTask(taskDTO.getAuthorTask())
                        .executorTask(taskDTO.getExecutorTask())
                        .build()
        ));
    }

    //Get Requests
    @Operation(
            summary = "admin is getting all tasks from database"
    )
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    @Operation(
            summary = "admin is getting tasks by name author task"
    )
    @GetMapping("/author-tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getTasksByAuthor(@RequestParam String authorTask){
        return taskRepository.findAllByAuthorTask(authorTask);
    }

    @Operation(
            summary = "admin is getting tasks by name executor task"
    )
    @GetMapping("/executor-tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Task> getTasksByExecutor(@RequestParam String executorTask){
        return taskRepository.findAllByExecutorTask(executorTask);
    }

    //Put Requests
    @Operation(
            summary = "admin can updating full task or some date"
    )
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateTask(@RequestParam Long id, @RequestBody Task task){
        if(!taskRepository.existsById(task.getId())){
            return "Task not found";
        }
        return taskRepository.save(task).toString();
    }

    //Delete Requests
    @Operation(
            summary = "admin can delete task from database"
    )
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTask(@RequestParam Long id){
        taskRepository.deleteById(id);
    }

}
