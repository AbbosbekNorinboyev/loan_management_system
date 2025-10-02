package uz.brb.loan_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.entity.AuthUser;
import uz.brb.loan_management_system.mapper.UserMapper;
import uz.brb.loan_management_system.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public Response me(AuthUser authUser) {
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("AuthUser successfully found")
                .success(true)
                .data(userMapper.toDto(authUser))
                .build();
    }
}
