package br.com.fiap.concessionaria.dto.response;

import lombok.Builder;

@Builder
public record CaracteristicaResponse(
        String nome,
        String descricao,
        Long id,
        VeiculoResponse veiculo
) {
}