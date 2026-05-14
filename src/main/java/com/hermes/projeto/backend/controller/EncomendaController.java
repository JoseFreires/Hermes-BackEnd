package com.hermes.projeto.backend.controller;

import com.hermes.projeto.backend.dto.DadosListagemEncomendaDTO;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.hermes.projeto.backend.dto.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.entities.Encomenda;
import com.hermes.projeto.backend.repository.EncomendaRepository;
import com.hermes.projeto.backend.repository.MoradorRepository;
import com.hermes.projeto.backend.repository.PorteiroRepository;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/encomendas")
public class EncomendaController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PorteiroRepository porteiroRepository;


    @Autowired
    private EncomendaRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity registrarEncomenda( @Valid @RequestBody DadosRegistrarEncomendaDTO dados, UriComponentsBuilder uriBuilder){

        var porteiro = porteiroRepository.getReferenceById(dados.idPorteiro());
        var usuario = usuarioRepository.findByUsername(dados.emailDestinatario()); //Agora é usuario, não mais Morador, aqui ele pega o Email (Username no banco)
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        var encomenda = new Encomenda(dados, porteiro, (Usuario) usuario);
        repository.save(encomenda);

        var uri = uriBuilder.path("/encomendas/{id}").buildAndExpand(encomenda.getIdEncomenda()).toUri();
        return ResponseEntity.created(uri).body(dados);
    }

    //Get padrão (Lista Json)
    @GetMapping
    public ResponseEntity<List<DadosListagemEncomendaDTO>> listar() {
        var lista = repository.findAll().stream()
                .map(DadosListagemEncomendaDTO::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    //Get por ID
    @GetMapping("/{id}")
    public ResponseEntity detalharEncomenda(@PathVariable Long id) {
        var encomenda = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosListagemEncomendaDTO(encomenda));
    }


}