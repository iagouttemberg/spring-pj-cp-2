package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;

public record TipoVeiculoRequest(
        @NotNull(message = "Nome do tipo do veiculo deve ser informado")
        String nome
) {
}
