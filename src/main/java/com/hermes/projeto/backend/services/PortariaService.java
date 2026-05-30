package com.hermes.projeto.backend.services;

import java.util.List;

import com.hermes.projeto.backend.entities.PerfilMorador;
import com.hermes.projeto.backend.entities.Pessoa;
import com.hermes.projeto.backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.projeto.backend.dto.DadosListagemEncomendaDTO;
import com.hermes.projeto.backend.dto.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.entities.Encomenda;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.repository.EncomendaRepository;
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

    @Transactional
    public DadosListagemEncomendaDTO registrar(DadosRegistrarEncomendaDTO dados, Usuario logado) {
        // 1. Busca destinatário (Morador)
        Pessoa morador = pessoaRepository.findById(dados.idDestinatario())
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado"));

        // 2. Validação de papel
        // Carrega o usuário (porteiro) gerenciado dentro da transação para evitar LazyInitializationException
        var porteiro = usuarioRepository.findById(logado.getId())
            .orElseThrow(() -> new EntityNotFoundException("Porteiro não encontrado"));

        boolean ePorteiro = porteiro.getPapeis().stream()
            .anyMatch(p -> p.getNomePapel().equals("ROLE_PORTEIRO"));

        if (!ePorteiro) {
            throw new RuntimeException("Apenas usuários com papel de porteiro podem registrar encomendas");
        }

        // 3. Cria e Salva
        var encomenda = new Encomenda(dados, porteiro, morador);
        repository.save(encomenda);

        // O SEGREDO: Converter para DTO AQUI dentro.
        // Como o método é @Transactional, o Hibernate consegue buscar o NomeCompleto agora.
        return new DadosListagemEncomendaDTO(encomenda);
    }

    @Transactional(readOnly = true)
    public List<DadosListagemEncomendaDTO> listarTodas() {
        // Usando o stream aqui dentro do Transactional também resolve para a lista
        return repository.findAll().stream()
                .map(DadosListagemEncomendaDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public DadosListagemEncomendaDTO buscarPorId(Long id) {
        var encomenda = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada"));
        return new DadosListagemEncomendaDTO(encomenda);
    }
}