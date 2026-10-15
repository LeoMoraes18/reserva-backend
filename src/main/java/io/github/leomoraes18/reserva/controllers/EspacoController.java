package io.github.leomoraes18.reserva.controllers;

import io.github.leomoraes18.reserva.dtos.EspacoRequest;
import io.github.leomoraes18.reserva.dtos.EspacoResponse;
import io.github.leomoraes18.reserva.services.EspacoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/espacos")
public class EspacoController {

    private final EspacoService espacoService;

    public EspacoController(EspacoService espacoService) {
        this.espacoService = espacoService;
    }

    @PostMapping
    public ResponseEntity<EspacoResponse> cadastrar(@Valid @RequestBody EspacoRequest request) {
        EspacoResponse response = espacoService.cadastrar(request);
        URI uri =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EspacoResponse>> listar() {
        return ResponseEntity.ok(espacoService.listar());
    }
}
