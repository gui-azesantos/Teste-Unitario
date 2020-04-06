package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exception.FilmeSemEstoqueException;
import br.ce.wcaquino.exception.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	private LocacaoService service;

	@Parameter
	public List<Filme> filme;

	@Parameter(value = 01)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String cenario;

	@Before
	public void setuo() {
		service = new LocacaoService();
	}

	private static Filme filme1 = new Filme("Filme 1", 10, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 1, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 1, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 1, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 1, 4.0);
	private static Filme filme6 = new Filme("Filme 6", 1, 4.0);
	private static Filme filme7 = new Filme("Filme 7", 1, 4.0);
	
	@Parameters(name="{2}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][]{
				{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes 25%"},
				{Arrays.asList(filme1, filme2, filme3, filme4),13.0,  "4 Filmes 50%" },
				{Arrays.asList(filme1, filme2, filme3, filme4, filme5),14.0,  "5 Filmes 75%" },
				{Arrays.asList(filme1, filme2, filme3, filme4,filme5,filme6),14.0,  "6 Filmes 100%" },
				{Arrays.asList(filme1, filme2, filme3, filme4,filme5,filme6, filme7),18.0,  "7 Filmes: sem desconto" },
	});
	}

	@SuppressWarnings("deprecation")
	@Test
	public void deveCalcularValorLocacao() throws FilmeSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filme);

		// Verifica��o
		assertThat(resultado.getValor(), is(valorLocacao));

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
}
