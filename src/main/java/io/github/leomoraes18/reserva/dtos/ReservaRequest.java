package io.github.leomoraes18.reserva.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservaRequest(
        @NotNull
        UUID espacoId,
        @NotNull
        UUID usuarioId,
        @NotNull
        @Future LocalDateTime inicio,
        @NotNull LocalDateTime fim
) {
}
