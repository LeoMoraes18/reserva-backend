package io.github.leomoraes18.reserva.dtos;

import io.github.leomoraes18.reserva.entities.Reserva;
import io.github.leomoraes18.reserva.entities.StatusReserva;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservaResponse(
        UUID id,
        UUID espacoId,
        String espacoNome,
        UUID usuarioId,
        String usuarioNome,
        LocalDateTime inicio,
        LocalDateTime fim,
        StatusReserva status

) {
    public static ReservaResponse from(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getEspaco().getId(),
                reserva.getEspaco().getNome(),
                reserva.getUsuario().getId(),
                reserva.getUsuario().getNome(),
                reserva.getInicio(),
                reserva.getFim(),
                reserva.getStatus()
        );
    }
}
