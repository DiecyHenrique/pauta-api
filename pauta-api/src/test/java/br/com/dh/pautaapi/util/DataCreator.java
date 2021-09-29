package br.com.dh.pautaapi.util;

import java.util.Optional;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.model.ResultadoPauta;
import br.com.dh.pautaapi.model.Voto;

public class DataCreator {
	
	public static Pauta criaPauta() {
		Pauta pauta = new Pauta();
		pauta.setNome("Teste pauta");
		pauta.setDuracao(300000l);
		return pauta;
	}
	
	public static Pauta criaPautaParaAtualizacao() {
		Pauta pauta = new Pauta();
		pauta.setId_pauta(1l);
		pauta.setNome("Teste pauta");
		pauta.setDuracao(300000l);
		pauta.setDataInicioVotacao(1632881364969l);
		return pauta;
	}
	
	public static ResultadoPauta criaResultadoPauta() {
		ResultadoPauta resultadoPauta = new ResultadoPauta();
		
		resultadoPauta.setResultadoNao(5);
		resultadoPauta.setResultadoSim(10);
		
		return resultadoPauta;
	}
	
	public static Voto criaVoto() {
		Voto voto = new Voto();
		Pauta pautaRecebida = criaPautaParaAtualizacao();
		
		voto.setCpf("11903737001");
		voto.setPauta(pautaRecebida);
		voto.setVoto("SIM");
		
		return voto;
	}
	
	
	public static Optional<Pauta> criaDadosPautaOptional() {
		Optional<Pauta> pauta = Optional.ofNullable(new Pauta());
		
		pauta.get().setId_pauta(1l);
		pauta.get().setNome("Teste pauta");
		pauta.get().setDuracao(300000l);
		pauta.get().setDataInicioVotacao(1632881364969l);
		
		return pauta;
	}
	
}
