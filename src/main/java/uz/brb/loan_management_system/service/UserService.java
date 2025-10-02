package uz.brb.loan_management_system.service;

import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.entity.AuthUser;

public interface UserService {
    Response me(AuthUser authUser);
}
