package br.infnet.at_dev_spring.repository;


import br.infnet.at_dev_spring.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}

