package com.gifprojects.membersphere.repository;

import com.gifprojects.membersphere.model.Task;

import java.util.List;

public interface ITaskRepository {
    void saveTask(Task task);
    List<Task> getTasksById(long id);
    void updateTask(long taskId, boolean isCompleted);
    void deleteTaskById(Long id);
}