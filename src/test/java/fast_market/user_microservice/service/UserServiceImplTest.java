package fast_market.user_microservice.service;

import fast_market.user_microservice.dto.UserDto;
import fast_market.user_microservice.entity.User;
import fast_market.user_microservice.mapper.UserMapper;
import fast_market.user_microservice.repository.UserRepository;
import fast_market.user_microservice.service.ServiceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    private UserDto userDto;
    private User user;

    @BeforeEach
    public void setUp(){
        userDto = new UserDto(1L, "John Joe", "john@gmail.com", "password123");
        user = new User(1L, "John Joe", "john@gmail.com", "password123");
    }

    @Test
    public void createUser_ShouldCreateUser_WhenUserDoesNotExist() {
        when(userRepository.findUserByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(userMapper.dtoToUser(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToDto(user)).thenReturn(userDto);

        UserDto createdUser = userServiceImpl.createUser (userDto);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    public void getUserById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userToDto(user)).thenReturn(userDto);

        UserDto foundUser = userServiceImpl.getUserById(1L);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserId()).isEqualTo(1L);
    }

    @Test
    public void updateUser_ShouldUpdateUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.dtoToUser(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToDto(user)).thenReturn(userDto);

        UserDto updatedUser = userServiceImpl.updateUser (userDto, 1L);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmail()).isEqualTo(userDto.getEmail());
    }

    @Test
    public void deleteUser_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userServiceImpl.deleteUser(1L);

    }
}
