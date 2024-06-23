package br.infnet.at_dev_spring.controller;

import br.infnet.at_dev_spring.model.Departamento;
import br.infnet.at_dev_spring.model.Funcionario;
import br.infnet.at_dev_spring.repository.DepartamentoRepository;
import br.infnet.at_dev_spring.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @GetMapping
    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    @PostMapping
    public Funcionario createFuncionario(@RequestBody Funcionario funcionario) {
        Departamento departamento = funcionario.getDepartamento();
        if (departamento != null && departamento.getId() == null) {
            departamento = departamentoRepository.save(departamento);
            funcionario.setDepartamento(departamento);
        }
        return funcionarioRepository.save(funcionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioDetails) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario not found for this id :: " + id));

        funcionario.setNome(funcionarioDetails.getNome());
        funcionario.setEndereco(funcionarioDetails.getEndereco());
        funcionario.setTelefone(funcionarioDetails.getTelefone());
        funcionario.setEmail(funcionarioDetails.getEmail());
        funcionario.setDataNascimento(funcionarioDetails.getDataNascimento());

        Departamento departamento = funcionarioDetails.getDepartamento();
        if (departamento != null) {
            if (departamento.getId() == null) {
                departamento = departamentoRepository.save(departamento);
            }
            funcionario.setDepartamento(departamento);
        }

        final Funcionario updatedFuncionario = funcionarioRepository.save(funcionario);
        return ResponseEntity.ok(updatedFuncionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario not found for this id :: " + id));

        funcionarioRepository.delete(funcionario);
        return ResponseEntity.noContent().build();
    }
}

