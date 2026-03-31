package com.hexa.hexagonal_demo.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.hexagonal_demo.application.mapper.UserMapper;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserCommand;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetUserByCpfQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetUserByCpfUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetUserQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetUserUseCase;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserByCpfUseCase getUserByCpfUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase, GetUserByCpfUseCase getUserByCpfUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.getUserByCpfUseCase = getUserByCpfUseCase;
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("A API está no ar! Para criar um usuário, envie um POST /users");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserRequest request) {

        var user = createUserUseCase.execute(
                new CreateUserCommand(request.name(), request.email(), request.cpf()));
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        var user = getUserUseCase.execute(new GetUserQuery(id));
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf) {
        var user = getUserByCpfUseCase.execute(new GetUserByCpfQuery(cpf));
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }
}
