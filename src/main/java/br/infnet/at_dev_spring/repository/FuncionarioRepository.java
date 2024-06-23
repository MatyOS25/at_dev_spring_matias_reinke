package br.infnet.at_dev_spring.repository;

import br.infnet.at_dev_spring.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
