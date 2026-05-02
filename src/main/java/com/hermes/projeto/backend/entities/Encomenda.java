package com.hermes.projeto.backend.entities;

import java.time.LocalDateTime;

import com.hermes.projeto.backend.dto.DadosRegistrarEncomendaDTO;
import com.hermes.projeto.backend.enums.StatusEncomenda;
import com.hermes.projeto.backend.enums.TipoRetirada;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Encomenda")
@Table(name = "encomenda")
public class Encomenda{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEncomenda;

    @Column(name = "nome_pacote")
    private String nomePacote;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_hora_recebido")
    private LocalDateTime dataHoraRecebido;

    @Column(name = "data_hora_retirado")
    private LocalDateTime dataHoraRetirado;

    @Column(name = "foto_encomenda")
    private String fotoEncomenda;

    // Se no banco a coluna é apenas 'status', mapeamos assim:
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL) // Use ORDINAL se no banco for 0 ou 1 (TINYINT)
    private StatusEncomenda statusEncomenda;

    // No print NÃO aparece a coluna 'foto'. 
    // Se você não for criar a coluna no banco agora, use @Transient para o Java ignorar:
    @Transient 
    private String foto;

    // No print NÃO aparece 'token', mas aparece 'token_retirada'
    @Column(name = "token_retirada")
    private String token;

    @Column(name = "tipo_retirada")
    @Enumerated(EnumType.ORDINAL) // Use ORDINAL se no banco for 0 ou 1 (TINYINT)
    private TipoRetirada tipoRetirada;


    @ManyToOne
    @JoinColumn(name = "`Morador_id_papel`")
    private Morador morador;

    @ManyToOne
    @JoinColumn(name = "`Porteiro_id_papel`")
    private Porteiro porteiro;

    // ... restante do código (construtores, etc)



    public Encomenda(DadosRegistrarEncomendaDTO dados, Morador morador, Porteiro porteiro){

        this.nomePacote = dados.nomePacote();
        this.foto = dados.foto();
        this.statusEncomenda = StatusEncomenda.RECEBIDA;
        this.dataHoraRecebido = LocalDateTime.now();
        this.dataHoraRetirado = null;
        this.token = null;
        this.morador = morador;
        this.porteiro = porteiro;
        this.dataHoraRetirado = null;

    }

    public Long getidEncomenda() {
        return idEncomenda;
    }
}
    






