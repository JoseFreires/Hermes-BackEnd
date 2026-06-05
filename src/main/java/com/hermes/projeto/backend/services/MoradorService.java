package com.hermes.projeto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.projeto.backend.dto.DadosConsultaMoradorDTO;
import com.hermes.projeto.backend.dto.DadosRegistrarMoradorDTO;
import com.hermes.projeto.backend.entities.Morador;
import com.hermes.projeto.backend.entities.Pessoa;
import com.hermes.projeto.backend.entities.condo.Moradia;
import com.hermes.projeto.backend.entities.security.Papel;
import com.hermes.projeto.backend.entities.security.Usuario;
import com.hermes.projeto.backend.repository.MoradiaRepository;
import com.hermes.projeto.backend.repository.PapelRepository;
import com.hermes.projeto.backend.repository.PessoaRepository;
import com.hermes.projeto.backend.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

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
    public DadosConsultaMoradorDTO registrarMorador(DadosRegistrarMoradorDTO dados) {

        //Buscando a moradia para associar (como tinhaos falado nas reuniões moradia etc já vai ter cadastro)
        Moradia moradia = moradiaRepository.findById(dados.idMoradia())
                .orElseThrow(() -> new EntityNotFoundException("Moradia/Apartamento não encontrado no sistema."));

        // Agora o papel é fixo então ele busca no banco
        Papel papelMorador = papelRepository.findByNomePapel("ROLE_MORADOR")
                .orElseThrow(() -> new EntityNotFoundException("O papel ROLE_MORADOR não está configurado no banco."));

        //Instancia de pessoa
        Pessoa pessoa = new Pessoa(dados.pessoa());

        //Instancia de Morador (Não é mais um papel)
        Morador morador = new Morador(dados, moradia);

        // Inserindo composição
        morador.setPessoa(pessoa);
        pessoa.setMorador(morador);
        pessoaRepository.save(pessoa);

        //Senha com a lógica do Rian de criptografia
        String senhaCodificada = passwordEncoder.encode(dados.usuario().senha());
        Usuario usuario = new Usuario(dados.usuario(), senhaCodificada);

        //Colocando navegabilidade (como no diagrama)
        usuario.setPessoa(pessoa);

        //Inserindo Role Morador
        usuario.setPapel(papelMorador);

        //Salva Usuario
        usuarioRepository.save(usuario);

        // retorna o usuario completo
        return new DadosConsultaMoradorDTO(usuario);
    }

    //Get padrão
    @Transactional(readOnly = true)
    public List<DadosConsultaMoradorDTO> listarTodasMoradores() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getPessoa().getMorador() != null)
                .map(DadosConsultaMoradorDTO::new)
                .toList();
    }

    //Get por ID
    @Transactional(readOnly = true)
    public DadosConsultaMoradorDTO buscarMoradorPorId(Long id) {
        Usuario morador = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado"));
        return new DadosConsultaMoradorDTO(morador);
    }
}