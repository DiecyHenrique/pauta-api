package br.com.dh.pautaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.model.RetornoApiCPF;
import br.com.dh.pautaapi.model.Voto;
import br.com.dh.pautaapi.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private PautaService pautaService;

	private static final String URI = "https://user-info.herokuapp.com/users/";

	public boolean verificaCpfValido(String cpf) throws HttpClientErrorException {

		RestTemplate rest = new RestTemplate();
		RetornoApiCPF retornoApiCPF = rest.getForObject(URI + cpf, RetornoApiCPF.class);

		if (retornoApiCPF.getStatus().equals("ABLE_TO_VOTE")) {
			return true;
		}

		return false;

	}

	public boolean cpfPodeVotar(String cpf, Pauta pauta) {
		Voto voto = new Voto();
		voto = votoRepository.findByCpf(cpf, pauta);
		if (voto == null) {
			return true;
		}
		return false;
	}

	public Pauta findPautaById(Long id) {
		Pauta pautaEncontrada = pautaService.findByid(id);
		return pautaEncontrada;
	}

	public boolean buscaValidadePauta(Long dataInicioVotacao, Long duracao) {
		if (pautaService.verificaValidadePauta(dataInicioVotacao, duracao)) {
			return true;
		}
		return false;
	}

	public Voto efetivarVoto(Voto voto) {
		Voto votoComputado = votoRepository.save(voto);
		return votoComputado;
	}

}
