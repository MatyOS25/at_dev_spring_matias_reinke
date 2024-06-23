package br.infnet.at_dev_spring.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Usuario {

    @Id
    private Long id;

    private String nome;
    private String senha;
    private String papel;

    // Getters e setters são gerados automaticamente pelo Lombok
}

