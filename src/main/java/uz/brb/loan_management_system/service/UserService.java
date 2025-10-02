package uz.brb.loan_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.UserDto;
import uz.brb.loan_management_system.entity.AuthUser;

public interface UserService {
    Response get(Long id);

    Response getAll(Pageable pageable);

    Response update(UserDto userDto);

    Response me(AuthUser authUser);

    Response roleStatistics();

    Response search(String fullName, String username);
}
