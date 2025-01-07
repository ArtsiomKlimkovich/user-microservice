package fast_market.user_microservice.mapper;

import fast_market.user_microservice.dto.UserDto;
import fast_market.user_microservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);
}