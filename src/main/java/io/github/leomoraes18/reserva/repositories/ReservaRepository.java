package io.github.leomoraes18.reserva.repositories;

import io.github.leomoraes18.reserva.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {
}
