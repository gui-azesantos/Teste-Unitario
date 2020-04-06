package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exception.NaoPodeDividirPorZeroException;

public class CalculadoraTest {

	private Calculadora calc;
	
	@Before
	public void sutup() {
		 calc = new Calculadora();
	}
	
	@Test
	public void DeveSomarDoisValores() {
		// C�nario
		int a = 5;
		int b = 3;
	
		// A��o
		int resultado = calc.somar(a, b);

		// Verifica��o
		
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSutrairDoisValores() {
		// C�nario
		int a = 8;
		int b = 5;
	
		
		// A��o
		int resultado = calc.subtrair( a,  b);
		
		// Verifica��o
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		int a = 6;
		int b = 3;
	
		
		//A��o
		int resultado = calc.dividir(a,b);
		
		//Verifica��o
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExececaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		int a = 10;
		int b = 0;
		
		
		//A��o
		int resultado = calc.dividir(a,b);
		
		//Verifica��o
		Assert.assertEquals(2, resultado);
	}
	
}
