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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

        Espaco espaco = espacoRepository.buscarComLock(request.espacoId()).orElseThrow(
                () -> new RecursoNaoEncontradoException("Espaço não encontrado com id " + request.espacoId())
        );

        if (!espaco.getAtivo()) {
            throw new RegraNegocioException("Não é possível reservar um espaço inativo.");
        }

        Usuario usuario = usuarioRepository.findById(request.usuarioId()).orElseThrow(
                () -> new RecursoNaoEncontradoException("Usuário não encontrado com id " + request.usuarioId())
        );

        Reserva nova = new Reserva(usuario, espaco, request.inicio(), request.fim());

        if (reservaRepository.existeConflito(
                espaco.getId(),
                request.inicio(),
                request.fim(),
                StatusReserva.CONFIRMADA)) {
            throw new RegraNegocioException("Já existe uma reserva confirmada para este espaço neste horário");
        }

        Reserva salvo = reservaRepository.save(nova);

        return ReservaResponse.from(salvo);
    }

    private Reserva buscarReserva(UUID id) {
        Reserva reserva = reservaRepository.buscarComDetalhes(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("Reserva não encontrada com id " + id));
        return reserva;
    }

    @Transactional(readOnly = true)
    public ReservaResponse buscarPorId(UUID id) {
        return ReservaResponse.from(buscarReserva(id));
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> listar() {
        return reservaRepository.listarComDetalhes()
                .stream()
                .map(ReservaResponse::from)
                .toList();
    }

    @Transactional
    public ReservaResponse cancelar(UUID id) {
        Reserva reserva = buscarReserva(id);
        reserva.cancelar(LocalDateTime.now());
        return ReservaResponse.from(reserva);
    }

}
