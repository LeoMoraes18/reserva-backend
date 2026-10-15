package io.github.leomoraes18.reserva.repositories;

import io.github.leomoraes18.reserva.entities.Espaco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EspacoRepository extends JpaRepository<Espaco, UUID> {

}
