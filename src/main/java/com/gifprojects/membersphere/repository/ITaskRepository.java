package com.gifprojects.membersphere.repository;

import com.gifprojects.membersphere.model.Task;

import java.util.List;

public interface ITaskRepository {
    void saveTask(Task task);
    List<Task> getTasksByEmail(String email);
    void updateTask(long taskId, boolean isCompleted);
    void deleteTaskById(Long id);
}