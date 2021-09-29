package br.com.dh.pautaapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.repository.PautaRepository;

@Configuration
@EnableScheduling
public class EncerramentoPautaService {

	private final long SEGUNDO = 60000;

	@Autowired
	private PautaRepository pautaRepository;

	@Scheduled(fixedDelay = SEGUNDO)
	public void executaEncerramentoPauta() {

		// LISTAR TODAS AS PAUTAS
		List<Pauta> pautas = pautaRepository.findAll();

		for (Pauta pauta : pautas) {

			if (pauta.getStatus() != null && !pauta.getStatus().equals("ENCERRADA")) {
				if ((pauta.getDataInicioVotacao() + pauta.getDuracao()) < System.currentTimeMillis()) {

					pauta.setStatus("ENCERRADA");
					pautaRepository.save(pauta);

				}
			}

		}

	}

}
