package io.github.leomoraes18.reserva.services;

import io.github.leomoraes18.reserva.dtos.EspacoRequest;
import io.github.leomoraes18.reserva.dtos.EspacoResponse;
import io.github.leomoraes18.reserva.entities.Espaco;
import io.github.leomoraes18.reserva.exceptions.RecursoNaoEncontradoException;
import io.github.leomoraes18.reserva.repositories.EspacoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EspacoService {

    private final EspacoRepository espacoRepository;

    public EspacoService(EspacoRepository espacoRepository) {
        this.espacoRepository = espacoRepository;
    }

    @Transactional
    public EspacoResponse cadastrar(EspacoRequest request) {
        Espaco espaco = new Espaco(request.nome(), request.descricao(), request.capacidade(), true);
        Espaco salvo = espacoRepository.save(espaco);
        return EspacoResponse.from(salvo);
    }

    @Transactional(readOnly = true)
    public List<EspacoResponse> listar() {
        return espacoRepository.findAll().stream().map(EspacoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public EspacoResponse buscarPorId(UUID id) {
        return EspacoResponse.from(buscarEntidade(id));
    }

    private Espaco buscarEntidade(UUID id) {
        Espaco espaco = espacoRepository.findById(id).orElseThrow(() -> new
                RecursoNaoEncontradoException("Espaço não encontrado com id " + id));
        return espaco;
    }

    @Transactional
    public EspacoResponse atualizar(UUID id, EspacoRequest request) {
        Espaco espaco = buscarEntidade(id);
        espaco.setNome(request.nome());
        espaco.setDescricao(request.descricao());
        espaco.setCapacidade(request.capacidade());
        return EspacoResponse.from(espaco);
    }
}
