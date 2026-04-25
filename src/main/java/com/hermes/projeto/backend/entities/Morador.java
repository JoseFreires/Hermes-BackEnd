package com.hermes.projeto.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@PrimaryKeyJoinColumn(name = "idPessoa")
@Entity (name = "Morador")
@Table(name = "morador")
public class Morador extends Pessoa{

}