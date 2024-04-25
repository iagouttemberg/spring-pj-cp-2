package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.constraints.NotNull;

public record AcessorioRequest(
        @NotNull(message = "Nome do acessório deve ser informado")
        String nome,
        @NotNull(message = "Preço do acessório deve ser informado")
        Double preco
) {
}
