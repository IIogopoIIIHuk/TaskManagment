package com.IIogopoIIIHuk.TaskManagment_demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskRequest {
    private String title;
    private String status;
    private String comments;


}
