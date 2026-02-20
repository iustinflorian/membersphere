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
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) { this.taskService = taskService; }

    @PostMapping("/assign")
    public ResponseEntity<?> assign (@RequestBody TaskAssignDTO data){
        try {
            if (taskService.assignTask(data)) {
                return ResponseEntity.ok("Task assigned successfully.");
            } else {
                return ResponseEntity.badRequest().body("Task couldn't be assigned. Check source & destination emails.");
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @GetMapping("/{userId}/mytasks")
    public ResponseEntity<?> tasks (@PathVariable long userId){
        try{
            List<Task> userTasks = taskService.getTasksByUserId(userId);
            if (!userTasks.isEmpty()){
                return ResponseEntity.ok(userTasks);
            } else {
                return ResponseEntity.badRequest().body("No tasks available for user with ID: "+userId);
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @PatchMapping("/{taskId}/toggle")
    public ResponseEntity<?> toggle (@PathVariable long taskId){
        try{
            if (taskService.toggleStatus(taskId)){
                return ResponseEntity.ok().body("Task with ID: " + taskId + " has been updated.");
            } else {
                return ResponseEntity.badRequest().body("No task with ID: " + taskId);
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity<?> delete (@PathVariable long taskId, @RequestHeader("X-User-Id") long requesterId){
        try{
            if (taskService.deleteTaskById(taskId, requesterId)){
                return ResponseEntity.ok().body("Task with ID: " + taskId + " has been deleted.");
            } else {
                return ResponseEntity.badRequest().body("No task with ID: " + taskId);
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }
}
