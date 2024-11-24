package com.IIogopoIIIHuk.TaskManagment_demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "tasks")
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private String priority;

    @Column(name = "comments")
    private String comments;

    @Column(name = "author")
    private String authorTask;

    @Column(name = "executor")
    private String executorTask;


    public Task(String title, String description, String status, String priority, String comments, String authorTask, String executorTask){
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.comments = comments;
        this.authorTask = authorTask;
        this.executorTask = executorTask;
    }

    public Task(){}

    public String toString(){
        return "Task{" +
                "id= " + id +
                ", title= " + title +
                ", description= " + description +
                ", status= " + status +
                ", priority= " + priority +
                ", comments= " + comments +
                ", authorTask= " + authorTask +
                ", executorTask= " + executorTask +
                '}';
    }

}
