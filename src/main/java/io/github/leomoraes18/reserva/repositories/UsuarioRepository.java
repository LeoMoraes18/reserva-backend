package io.github.leomoraes18.reserva.repositories;

import io.github.leomoraes18.reserva.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
