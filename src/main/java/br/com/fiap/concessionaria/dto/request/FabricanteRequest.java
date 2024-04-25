package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;

public record FabricanteRequest(
        @NotNull(message = "Nome do fabricante deve ser informado")
        String nome,
        @NotNull(message = "Nome de fantasia do fabricante deve ser informado")
        String nomeFantasia
) {
}