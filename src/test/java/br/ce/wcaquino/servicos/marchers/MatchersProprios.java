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
}
