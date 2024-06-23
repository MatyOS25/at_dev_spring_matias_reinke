package br.infnet.at_dev_spring.controller;

import br.infnet.at_dev_spring.model.Funcionario;
import br.infnet.at_dev_spring.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FuncionarioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private Funcionario funcionario;

    @BeforeEach
    public void setup() {
        funcionario = new Funcionario();
        funcionario.setNome("João da Silva");
        funcionario.setEndereco("Rua Teste, 123");
        funcionario.setTelefone("(99) 9999-9999");
        funcionario.setEmail("joao.silva@example.com");
        funcionario.setDataNascimento(LocalDate.of(1990, 5, 15));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "USER")
    @Transactional
    public void testCreateFuncionario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("João da Silva"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "USER")
    @Transactional
    public void testGetAllFuncionarios() throws Exception {
        funcionarioRepository.save(funcionario);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("João da Silva"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "USER")
    @Transactional
    public void testUpdateFuncionario() throws Exception {
        funcionarioRepository.save(funcionario);
        funcionario.setNome("João Oliveira");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/funcionarios/{id}", funcionario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(funcionario.getId()))
                .andExpect(jsonPath("$.nome").value("João Oliveira"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "USER")
    @Transactional
    public void testDeleteFuncionario() throws Exception {
        funcionarioRepository.save(funcionario);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/funcionarios/{id}", funcionario.getId()))
                .andExpect(status().isNoContent());

        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        assertThat(funcionarios).hasSize(0);
    }
}




