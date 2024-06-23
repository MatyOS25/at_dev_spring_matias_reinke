package br.infnet.at_dev_spring.repository;

import br.infnet.at_dev_spring.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
