package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(
        @Positive
        @NotNull(message = "Id deve ser informado")
        Long id
) {
}
