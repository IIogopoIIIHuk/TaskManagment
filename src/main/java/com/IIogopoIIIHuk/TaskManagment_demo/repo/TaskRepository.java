package com.IIogopoIIIHuk.TaskManagment_demo.repo;

import com.IIogopoIIIHuk.TaskManagment_demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskById(Long id);

    List<Task> findAllByExecutorTask(String executorTask);
//    Page<Task> findAllByExecutorTask(String executorTask, Pageable pageable);

    List<Task> findAllByAuthorTask(String authorTask);

    Optional<Task> findTaskByTitleAndExecutorTask(String title, String executorTask);
}
