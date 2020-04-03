package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {

	@Test
	public void test() {
		Assert.assertFalse(false);
		
	
		Usuario u1 = new Usuario("Usuario 1");
		
		
		Assert.assertEquals(u1, u1);
		
	}
}
