package com.gifprojects.membersphere.services;

import com.gifprojects.membersphere.model.Employee;
import com.gifprojects.membersphere.model.Manager;
import com.gifprojects.membersphere.model.Task;
import com.gifprojects.membersphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gifprojects.membersphere.repository.ITaskRepository;
import com.gifprojects.membersphere.repository.IUserRepository;

import java.util.List;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;

    @Autowired
    public TaskService(ITaskRepository taskRepository, IUserRepository userRepository){
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void assignTask(Task task){
        User source = userRepository.getUserByEmail(task.getSource());
        User destination = userRepository.getUserByEmail(task.getDestination());

        if (source instanceof Manager && destination instanceof Employee){
            taskRepository.saveTask(task);
        } else {
            throw new RuntimeException ("Invalid task: Source must be a manager and destination must be an employee");
        }
    }

    public List<Task> getTasksByEmail(String email){
        return taskRepository.getTasksByEmail(email);
    }

    public void toggleStatus(long taskId, boolean isCompleted){
        taskRepository.updateTask(taskId, isCompleted);
    }

}
