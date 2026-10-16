package io.github.leomoraes18.reserva.repositories;

import io.github.leomoraes18.reserva.entities.Espaco;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EspacoRepository extends JpaRepository<Espaco, UUID> {

    List<Espaco> findByAtivoTrue();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Espaco e where e.id = :id")
    Optional<Espaco> buscarComLock(@Param("id") UUID id);
}
