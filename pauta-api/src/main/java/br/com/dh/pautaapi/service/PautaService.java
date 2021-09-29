package br.com.dh.pautaapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dh.pautaapi.exceptions.EntityNotFoundException;
import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.model.ResultadoPauta;
import br.com.dh.pautaapi.model.Voto;
import br.com.dh.pautaapi.repository.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Pauta findByid(Long id) {
		return pautaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada: " + id));
	}

	public List<Pauta> listarPautas() {
		return pautaRepository.findAll();
	}

	public void deletarPauta(Long id) {
		if(!pautaRepository.existsById(id)) {
			throw new EntityNotFoundException("Pauta inválida: " + id);
		}
		pautaRepository.deleteById(id);
	}

	public Pauta salvarPauta(Pauta pauta) {
		
		if(pauta.getStatus() == null) {
			if (pauta.getDuracao() == null || pauta.getDuracao() < 1) {
				pauta.setDuracao(60000l);
			} else {
				pauta.setDuracao((pauta.getDuracao() * 60000)); // Convertendo para milisegundos
			}
		}
		
		return pautaRepository.save(pauta);
	}

	public Pauta abrirPauta(Pauta pauta) {
		
		Pauta pautaRecuperada = this.findByid(pauta.getId_pauta());
		pautaRecuperada.setDataInicioVotacao(System.currentTimeMillis());
		pautaRecuperada.setStatus("ABERTA");
		
		Pauta pautaSalva = this.pautaRepository.save(pautaRecuperada);
		return pautaSalva;
	}
	
	
	public boolean verificaValidadePauta(Long dataInicioVotacao, Long duracao) {
		Long dataAtual = System.currentTimeMillis();
		if ((dataInicioVotacao + duracao) >= dataAtual) {
			return true;
		}
		return false;
	}
	
	public ResultadoPauta verificaResultadoPauta(Pauta pautaRecuperada){
		
		int votosSim = 0;
		int votosNao = 0;
		
		for (Voto votoPauta : pautaRecuperada.getVotos()) {
			if (votoPauta.getVoto().equals("SIM")) {
				votosSim++;
			} else {
				votosNao++;
			}
		}

		ResultadoPauta resultadoPauta = new ResultadoPauta();
		resultadoPauta.setResultadoNao(votosNao);
		resultadoPauta.setResultadoSim(votosSim);

		return resultadoPauta;
		
	}

}
