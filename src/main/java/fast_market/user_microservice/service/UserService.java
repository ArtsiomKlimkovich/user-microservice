package fast_market.user_microservice.service;

import fast_market.user_microservice.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    UserDto updateUser(UserDto userDto, Long userid);

    void deleteUser(Long userId);
}