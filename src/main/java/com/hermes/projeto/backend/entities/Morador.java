package com.hermes.projeto.backend.entities;

import com.hermes.projeto.backend.entities.security.Papel;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity (name = "Morador")
@Table(name = "morador")
@PrimaryKeyJoinColumn(name = "id_papel")
public class Morador extends Papel{



}
