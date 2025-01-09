package fast_market.user_microservice.controller;

import fast_market.user_microservice.dto.UserDto;
import fast_market.user_microservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto("John Joe", "john@gmail.com", "password123");
    }

    @Test
    public void createUser_ShouldReturnCreatedUser_WhenValidRequest() {
        when(userService.createUser(userDto)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.createUser(userDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    public void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userService.getUserById(1L)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUserById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    public void updateUser_ShouldReturnUpdatedUser_WhenUserExists() {
        when(userService.updateUser(userDto, 1L)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.updateUser(userDto, 1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDto);
    }

    @Test
    public void deleteUser_ShouldReturnNoContent_WhenUserDeleted() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<String> response = userController.deleteUser(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}