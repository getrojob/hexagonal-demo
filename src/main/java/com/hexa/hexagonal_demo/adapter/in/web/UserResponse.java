package com.hexa.hexagonal_demo.adapter.in.web;

import java.util.UUID;

public record UserResponse(UUID id, String name, String email, String cpf) {}
