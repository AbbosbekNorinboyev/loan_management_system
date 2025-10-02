package uz.brb.loan_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.brb.loan_management_system.dto.UserDto;
import uz.brb.loan_management_system.entity.AuthUser;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public AuthUser toEntity(UserDto userDto) {
        return AuthUser.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
    }

    public UserDto toDto(AuthUser authUser) {
        return UserDto.builder()
                .id(authUser.getId())
                .username(authUser.getUsername())
                .password(authUser.getPassword())
                .role(authUser.getRole())
                .build();
    }

    public List<UserDto> dtoList(List<AuthUser> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(AuthUser entity, UserDto request) {
        if (request == null) {
            return;
        }
        if (request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
            entity.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            entity.setPassword(request.getPassword());
        }
    }
}
