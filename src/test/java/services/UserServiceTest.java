package services;

import com.gifprojects.membersphere.model.Employee;
import com.gifprojects.membersphere.model.Manager;
import com.gifprojects.membersphere.model.User;
import com.gifprojects.membersphere.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import com.gifprojects.membersphere.repository.IUserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userServiceMock;
    private IUserRepository userRepositoryMock;

    @BeforeEach
    void setUp(){
        userRepositoryMock = Mockito.mock(IUserRepository.class);
        userServiceMock = new UserService(userRepositoryMock);
    }

    @Test
    void shouldRegisterManagerCorrectly() {
        String role = "MANAGER";
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        //when:
        userServiceMock.registerAccount("test", "123","test@test.com", "0784 322 325", role);
        //then:
        verify(userRepositoryMock).saveUser(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertTrue(savedUser instanceof Manager, "It should be MANAGER.");
        assertEquals("test@test.com", savedUser.getEmail());
    }

    @Test
    void loginShouldReturnUserWhenPasswordIsCorrect(){
        String username = "test";
        String password = "1234";

        User mockUser = Employee.createEmployee(username, password, "test@test.com", "0723 223 323");

        when(userRepositoryMock.getUserByUsername(username)).thenReturn(mockUser);

        User result = userServiceMock.loginAccount(username, password);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

}
