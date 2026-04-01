package com.hexa.hexagonal_demo.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.hexagonal_demo.application.mapper.AddressMapper;
import com.hexa.hexagonal_demo.application.mapper.UserMapper;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserCommand;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetAddressQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetAddressUseCase;
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
    private final GetAddressUseCase getAddressUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase,
            GetUserByCpfUseCase getUserByCpfUseCase, GetAddressUseCase getAddressUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.getUserByCpfUseCase = getUserByCpfUseCase;
        this.getAddressUseCase = getAddressUseCase;
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

    @GetMapping("/address/{cep}")
    public ResponseEntity<AddressResponse> getAddress(@PathVariable String cep) {
        // 1. Cria a Query com o dado da URL
        var query = new GetAddressQuery(cep);

        // 2. Executa o Caso de Uso
        var address = getAddressUseCase.execute(query);

        // 3. Mapeia para o DTO de resposta e retorna 200 OK
        return ResponseEntity.ok(AddressMapper.toResponse(address));
    }
}
