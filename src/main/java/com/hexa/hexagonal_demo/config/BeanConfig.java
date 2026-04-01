package com.hexa.hexagonal_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.hexa.hexagonal_demo.application.service.CreateUserService;
import com.hexa.hexagonal_demo.application.service.GetAddressService;
import com.hexa.hexagonal_demo.application.service.GetFeriadoService;
import com.hexa.hexagonal_demo.application.service.GetUserByCpfService;
import com.hexa.hexagonal_demo.application.service.GetUserService;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetAddressUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetFeriadoUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetUserByCpfUseCase;
import com.hexa.hexagonal_demo.domain.port.in.GetUserUseCase;
import com.hexa.hexagonal_demo.domain.port.out.AddressExternalService;
import com.hexa.hexagonal_demo.domain.port.out.FeriadoService;
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

    @Bean
    public GetAddressUseCase getAddressUseCase(AddressExternalService addressExternalService) {
        // O Spring injeta o ViaCepAdapter (que é um @Component) aqui automaticamente
        return new GetAddressService(addressExternalService);
    }

    @Bean
    public GetFeriadoUseCase getFeriadoUseCase(FeriadoService feriadoExternalService) {
        // O Spring injeta o FeriadoAdapter (que é um @Component) aqui automaticamente
        return new GetFeriadoService(feriadoExternalService);
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}

// @Configuration + @Bean para controlar a injeção de dependência sem poluir o
// domínio/application com Spring
