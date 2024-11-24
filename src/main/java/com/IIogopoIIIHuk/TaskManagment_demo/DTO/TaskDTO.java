package com.IIogopoIIIHuk.TaskManagment_demo.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDTO {

    private String title;
    private String description;
    private String status;
    private String priority;
    private String comments;
    private String authorTask;
    private String executorTask;


    public TaskDTO() {

    }
}
