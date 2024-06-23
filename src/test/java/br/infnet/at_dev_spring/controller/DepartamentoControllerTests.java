package br.infnet.at_dev_spring.controller;

import br.infnet.at_dev_spring.model.Departamento;
import br.infnet.at_dev_spring.repository.DepartamentoRepository;
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
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartamentoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Departamento departamento;

    @BeforeEach
    public void setup() {
        departamento = new Departamento();
        departamento.setNome("Departamento de TI");
        departamento.setLocal("Sala 101");
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    @Transactional
    public void testCreateDepartamento() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/departamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Departamento de TI"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    @Transactional
    public void testGetAllDepartamentos() throws Exception {
        departamentoRepository.save(departamento);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/departamentos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Departamento de TI"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    @Transactional
    public void testUpdateDepartamento() throws Exception {
        departamentoRepository.save(departamento);
        departamento.setNome("Departamento Financeiro");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/departamentos/{id}", departamento.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departamento)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(departamento.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Departamento Financeiro"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin123", roles = "ADMIN")
    @Transactional
    public void testDeleteDepartamento() throws Exception {
        departamentoRepository.save(departamento);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/departamentos/{id}", departamento.getId()))
                .andExpect(status().isNoContent());

        List<Departamento> departamentos = departamentoRepository.findAll();
        assertThat(departamentos).hasSize(0);
    }
}

