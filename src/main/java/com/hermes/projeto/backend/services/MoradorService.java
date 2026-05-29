package com.hermes.projeto.backend.services;

import com.hermes.projeto.backend.dto.*;
import com.hermes.projeto.backend.entities.PerfilMorador;
import com.hermes.projeto.backend.entities.Pessoa;
import com.hermes.projeto.backend.entities.condo.Moradia;
import com.hermes.projeto.backend.entities.security.Papel;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoradorService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MoradiaRepository moradiaRepository;

    @Autowired
    private PapelRepository papelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public DadosListagemMoradorDTO registrar(DadosRegistrarMoradorDTO dados) {

        //Buscando a moradia para associar (como tinhaos falado nas reuniões moradia etc já vai ter cadastro)
        Moradia moradia = moradiaRepository.findById(dados.idMoradia())
                .orElseThrow(() -> new EntityNotFoundException("Moradia/Apartamento não encontrado no sistema."));

        // Agora o papel é fixo então ele busca no banco
        Papel papelMorador = papelRepository.findByNomePapel("ROLE_MORADOR")
                .orElseThrow(() -> new EntityNotFoundException("O papel ROLE_MORADOR não está configurado no banco."));

        //Instancia de pessoa
        Pessoa pessoa = new Pessoa(dados.pessoa());

        //Instancia de perfilMorador (Não é mais um papel)
        PerfilMorador perfilMorador = new PerfilMorador(dados, moradia);

        // Inserindo composição
        perfilMorador.setPessoa(pessoa);
        pessoa.setPerfilMorador(perfilMorador);
        pessoaRepository.save(pessoa);

        //Senha com a lógica do Rian
        String senhaCodificada = passwordEncoder.encode(dados.usuario().senha());
        Usuario usuario = new Usuario(dados.usuario(), senhaCodificada);

        //Colocando navegabilidade (como no diagrama)
        usuario.setPessoa(pessoa);
        usuario.getPapeis().add(papelMorador);

        //Salva Usuario
        usuarioRepository.save(usuario);

        // retorna o usuario completo
        return new DadosListagemMoradorDTO(usuario);
    }

    //Get padrão
    @Transactional(readOnly = true)
    public List<DadosListagemMoradorDTO> listarTodas() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getPessoa().getPerfilMorador() != null)
                .map(DadosListagemMoradorDTO::new)
                .toList();
    }

    //Get por ID
    @Transactional(readOnly = true)
    public DadosListagemMoradorDTO buscarPorId(Long id) {
        Usuario morador = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado"));
        return new DadosListagemMoradorDTO(morador);
    }
}