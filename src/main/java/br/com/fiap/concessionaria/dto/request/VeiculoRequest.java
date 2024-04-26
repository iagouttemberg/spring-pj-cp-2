package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record VeiculoRequest(
        @NotNull(message = "Preço do veículo deve ser informado")
        Double preco,
        @NotNull(message = "Modelo do veículo deve ser informado")
        String modelo,
        @NotNull(message = "Ano de fabricação do veículo deve ser informado")
        Year anoDeFabricacao,
        @NotNull(message = "Nome do veículo deve ser informado")
        String nome,
        @Valid
        @NotNull(message = "Tipo do veículo deve ser informado")
        AbstractRequest tipo,
        @Valid
        @NotNull(message = "Fabricante do veiculo deve ser informado")
        AbstractRequest fabricante,
        @NotNull(message = "Cor do veículo deve ser informada")
        String cor,
        @NotNull(message = "Palavra de efeito do veículo deve ser informada")
        @Size(min = 1, max = 15)
        String palavraDeEfeito,
        @NotNull(message = "Cilindradas do veículo deve ser informada")
        Short cilindradas
) {
}