package com.gifprojects.membersphere.repository;

import com.gifprojects.membersphere.model.Task;

import java.util.List;

public interface ITaskRepository {
    boolean saveTask(Task task);
    List<Task> getTasksByUserId(long id);
    boolean updateTask(long taskId);
    boolean deleteTaskById(long id);
}