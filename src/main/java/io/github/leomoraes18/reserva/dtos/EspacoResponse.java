package io.github.leomoraes18.reserva.dtos;

import io.github.leomoraes18.reserva.entities.Espaco;

import java.util.UUID;

public record EspacoResponse(
        UUID id,
        String nome,
        String descricao,
        Integer capacidade,
        Boolean ativo
) {
    public static EspacoResponse from(Espaco espaco) {
        return new EspacoResponse(
                espaco.getId(),
                espaco.getNome(),
                espaco.getDescricao(),
                espaco.getCapacidade(),
                espaco.getAtivo()
        );
    }
}
