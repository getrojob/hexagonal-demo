package com.hexa.hexagonal_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hexa.hexagonal_demo.application.service.CreateUserService;
import com.hexa.hexagonal_demo.application.service.GetUserByCpfService;
import com.hexa.hexagonal_demo.application.service.GetUserService;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetUserByCpfUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetUserUseCase;
import com.hexa.hexagonal_demo.domain.port.out.UserRepository;

@Configuration
public class BeanConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserService(userRepository);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserRepository userRepository) {
        return new GetUserService(userRepository);
    }

    @Bean
    public GetUserByCpfUseCase getUserByCpfUseCase(UserRepository userRepository) {
        return new GetUserByCpfService(userRepository);
    }
}

//@Configuration + @Bean para controlar a injeção de dependência sem poluir o domínio/application com Spring
