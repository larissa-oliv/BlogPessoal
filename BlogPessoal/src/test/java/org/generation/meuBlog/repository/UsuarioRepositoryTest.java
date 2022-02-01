package org.generation.meuBlog.repository;

import org.generation.blogPessoal.model.*;
import org.generation.blogPessoal.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository repository;
	
	@BeforeAll
	void start()
	{
		repository.save(new Usuario(0L,"Maria Alice da Silva", "mrasi123@gmail.com","teste123456","hjsajuash.png"));
		
		repository.save(new Usuario(0L,"Joana Martins de Oliveira", "jo_martins123@gmail.com","teste123456","hjsajuash.png"));

		repository.save(new Usuario(0L,"João Paulo de Assis", "mrasi123@gmail.com","teste123456","hjsajuash.png"));
		
		repository.save(new Usuario(0L,"José Augusto da Silva", "mrasi123@gmail.com","teste123456","hjsajuash.png"));

	}

	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario () {
		Optional<Usuario>usuario = UsuarioRepository.findByUsuario("mrasi123@gmail.com");
		assertTrue (usuario.get().getUsuario().equals("mrasi123@gmail.com"));
	}

	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios(){
		List<Usuario> listaDeUsuarios = UsuarioRepository. findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue (listaDeUsuarios.get(0).getNome().equals("Maria Alice da Silva"));
		assertTrue (listaDeUsuarios.get(0).getNome().equals("João Paulo de Assis"));
		assertTrue (listaDeUsuarios.get(0).getNome().equals("Joana Martins de Oliveira"));
	}
}
