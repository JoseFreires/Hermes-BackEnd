package com.hermes.projeto.backend.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import com.hermes.projeto.backend.dto.DadosAtualizacaoEncomendaDTO;
import com.hermes.projeto.backend.dto.DadosAtualizarStatusEncomendaDTO;
import com.hermes.projeto.backend.enums.StatusEncomenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.projeto.backend.dto.DadosConsultaEncomendaDTO;
import com.hermes.projeto.backend.dto.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.entities.Encomenda;
import com.hermes.projeto.backend.entities.Pessoa;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.repository.EncomendaRepository;
import com.hermes.projeto.backend.repository.PessoaRepository;
import com.hermes.projeto.backend.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PortariaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EncomendaRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    private String gerarTokenEncomenda(){
        SecureRandom random = new SecureRandom();
        int tokenEncomenda = random.nextInt(10000);
        return String.format("%04d", tokenEncomenda);
    }

    @Transactional
    public DadosConsultaEncomendaDTO registrarEncomenda(DadosRegistrarEncomendaDTO dados, Usuario logado) {
        Pessoa morador = pessoaRepository.findById(dados.idDestinatario())
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado"));

        var porteiro = usuarioRepository.findById(logado.getId())
            .orElseThrow(() -> new EntityNotFoundException("Porteiro não encontrado"));

        boolean ePorteiro = porteiro.getPapel().getNomePapel().equals("ROLE_PORTEIRO"); 
        if (!ePorteiro) {
            throw new RuntimeException("Apenas usuários com papel de porteiro podem registrar encomendas");
        }

        String tokenEncomenda = gerarTokenEncomenda();

        var encomenda = new Encomenda(dados, porteiro, morador, tokenEncomenda);
        repository.save(encomenda);

        return new DadosConsultaEncomendaDTO(encomenda);
    }

    @Transactional(readOnly = true)
    public List<DadosConsultaEncomendaDTO> listarEncomendas(StatusEncomenda status) {
        List<Encomenda> encomendas = (status != null)
                ? repository.findByStatus(status)
                : repository.findAll();

        return encomendas.stream()
                .map(DadosConsultaEncomendaDTO::new)
                .toList();
    }


    @Transactional(readOnly = true)
    public DadosConsultaEncomendaDTO buscarEncomendaPorId(Long id) {
        var encomenda = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
        return new DadosConsultaEncomendaDTO(encomenda);
    }


    @Transactional
    public void registrarEntregaEncomenda(Long idEncomenda, DadosAtualizarStatusEncomendaDTO dados) {

        var encomenda = repository.findById(idEncomenda)
                .orElseThrow(() -> new RuntimeException("Encomenda não encontrada!"));

        if (encomenda.getStatusEncomenda() == StatusEncomenda.ENTREGUE) {
            throw new RuntimeException("Esta encomenda já consta como Retirada!");
        }

        encomenda.setStatusEncomenda(StatusEncomenda.ENTREGUE);
        encomenda.setDataHoraRetirado(LocalDateTime.now());
        encomenda.setTipoRetirada(dados.tipoRetirada());

        repository.save(encomenda);
    }

    @Transactional
    public void editarEncomenda(Long id, DadosAtualizacaoEncomendaDTO dados) {

        var encomenda = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encomenda não encontrada!"));


        if (dados.observacao() != null) {
            encomenda.setObservacao(dados.observacao());
        }

        if (dados.nomePacote() != null) {
            encomenda.setNomePacote(dados.nomePacote());
        }

        if (dados.idDestinatario() != null) {

            // Busca a nova pessoa no banco
            var novoDestinatario = pessoaRepository.findById(dados.idDestinatario())
                    .orElseThrow(() -> new RuntimeException("Novo destinatário não encontrado no sistema!"));

            encomenda.setMoradorDestinatario(novoDestinatario);
        }



        repository.save(encomenda);
    }


    @Transactional
    public void deletarEncomendaPorId(Long id){
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("A encomenda informada não existe.");
        }
        repository.deleteById(id);
    }
}