package com.hermes.projeto.backend.services;

import java.time.LocalDateTime;
import java.util.List;

import com.hermes.projeto.backend.dto.DadosListagemEncomendaDTO;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.repository.PorteiroRepository;
import com.hermes.projeto.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.projeto.backend.dto.DadosAtualizacaoEncomendaDTO;
import com.hermes.projeto.backend.dto.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.entities.Encomenda;
import com.hermes.projeto.backend.entities.Porteiro;
import com.hermes.projeto.backend.enums.StatusEncomenda;
import com.hermes.projeto.backend.repository.EncomendaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PortariaService {

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private PorteiroRepository porteiroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // + registrarNovaEncomenda() : void
    @Transactional
    public Encomenda registrarNovaEncomenda(DadosRegistrarEncomendaDTO dados) {
        // Procura o usuário pelo e-mail (no nosso caso é o username) que veio do dropdown do front
        var usuario = usuarioRepository.findByUsername(dados.emailDestinatario());
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário destinatário não encontrado com o e-mail: " + dados.emailDestinatario());
        }//Se não encontrar email

        // Procura o porteiro que está operando o sistema (Não mexi no id pq não sei como fazer pelo token Rian manja)
        var porteiro = porteiroRepository.findById(dados.idPorteiro())
                .orElseThrow(() -> new EntityNotFoundException("Porteiro não encontrado"));


        var encomenda = new Encomenda(dados, porteiro, (Usuario) usuario);

        return encomendaRepository.save(encomenda);
    }

    public List<Encomenda> visualizarEncomendas() {
        return encomendaRepository.findAll();
    }

    @Transactional
    public void atualizarStatusEncomenda(Long idEncomenda, StatusEncomenda novoStatus) {
        var encomenda = encomendaRepository.findById(idEncomenda)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));

        encomenda.setStatusEncomenda(novoStatus);

        if (novoStatus == StatusEncomenda.RETIRADA) {
            encomenda.setDataHoraRetirado(LocalDateTime.now());
        }
    }

    @Transactional
    public void editarEncomenda(Long id, DadosAtualizacaoEncomendaDTO dados) {
        var encomenda = encomendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));

        if (dados.nomePacote() != null) {
            encomenda.setNomePacote(dados.nomePacote());
        }

        // caso o e-mail mude ele faz a verificação novamente
        if (dados.emailDestinatario() != null) {
            var usuario = usuarioRepository.findByUsername(dados.emailDestinatario());
            if (usuario == null) {
                throw new EntityNotFoundException("Novo usuário destinatário não encontrado");
            }
            encomenda.setUsuario((Usuario) usuario);
        }

        if (dados.observacao() != null) {
            encomenda.setObservacao(dados.observacao());
        }
    }

//    public Encomenda consultaEncomenda(Long id) {
//        return encomendaRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
//    }

    @Transactional
    public void removeEncomenda(Long id) {
        if (!encomendaRepository.existsById(id)) {
            throw new EntityNotFoundException("Não é possível remover: Encomenda inexistente.");
        }
        encomendaRepository.deleteById(id);
    }

    //Método do GET com DTO
    public DadosListagemEncomendaDTO consultaEncomendaDTO(Long id) {
        var encomenda = encomendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));

        return new DadosListagemEncomendaDTO(encomenda);
    }
}