package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import junit.framework.Assert;


public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {
		// Cen�rio
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usu�rio 1");
		Filme filme = new Filme("Filme 1", 10, 5.0);

		// A��o
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

		// Verifica��o
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));

	}

	@Test(expected = Exception.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// Cen�rio
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usu�rio 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// A��o
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

	}

	@Test
	public void testeLocacao_filmeSemEstoque2() {
		// Cen�rio
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Usu�rio 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// A��o
		try {
			locacaoService.alugarFilme(usuario, filme);
			Assert.fail("Lan�ar uma excecao");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}

	}

	@Test
	public void testeLocacao_filmeSemEstoque3() throws Exception {
		// Cen�rio
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario("Filme 1");
		Filme filme = new Filme("Usu�rio 1", 0, 5.0);


		expectedException.expect(Exception.class);
		expectedException.expectMessage("Filme sem estoque");
		// A��o
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
		

	}
}	

