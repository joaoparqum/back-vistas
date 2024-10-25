package com.api.casadoconstrutor.vistas.dto;

import com.api.casadoconstrutor.vistas.user.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
}
