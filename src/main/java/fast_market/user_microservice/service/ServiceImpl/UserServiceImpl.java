package fast_market.user_microservice.service.ServiceImpl;

import fast_market.user_microservice.dto.UserDto;
import fast_market.user_microservice.entity.User;
import fast_market.user_microservice.exception.UserAlreadyExistsException;
import fast_market.user_microservice.exception.UserNotFoundException;
import fast_market.user_microservice.mapper.UserMapper;
import fast_market.user_microservice.repository.UserRepository;
import fast_market.user_microservice.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if(userRepository.findUserByEmail(userDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with the given email already exists.");
        }
        User savedUser = userMapper.dtoToUser(userDto);
        userRepository.save(savedUser);

        return userMapper.userToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with the given ID does not exist."));

        return userMapper.userToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userid) {
        User existingUser = userRepository.findById(userid).orElseThrow(() -> new UserNotFoundException("User with the given ID does not exist."));

        BeanUtils.copyProperties(userDto, existingUser, "userId");

        User updatedUser = userRepository.save(existingUser);

        return userMapper.userToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException("User with the given id does not exist.");
        }
        else{
            userRepository.deleteById(userId);
        }
    }
}