package uz.brb.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.entity.AuthUser;
import uz.brb.loan_management_system.service.UserService;
import uz.brb.loan_management_system.util.validator.CurrentUser;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public Response me(@CurrentUser AuthUser authUser) {
        return userService.me(authUser);
    }
}
