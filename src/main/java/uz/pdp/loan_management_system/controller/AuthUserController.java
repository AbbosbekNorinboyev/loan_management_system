package uz.pdp.loan_management_system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.loan_management_system.dto.LoginDto;
import uz.pdp.loan_management_system.dto.RegisterDto;
import uz.pdp.loan_management_system.entity.AuthUser;
import uz.pdp.loan_management_system.enums.Role;
import uz.pdp.loan_management_system.exception.ResourceNotFoundException;
import uz.pdp.loan_management_system.repository.AuthUserRepository;
import uz.pdp.loan_management_system.config.CustomUserDetailsService;
import uz.pdp.loan_management_system.util.JWTUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
@Slf4j
public class AuthUserController {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetails;
    private final JWTUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(registerDto.getUsername());
        if (byUsername.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        AuthUser authUser = new AuthUser();
        authUser.setUsername(registerDto.getUsername());
        authUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        authUser.setRole(Role.USER);
        authUserRepository.save(authUser);
        log.info("AuthUser successfully register");
        return ResponseEntity.ok("AuthUser successfully register");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        AuthUser authUser = authUserRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Auth User not found by username: " + loginDto.getUsername()));
        if (authUser.getUsername() == null) {
            return ResponseEntity.ok().body("Username not found");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        UserDetails userDetails = customUserDetails.loadUserByUsername(loginDto.getUsername());
        String jwtGenerateToken = jwtUtil.generateToken(userDetails.getUsername());
        log.info("AuthUser successfully login");
        return ResponseEntity.ok(jwtGenerateToken); // login qilgandan keyin token qaytadi
    }
}
