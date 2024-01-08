package br.com.karatedopi.controllers.dtos;

import jakarta.validation.constraints.NotNull;

public record GraduationDTO(@NotNull String belt) {
}
