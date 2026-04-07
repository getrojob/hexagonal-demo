package com.hexa.hexagonal_demo.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hexa.hexagonal_demo.application.mapper.AddressMapper;
import com.hexa.hexagonal_demo.application.mapper.FeriadoMapper;
import com.hexa.hexagonal_demo.application.mapper.UserMapper;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserCommand;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetAddressQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetAddressUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetFeriadoQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetFeriadoUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetUserByCpfQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetUserByCpfUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetUserQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetUserUseCase;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Endpoints para gerenciamento de usuários, endereços e feriados")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final GetUserByCpfUseCase getUserByCpfUseCase;
    private final GetAddressUseCase getAddressUseCase;
    private final GetFeriadoUseCase getFeriadoUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase,
            GetUserByCpfUseCase getUserByCpfUseCase, GetAddressUseCase getAddressUseCase,
            GetFeriadoUseCase getFeriadoUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.getUserByCpfUseCase = getUserByCpfUseCase;
        this.getAddressUseCase = getAddressUseCase;
        this.getFeriadoUseCase = getFeriadoUseCase;
    }

    @GetMapping
    @Operation(summary = "Verifica o status da API")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("A API está no ar! Para criar um usuário, envie um POST /users");
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> create(@RequestBody CreateUserRequest request) {
        try {
            var user = createUserUseCase.execute(
                    new CreateUserCommand(request.name(), request.email(), request.senha(), request.cpf()));

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();

            return ResponseEntity.created(location).body(UserMapper.toResponse(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        try {
            var user = getUserUseCase.execute(new GetUserQuery(id));
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(UserMapper.toResponse(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Busca um usuário pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf) {
        try {
            var user = getUserByCpfUseCase.execute(new GetUserByCpfQuery(cpf));
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(UserMapper.toResponse(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/address/{cep}")
    @Operation(summary = "Busca um endereço pelo CEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AddressResponse> getAddress(@PathVariable String cep) {
        try {
            var query = new GetAddressQuery(cep);
            var address = getAddressUseCase.execute(query);

            if (address == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(AddressMapper.toResponse(address));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/feriado/{year}")
    @Operation(summary = "Busca os feriados nacionais de um ano específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feriados listados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Ano no formato inválido"),
            @ApiResponse(responseCode = "404", description = "Feriados não encontrados para o ano"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<FeriadoResponse>> getAFeriado(@PathVariable String year) {
        try {
            if (!year.matches("\\d{4}")) {
                return ResponseEntity.badRequest().build();
            }

            var query = new GetFeriadoQuery(year);
            var feriado = getFeriadoUseCase.execute(query);

            if (feriado == null || feriado.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(FeriadoMapper.toResponse(feriado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
