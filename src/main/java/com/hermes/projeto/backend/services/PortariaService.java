package com.hermes.projeto.backend.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import com.hermes.projeto.backend.dto.request.DadosAtualizacaoEncomendaDTO;
import com.hermes.projeto.backend.dto.request.DadosAtualizarStatusEncomendaDTO;
import com.hermes.projeto.backend.domain.enums.StatusEncomenda;
import com.hermes.projeto.backend.dto.request.DadosEnvioEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.projeto.backend.dto.response.DadosConsultaEncomendaDTO;
import com.hermes.projeto.backend.dto.request.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.domain.Encomenda;
import com.hermes.projeto.backend.domain.Pessoa;
import com.hermes.projeto.backend.security.Usuario;
import com.hermes.projeto.backend.repository.EncomendaRepository;
import com.hermes.projeto.backend.repository.PessoaRepository;
import com.hermes.projeto.backend.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PortariaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    EmailServiceImpl emailService;

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
        encomendaRepository.save(encomenda);

        DadosEnvioEmailDTO email = new DadosEnvioEmailDTO(
                morador.getEmail(),
                "Recebemos sua encomenda!",
                "Olá " + morador.getNomeCompleto() + ", sua encomenda chegou na portaria!"
        );
        emailService.enviarEmail(email);

        return new DadosConsultaEncomendaDTO(encomenda);
    }

    @Transactional(readOnly = true)
    public List<DadosConsultaEncomendaDTO> listarEncomendas(StatusEncomenda status) {
        List<Encomenda> encomendas = (status != null)
                ? encomendaRepository.findByStatus(status)
                : encomendaRepository.findAll();

        return encomendas.stream()
                .map(DadosConsultaEncomendaDTO::new)
                .toList();
    }


    @Transactional(readOnly = true)
    public DadosConsultaEncomendaDTO buscarEncomendaPorId(Long id) {
        var encomenda = encomendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
        return new DadosConsultaEncomendaDTO(encomenda);
    }


    @Transactional
    public void registrarEntregaEncomenda(Long idEncomenda, DadosAtualizarStatusEncomendaDTO dados) {

        var encomenda = encomendaRepository.findById(idEncomenda)
                .orElseThrow(() -> new RuntimeException("Encomenda não encontrada!"));

        if (encomenda.getStatusEncomenda() == StatusEncomenda.ENTREGUE) {
            throw new RuntimeException("Esta encomenda já consta como Retirada!");
        }

        encomenda.setStatusEncomenda(StatusEncomenda.ENTREGUE);
        encomenda.setDataHoraRetirado(LocalDateTime.now());
        encomenda.setTipoRetirada(dados.tipoRetirada());



        encomendaRepository.save(encomenda);
    }

    @Transactional
    public void editarEncomenda(Long id, DadosAtualizacaoEncomendaDTO dados) {

        var encomenda = encomendaRepository.findById(id)
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



        encomendaRepository.save(encomenda);
    }


    @Transactional
    public void deletarEncomendaPorId(Long id){
        if (!encomendaRepository.existsById(id)) {
            throw new EntityNotFoundException("A encomenda informada não existe.");
        }
        encomendaRepository.deleteById(id);
    }
}