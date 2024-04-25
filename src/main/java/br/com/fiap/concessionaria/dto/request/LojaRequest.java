package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;

public record LojaRequest(
        @NotNull(message = "Nome da loja deve ser informado")
        String nome
) {
}
