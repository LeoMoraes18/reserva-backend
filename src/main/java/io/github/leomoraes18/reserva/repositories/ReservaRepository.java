package io.github.leomoraes18.reserva.repositories;

import io.github.leomoraes18.reserva.entities.Reserva;
import io.github.leomoraes18.reserva.entities.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    @Query("""
            select case when count(r) > 0 then true else false end
            from Reserva r
            where r.espaco.id = :espacoId
              and r.status = :status
              and r.inicio < :fim
              and r.fim > :inicio
          """)
    boolean existeConflito(@Param("espacoId") UUID espacoId,
                           @Param("inicio") LocalDateTime inicio,
                           @Param("fim") LocalDateTime fim,
                           @Param("status")StatusReserva status);

    @Query("""
            select r from Reserva r
            join fetch r.espaco
            join fetch r.usuario
            order by r.inicio
           """)
    List<Reserva> listarComDetalhes();

    @Query("""
            select r from Reserva r
            join fetch r.espaco
            join fetch r.usuario
            where r.id = :id
           """)
    Optional<Reserva> buscarComDetalhes(@Param("id") UUID id);
}
