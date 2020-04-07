package br.ce.wcaquino.servicos.marchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DataDiferencaDiaMatcher extends TypeSafeMatcher<Date> {

	private Integer qtdDias;
	
	public DataDiferencaDiaMatcher(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}
		
	@Override
	protected boolean matchesSafely(Date data) {
			return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
	}


}
