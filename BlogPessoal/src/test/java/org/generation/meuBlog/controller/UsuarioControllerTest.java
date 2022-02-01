package org.generation.meuBlog.controller;


import org.generation.blogPessoal.Service.*;
import org.generation.blogPessoal.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.*;
import org.springframework.http.*;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;


    @Test
    @Order(1)
    @DisplayName("Cadastrar um Usuario")
    public void deveCriarUmUsuario() {
        HttpEntity<Usuario>requisisao = new HttpEntity<Usuario>(new Usuario(0L,
                "Maria Alice da Silva", "mrasi123@gmail.com","teste123456"));
        ResponseEntity<Usuario> resposta = TestRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, requisisao, Usuario.class);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(requisisao.getBody().getNome(), resposta.getBody().getNome());
        assertEquals(requisisao.getBody().getUsuario(), resposta.getBody().getUsuario());
    }

    @Test
    @Order(2)
    @DisplayName("Não deve permitir a duplicação de Usuario")
    public void naoDeveDuplicarUsuario(){
        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Maria Alice da Silva", "mrasi123@gmail.com","teste123456"));
        HttpEntity<Usuario>requisisao = new HttpEntity<Usuario>(new Usuario(0L,
                "Maria Alice da Silva", "mrasi123@gmail.com","teste123456"));
        ResponseEntity<Usuario> resposta = TestRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, requisisao, Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    }
    @Test
    @Order(3)
    @DisplayName("Alterar um usuario")
    public void deveAlterarUmUsuario(){
        Optional<Usuario>usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L,
                "Joana Martins de Oliveira", "jo_martins123@gmail.com","teste123456"));

        Usuario usuarioUpdate =new Usuario(usuarioCreate.get().getId(),
        "Joana Martins de Oliveira", "jo_martins123@gmail.com","teste123456");

        HttpEntity<Usuario>requisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario>resposta = testRestTemplate
                .whitBasicAuth("root", "root")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
        assertEquals(usuarioUpdate. getUsuario(), resposta.getBody().getUsuario());
    }

    @Test
    @Order(4)
    @DisplayName("Listar todos os usuarios")
    public void deveMostrarTodosOsUsuarios(){

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Sabrina Sanches", "Sabrina_sanches@gmail.com", "S@sanches123" ));

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "André Castilho", "andrecs@gmail.com", "123andrecast" ));

        ResponseEntity<String> resposta = testRestTemplate
                .whitBasicAuth("root", "root")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }





}
