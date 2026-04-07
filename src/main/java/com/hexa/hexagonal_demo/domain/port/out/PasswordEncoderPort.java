package com.hexa.hexagonal_demo.domain.port.out;

public interface PasswordEncoderPort {
    String encode(String rawPassword);
}
