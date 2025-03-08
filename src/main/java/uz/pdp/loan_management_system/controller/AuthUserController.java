package uz.pdp.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.loan_management_system.dto.LoginCreateDTO;
import uz.pdp.loan_management_system.dto.RegisterCreateDTO;
import uz.pdp.loan_management_system.entity.AuthUser;
import uz.pdp.loan_management_system.enums.Role;
import uz.pdp.loan_management_system.exception.CustomUserNotFoundException;
import uz.pdp.loan_management_system.repository.AuthUserRepository;
import uz.pdp.loan_management_system.security.CustomUserDetailsService;
import uz.pdp.loan_management_system.util.JWTUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetails;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterCreateDTO registerCreateDTO) {
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(registerCreateDTO.getUsername());
        if (byUsername.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        AuthUser authUser = new AuthUser();
        authUser.setUsername(registerCreateDTO.getUsername());
        authUser.setPassword(passwordEncoder.encode(registerCreateDTO.getPassword()));
        authUser.setRole(Role.USER);
        authUserRepository.save(authUser);
        return ResponseEntity.ok("AuthUser successfully register");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginCreateDTO loginCreateDTO) {
        AuthUser authUser = authUserRepository.findByUsername(loginCreateDTO.getUsername())
                .orElseThrow(() -> new CustomUserNotFoundException("Auth User not found by username: " + loginCreateDTO.getUsername()));
        if (authUser.getUsername() == null) {
            return ResponseEntity.ok().body("Username not found");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginCreateDTO.getUsername(), loginCreateDTO.getPassword())
        );
        UserDetails userDetails = customUserDetails.loadUserByUsername(loginCreateDTO.getUsername());
        String jwtGenerateToken = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(jwtGenerateToken); // login qilgandan keyin token qaytadi
    }
}
