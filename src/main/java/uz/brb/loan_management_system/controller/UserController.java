package uz.brb.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.UserDto;
import uz.brb.loan_management_system.entity.AuthUser;
import uz.brb.loan_management_system.service.UserService;
import uz.brb.loan_management_system.util.validator.CurrentUser;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public Response get(@RequestParam Long id) {
        return userService.get(id);
    }

    @GetMapping("/getAll")
    public Response getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return userService.getAll(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response update(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @GetMapping("/me")
    public Response me(@CurrentUser AuthUser authUser) {
        return userService.me(authUser);
    }

    @GetMapping("/roleStatistics")
    public Response roleStatistics() {
        return userService.roleStatistics();
    }

    @GetMapping("/search")
    public Response search(@RequestParam(required = false) String fullName,
                           @RequestParam(required = false) String username) {
        return userService.search(fullName, username);
    }
}
