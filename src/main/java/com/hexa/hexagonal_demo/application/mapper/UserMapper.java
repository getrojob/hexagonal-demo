package com.hexa.hexagonal_demo.application.mapper;

import com.hexa.hexagonal_demo.adapter.in.web.UserResponse;
import com.hexa.hexagonal_demo.domain.model.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCpf()
        );
    }
}
