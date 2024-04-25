package br.com.fiap.concessionaria.dto.response;

public record CaracteristicaResponse(
        String nome,
        String descricao,
        Long id,
        VeiculoResponse veiculo
) {
}
