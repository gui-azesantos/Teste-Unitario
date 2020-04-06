package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
		
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 1, 0.0));

		// A��o
		Locacao Locacao = service.alugarFilme(usuario, filme);

		// Verifica��o
		error.checkThat(Locacao.getValor(), is(equalTo(0.0)));
		error.checkThat(DataUtils.isMesmaData(Locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(Locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));

	}

	@Test(expected = Exception.class)
	public void deveLnacarExcecao() throws Exception {
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// A��o
		service.alugarFilme(usuario, filme);
	}

	// Forma Robusta (Mais Completa)
	@SuppressWarnings("deprecation")
	@Test
	public void NaoDeveAlugarFilmeSemEstoque() throws FilmeSemEstoqueException {
		// Cen�rio
		LocacaoService locA��oService = new LocacaoService();
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 10, 5.0));

		// A��o
		try {
			locA��oService.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio Vazio"));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void NaoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException, LocadoraException {
		// C�nario
		LocacaoService service = new LocacaoService();
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 10, 5.0));
		Usuario usuario = new Usuario("Usu�rio 1");

		// A��o
		try {
			service.alugarFilme(usuario, filme);
			// Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Filme Vazio"));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void devePagar75porNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filme = Arrays.asList(new Filme("Filme 1", 1, 4.0), new Filme("Filme 2", 1, 4.0),
				new Filme("Filme 3", 1, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filme);

		// Verifica��o
		assertThat(resultado.getValor(), is(11.0));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 10, 4.0), new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 1, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verifica��o
		assertThat(resultado.getValor(), is(11.0));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 10, 4.0), new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 1, 4.0), new Filme("Filme 4", 1, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verifica��o
		assertThat(resultado.getValor(), is(13.0));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 10, 4.0), new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 1, 4.0), new Filme("Filme 4", 1, 4.0), new Filme("Filme 5", 1, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verifica��o
		assertThat(resultado.getValor(), is(14.0));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void devePagar00PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 10, 4.0), new Filme("Filme 2", 3, 4.0),
				new Filme("Filme 3", 1, 4.0), new Filme("Filme 4", 1, 4.0), new Filme("Filme 5", 1, 4.0),
				new Filme("Filme 6", 1, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verifica��o
		assertThat(resultado.getValor(), is(14.0));

	}

	@Test
	public void naoDeveDevolverFilmeDomingo() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 10, 4.0));

		// A��o
		Locacao retorno = service.alugarFilme(usuario, filmes);

		// Verifica��o
		boolean isSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(isSegunda);
	}

}