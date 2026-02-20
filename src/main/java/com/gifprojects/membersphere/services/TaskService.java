package com.gifprojects.membersphere.services;

import com.gifprojects.membersphere.datatransfer.task.TaskAssignDTO;
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

    public boolean assignTask(TaskAssignDTO data){
        User sourceUser = userRepository.getUserByEmail(data.getSource());
        User destinationUser = userRepository.getUserByEmail(data.getDestination());

        if (sourceUser instanceof Manager && destinationUser instanceof Employee){
            Task task = Task.createTask(
                    data.getTitle(),
                    data.getDetails(),
                    data.getSource(),
                    data.getDestination(),
                    data.getDeadline().toString()
            );
            return taskRepository.saveTask(task);
        } else {
            throw new RuntimeException ("Invalid task: source instance must be a manager and destination instance must be an employee");
        }
    }

    public List<Task> getTasksByUserId(long userId){
        return taskRepository.getTasksByUserId(userId);
    }

    public boolean toggleStatus(long taskId){
        return taskRepository.updateTask(taskId);
    }

    public boolean deleteTaskById(long taskId, long requesterId) {
        User newUser = userRepository.getUserById(requesterId);

        if (newUser instanceof Manager){
            return taskRepository.deleteTaskById(taskId);
        } else {
            throw new RuntimeException ("Requester must be of instance manager.");
        }
    }
}
