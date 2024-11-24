package com.IIogopoIIIHuk.TaskManagment_demo.DTO;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String title;
    private String status;
    private String comments;
}
