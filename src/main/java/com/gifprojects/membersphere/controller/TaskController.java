package com.gifprojects.membersphere.controller;

import com.gifprojects.membersphere.datatransfer.task.*;
import com.gifprojects.membersphere.model.Task;
import com.gifprojects.membersphere.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("api/tasks/")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) { this.taskService = taskService; }

    @PostMapping("/assign")
    public ResponseEntity<?> assign (@RequestBody TaskAssignDTO data){
        try {
            Task task = Task.createTask(
                    data.getTitle(),
                    data.getDetails(),
                    data.getSource(),
                    data.getDestination(),
                    data.getDeadline().toString()
            );
            taskService.assignTask(task);
            return ResponseEntity.ok("Task assigned successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/mytasks")
    public ResponseEntity<?> tasks (@RequestParam long id){
        try{
            List<Task> userTasks = taskService.getTasksById(id);
            return ResponseEntity.ok(userTasks);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }
}
