package com.hermes.projeto.backend.services;

import com.hermes.projeto.backend.dto.*;
import com.hermes.projeto.backend.entities.Pessoa;
import com.hermes.projeto.backend.entities.Porteiro;
import com.hermes.projeto.backend.entities.security.Papel;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.repository.PapelRepository;
import com.hermes.projeto.backend.repository.PessoaRepository;
import com.hermes.projeto.backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdmService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PapelRepository papelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public DadosConsultaPessoaDTO registrarSindico(DadosRegistrarPessoaDTO dados, DadosLoginDTO login) {

        Papel papelSindico = papelRepository.findByNomePapel("ROLE_SINDICO")
                .orElseThrow(() -> new EntityNotFoundException("O papel ROLE_SINDICO não está configurada no banco."));

        Pessoa pessoa = new Pessoa(dados);
        pessoaRepository.save(pessoa);

        String senhaCodificada = passwordEncoder.encode(login.senha());
        Usuario usuario = new Usuario(login, senhaCodificada);

        //Colocando navegabilidade
        usuario.setPessoa(pessoa);

        //Inserindo Role Sindico
        usuario.setPapel(papelSindico);

        //Salva Usuario
        usuarioRepository.save(usuario);

        return new DadosConsultaPessoaDTO(usuario);

    }

    //GET List Sindico
    @Transactional(readOnly = true)
    public List<DadosConsultaPessoaDTO> listarTodosSindicos() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getPapel() != null &&
                        usuario.getPapel().getNomePapel().equals("ROLE_SINDICO") &&
                        Boolean.TRUE.equals(usuario.getPessoa().getAtivo()))
                .map(DadosConsultaPessoaDTO::new)
                .toList();
    }

    //GET Sindico por Id
    @Transactional(readOnly = true)
    public DadosConsultaPessoaDTO buscarSindicoPorId(Long id) {
        var sindico = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sindico não encontrado"));
        return new DadosConsultaPessoaDTO(sindico);
    }

    //PUT Sindico
    @Transactional
    public DadosConsultaPessoaDTO editarSindico(Long idUsuario, DadosAtualizacaoPessoaDTO dados){


        var usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

        var pessoaSindico = usuario.getPessoa();

        if(dados.email() != null){
            pessoaSindico.setEmail(dados.email());
        }

        if (dados.dataNascimento() != null) {
            pessoaSindico.setDataNascimento(dados.dataNascimento());
        }

        if(dados.nomeCompleto() != null){
            pessoaSindico.setNomeCompleto(dados.nomeCompleto());
        }

        if(dados.telefone() != null){
            pessoaSindico.setTelefone(dados.telefone());
        }

        // O Hibernate salva tudo (PessoaSindico) automaticamente ao final da transação.
        return new DadosConsultaPessoaDTO(usuario);
    }


    //"DELETE" Sindico
    @Transactional
    public void desativarSindico(Long id){

        var usuarioSindico = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sindico não encontrado."));

        var pessoaSindico = usuarioSindico.getPessoa();
        pessoaSindico.setAtivo(false);

        pessoaRepository.save(pessoaSindico);
    }

}
