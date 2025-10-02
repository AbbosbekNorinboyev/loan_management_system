package uz.brb.loan_management_system.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.brb.loan_management_system.dto.Response;
import uz.brb.loan_management_system.dto.ShortDto;
import uz.brb.loan_management_system.dto.UserDto;
import uz.brb.loan_management_system.entity.AuthUser;
import uz.brb.loan_management_system.enums.Role;
import uz.brb.loan_management_system.exception.ResourceNotFoundException;
import uz.brb.loan_management_system.mapper.UserMapper;
import uz.brb.loan_management_system.repository.AuthUserRepository;
import uz.brb.loan_management_system.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthUserRepository authUserRepository;
    private final EntityManager entityManager;
    private final UserMapper userMapper;

    @Override
    public Response get(Long id) {
        AuthUser authUser = authUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully found")
                .success(true)
                .data(userMapper.toDto(authUser))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response getAll(Pageable pageable) {
        List<AuthUser> users = authUserRepository.findAll(pageable).getContent();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser list successfully found")
                .data(userMapper.dtoList(users))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response update(UserDto userDto) {
        AuthUser authUser = authUserRepository.findById(userDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + userDto.getId()));
        userMapper.update(authUser, userDto);
        authUserRepository.save(authUser);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully updated")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response me(AuthUser authUser) {
        if (authUser == null) {
            return Response.builder()
                    .message("USER IS NULL")
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("AuthUser successfully found")
                .success(true)
                .data(userMapper.toDto(authUser))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response roleStatistics() {
        List<Tuple> roleStatistics = authUserRepository.roleStatistics();
        List<ShortDto> shortDtoList = new ArrayList<>();
        for (Tuple roleStatistic : roleStatistics) {
            shortDtoList.add(
                    new ShortDto(
                            String.valueOf(roleStatistic.get(1, Role.class)),
                            roleStatistic.get(0, Long.class)
                    )
            );
        }
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Role statistics")
                .data(shortDtoList)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response search(String fullName, String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthUser> query = builder.createQuery(AuthUser.class);
        Root<AuthUser> root = query.from(AuthUser.class);

        List<Predicate> predicates = new ArrayList<>();

        if (fullName != null && !fullName.isEmpty()) {
            predicates.add(builder.like(root.get("fullName"), "%" + fullName + "%"));
        }
        if (username != null && !username.isEmpty()) {
            predicates.add(builder.like(root.get("username"), "%" + username + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<AuthUser> authUsers = entityManager.createQuery(query).getResultList();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully found")
                .data(userMapper.dtoList(authUsers))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
