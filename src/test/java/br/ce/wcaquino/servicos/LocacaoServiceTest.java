package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static br.ce.wcaquino.servicos.marchers.MatchersProprios.isHojeComDiferencadeDias;
import static br.ce.wcaquino.servicos.marchers.MatchersProprios.isHoje;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exception.FilmeSemEstoqueException;
import br.ce.wcaquino.exception.LocadoraException;
import br.ce.wcaquino.servicos.marchers.MatchersProprios;
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
	public void deveAlugar() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// Cenário
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 1, 0.0));

		// Ação
		Locacao Locacao = service.alugarFilme(usuario, filme);

		// Verificação
		error.checkThat(Locacao.getValor(), is(equalTo(0.0)));
		error.checkThat(Locacao.getDataRetorno(), isHojeComDiferencadeDias(1));
		error.checkThat(Locacao.getDataLocacao(), isHoje());
	}

	@Test(expected = Exception.class)
	public void deveLnacarExcecao() throws Exception {
		// Cenário
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// Ação
		service.alugarFilme(usuario, filme);
	}

	// Forma Robusta (Mais Completa)
	@SuppressWarnings("deprecation")
	@Test
	public void NaoDeveAlugarFilmeSemEstoque() throws FilmeSemEstoqueException {
		// Cenário
		LocacaoService locAçãoService = new LocacaoService();
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 10, 5.0));

		// Ação
		try {
			locAçãoService.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário Vazio"));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void NaoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException, LocadoraException {
		// Cénario
		LocacaoService service = new LocacaoService();
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 10, 5.0));
		Usuario usuario = new Usuario("Usuário 1");

		// Ação
		try {
			service.alugarFilme(usuario, filme);
			// Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Filme Vazio"));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void naoDeveDevolverFilmeDomingo() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));


		// Cenário
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 10, 4.0));

		// Ação
		Locacao retorno = service.alugarFilme(usuario, filmes);

		// Verificação
		assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	
	}
}
