package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exception.FilmeSemEstoqueException;
import br.ce.wcaquino.exception.LocadoraExcepetion;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private LocacaoService service;

	@Before
	public void setup() {
		service = new LocacaoService();
	}

	@Test
	public void testeLocacao() throws Exception {
		// Cenário
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 1, 0.0));

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);

		// Verificação
		error.checkThat(locacao.getValor(), is(equalTo(0.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));

	}

	@Test(expected = Exception.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// Cenário
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// Ação
		service.alugarFilme(usuario, filme);
	}

	// Forma Robusta (Mais Completa)
	@SuppressWarnings("deprecation")
	@Test
	public void testeLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// Cenário
		LocacaoService locacaoService = new LocacaoService();
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 10, 5.0));

		// Ação
		try {
			locacaoService.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraExcepetion e) {
			assertThat(e.getMessage(), is("Usuário Vazio"));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testeLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraExcepetion {
		// Cénario
		LocacaoService service = new LocacaoService();
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 10, 5.0));
		Usuario usuario = new Usuario("Usuário 1");

		// Ação
		try {
			service.alugarFilme(usuario, filme);
			// Assert.fail();
		} catch (LocadoraExcepetion e) {
			assertThat(e.getMessage(), is("Filme Vazio"));
		}
	}
}