package com.projeto.shopee.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.dto.UsuarioDTO;
import com.projeto.shopee.dto.EnderecoDTO;
import com.projeto.shopee.dto.UsuarioAutenticarDTO;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.UsuarioRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.projeto.shopee.repository.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LojaControllerEndPointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private LojaDTO novaLoja;
    private UsuarioDTO novoUsuario;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();
        lojaRepository.deleteAll();
        usuarioRepository.deleteAll();

        novoUsuario = new UsuarioDTO();
        novoUsuario.setNome("Usuário Teste");
        novoUsuario.setEmail("teste@teste.com");
        novoUsuario.setTelefone("48988613333");
        novoUsuario.setCpf("13164097913");
        novoUsuario.setDataNascimento(LocalDate.now().minusYears(13));

        EnderecoDTO novoEnderecoDTO = new EnderecoDTO();
        novoEnderecoDTO.setCep("88075520");
        novoEnderecoDTO.setRua("Rua Exemplo");
        novoEnderecoDTO.setNumero("123");
        novoEnderecoDTO.setCidade("Cidade Exemplo");
        novoEnderecoDTO.setEstado("Estado Exemplo");
        novoEnderecoDTO.setPais("País Exemplo");
        novoUsuario.setEnderecoDTO(novoEnderecoDTO);

        UsuarioAutenticarDTO novoUsuarioAutenticar = new UsuarioAutenticarDTO();
        novoUsuarioAutenticar.setUsername("teste@teste.com");
        novoUsuarioAutenticar.setPasswordHash("Senha123!");
        novoUsuario.setUsuarioAutenticarDTO(novoUsuarioAutenticar);

        novaLoja = new LojaDTO();
        novaLoja.setNome("Loja Teste");
        novaLoja.setCategoriaLojaId(1L); 

        objectMapper.registerModule(new JavaTimeModule()); 

        session = new MockHttpSession();
    }

    @Test
    void criarUsuarioECriarLoja() throws Exception {
        String usuarioNovoJson = objectMapper.writeValueAsString(novoUsuario);
        this.mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioNovoJson))
                .andExpect(status().isOk());

        Usuario usuarioCriado = usuarioRepository.findByEmail(novoUsuario.getEmail());

        session.setAttribute("userId", usuarioCriado.getId());
        novaLoja.setUsuarioId(usuarioCriado.getId());

        String lojaNovaJson = objectMapper.writeValueAsString(novaLoja);
        this.mockMvc.perform(post("/lojas")
                .session(session) 
                .contentType(MediaType.APPLICATION_JSON)
                .content(lojaNovaJson))
                .andExpect(status().isOk());
    }

    @Test
    void naoDeveCriarLojaSeUsuarioJaPossui() throws Exception {
        String usuarioNovoJson = objectMapper.writeValueAsString(novoUsuario);
        this.mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioNovoJson))
                .andExpect(status().isOk());

        Usuario usuarioCriado = usuarioRepository.findByEmail(novoUsuario.getEmail());

        session.setAttribute("userId", usuarioCriado.getId());
        novaLoja.setUsuarioId(usuarioCriado.getId());

        Loja lojaExistente = new Loja();
        lojaExistente.setNome("Loja Existente");
        lojaExistente.setUsuario(usuarioCriado);
        lojaRepository.save(lojaExistente);

 
        String lojaNovaJson = objectMapper.writeValueAsString(novaLoja);
        this.mockMvc.perform(post("/lojas")
                .session(session) 
                .contentType(MediaType.APPLICATION_JSON)
                .content(lojaNovaJson))
                .andExpect(status().isBadRequest());
    }
} 