package br.com.fiap.concessionaria.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CaracteristicaRequest(
        @Valid
        @NotNull(message = "Veiculo deve ser informado")
        VeiculoRequest veiculo,
        @NotNull(message = "Nome da característica deve ser informado")
        @Size(min = 1, max = 30)
        String nome,
        @NotNull(message = "Descrição da característica deve ser informado")
        @Size(min = 1, max = 20)
        String descricao
) {
}