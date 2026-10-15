package io.github.leomoraes18.reserva.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EspacoRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        String descricao,

        @NotNull(message = "A capacidade é obrigatória")
        @Positive(message = "A capacidade deve ser maior que zero")
        Integer capacidade
) {
}
