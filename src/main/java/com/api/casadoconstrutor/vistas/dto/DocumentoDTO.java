package com.api.casadoconstrutor.vistas.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DocumentoDTO(UUID id,
                           @NotNull String nomeArquivo,
                           @NotNull String tipoArquivo,
                           Long tamanhoArquivo) {
}
