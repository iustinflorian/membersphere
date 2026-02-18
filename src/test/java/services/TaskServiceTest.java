package services;

import com.gifprojects.membersphere.model.Manager;
import com.gifprojects.membersphere.model.Task;
import com.gifprojects.membersphere.model.User;
import com.gifprojects.membersphere.repository.ITaskRepository;
import com.gifprojects.membersphere.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceTest {
    private TaskService taskServiceMock;
    private ITaskRepository taskRepositoryMock;

    @BeforeEach
    void setUp(){
        taskRepositoryMock = Mockito.mock(ITaskRepository.class);
    }

    @Test
    void shouldAssignTaskCorrectlyToEmployee() {

    }
}
