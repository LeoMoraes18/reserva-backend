package io.github.leomoraes18.reserva.services;

import io.github.leomoraes18.reserva.dtos.ReservaRequest;
import io.github.leomoraes18.reserva.dtos.ReservaResponse;
import io.github.leomoraes18.reserva.entities.Espaco;
import io.github.leomoraes18.reserva.entities.Reserva;
import io.github.leomoraes18.reserva.entities.StatusReserva;
import io.github.leomoraes18.reserva.entities.Usuario;
import io.github.leomoraes18.reserva.exceptions.RecursoNaoEncontradoException;
import io.github.leomoraes18.reserva.exceptions.RegraNegocioException;
import io.github.leomoraes18.reserva.repositories.EspacoRepository;
import io.github.leomoraes18.reserva.repositories.ReservaRepository;
import io.github.leomoraes18.reserva.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final EspacoRepository espacoRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          EspacoRepository espacoRepository,
                          UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.espacoRepository = espacoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public ReservaResponse criar(ReservaRequest request) {
        if (!request.fim().isAfter(request.inicio())) {
            throw new RegraNegocioException("O fim da reserva deve ser depois do início.");
        }

        Espaco espaco = espacoRepository.buscarComLock(request.espacoId()).orElseThrow(
                () -> new RecursoNaoEncontradoException("Espaço não encontrado com id " + request.espacoId())
        );

        if (!espaco.getAtivo()) {
            throw new RegraNegocioException("Não é possível reservar um espaço inativo.");
        }

        Usuario usuario = usuarioRepository.findById(request.usuarioId()).orElseThrow(
                () -> new RecursoNaoEncontradoException("Usuário não encontrado com id " + request.usuarioId())
        );

        if (reservaRepository.existeConflito(
                espaco.getId(),
                request.inicio(),
                request.fim(),
                StatusReserva.CONFIRMADA)) {
            throw new RegraNegocioException("Já existe uma reserva confirmada para este espaço neste horário");
        }

        Reserva salvo = reservaRepository.save(
                new Reserva(usuario, espaco, request.inicio(), request.fim()));

        return ReservaResponse.from(salvo);


    }

}
