package uz.pdp.loan_management_system.service;

import uz.pdp.loan_management_system.dto.Response;
import uz.pdp.loan_management_system.entity.AuthUser;

public interface UserService {
    Response me(AuthUser authUser);
}
