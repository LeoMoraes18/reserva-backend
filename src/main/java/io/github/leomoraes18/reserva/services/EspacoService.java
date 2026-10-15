package io.github.leomoraes18.reserva.services;

import io.github.leomoraes18.reserva.dtos.EspacoRequest;
import io.github.leomoraes18.reserva.dtos.EspacoResponse;
import io.github.leomoraes18.reserva.entities.Espaco;
import io.github.leomoraes18.reserva.repositories.EspacoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
