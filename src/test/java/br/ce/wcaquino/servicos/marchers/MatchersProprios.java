package br.ce.wcaquino.servicos.marchers;

import java.util.Calendar;

import br.ce.wcaquino.utils.DataUtils;

public class MatchersProprios {

	public static DiaDaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaDaSemanaMatcher(diaSemana);
	}

	public static DiaDaSemanaMatcher caiNumaSegunda() {
		return new DiaDaSemanaMatcher(Calendar.MONDAY);
	}

	public static DataDiferencaDiaMatcher isHojeComDiferencadeDias(Integer qtdDias) {
		return new DataDiferencaDiaMatcher(qtdDias);
	}

	public static DataDiferencaDiaMatcher isHoje() {
		return new DataDiferencaDiaMatcher(0);
	}
}
