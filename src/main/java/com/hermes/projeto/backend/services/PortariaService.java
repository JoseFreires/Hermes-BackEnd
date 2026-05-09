package com.hermes.projeto.backend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.projeto.backend.dto.DadosAtualizacaoEncomendaDTO;
import com.hermes.projeto.backend.dto.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.entities.Encomenda;
import com.hermes.projeto.backend.entities.Porteiro;
import com.hermes.projeto.backend.enums.StatusEncomenda;
import com.hermes.projeto.backend.repository.EncomendaRepository;
import com.hermes.projeto.backend.repository.MoradorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PortariaService {

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private MoradorRepository moradorRepository;

    // + registrarNovaEncomenda() : void
    @Transactional
    public void registrarNovaEncomenda(DadosRegistrarEncomendaDTO dados, Porteiro porteiro) {
        // Busca o morador destinado
        var morador = moradorRepository.findById(dados.idMorador())
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado"));

        // Cria a entidade Encomenda usando o construtor de negócio que validamos
        var encomenda = new Encomenda(dados, morador, porteiro);
        
        encomendaRepository.save(encomenda);
    }

    // + visualizarEncomendas() : List<Encomenda>
    public List<Encomenda> visualizarEncomendas() {
        return encomendaRepository.findAll();
    }

    // + atualizarStatusEncomenda() : void
    @Transactional
public void atualizarStatusEncomenda(Long idEncomenda, StatusEncomenda novoStatus) {
    var encomenda = encomendaRepository.findById(idEncomenda)
            .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
    
    encomenda.setStatusEncomenda(novoStatus);

    // Lógica extra: se retirou, marca a hora agora!
    if (novoStatus == StatusEncomenda.RETIRADA) {
        encomenda.setDataHoraRetirado(LocalDateTime.now());
    }
}

    // + EditarEncomenda() : void
    @Transactional
    public void editarEncomenda(Long id, DadosAtualizacaoEncomendaDTO dados) {
        var encomenda = encomendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
        
        if (dados.nomePacote() != null) {
            encomenda.setNomePacote(dados.nomePacote());
        }

        if (dados.moradorId() != null){
            var morador = moradorRepository.findById(dados.moradorId())
            .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado"));

            encomenda.setMorador(morador);
        }

        if (dados.observacao() != null) {
            encomenda.setObservacao(dados.observacao());
        }
    }

    // + consultaEncomenda() : Encomenda
    public Encomenda consultaEncomenda(Long id) {
        return encomendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
    }

    // + removeEncomenda() : void
    @Transactional
    public void removeEncomenda(Long id) {
        if (!encomendaRepository.existsById(id)) {
            throw new EntityNotFoundException("Não é possível remover: Encomenda inexistente.");
        }
        encomendaRepository.deleteById(id);
    }
}