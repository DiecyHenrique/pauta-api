package br.com.dh.pautaapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.model.ResultadoPauta;
import br.com.dh.pautaapi.service.PautaService;

@RestController
@RequestMapping("/pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@GetMapping
	public ResponseEntity<List<Pauta>> listarPautas() {
		List<Pauta> pautas = pautaService.listarPautas();
		return new ResponseEntity<List<Pauta>>(pautas, HttpStatus.OK);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<Pauta> salvar(@RequestBody Pauta pauta) {
		Pauta pautaCadastrada = pautaService.salvarPauta(pauta);
		return new ResponseEntity<Pauta>(pautaCadastrada, HttpStatus.CREATED);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> remover(@PathVariable(name = "id") Long id) {
		pautaService.deletarPauta(id);
		return new ResponseEntity<String>("Pauta " + id + " removida", HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Pauta> abrirVotacao(@RequestBody Pauta pauta) {
		return new ResponseEntity<Pauta>(pautaService.abrirPauta(pauta), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarResultadoPauta(@PathVariable(name = "id") Long id) {
		
		Pauta pautaRecuperada = pautaService.findByid(id);
		
		if (pautaService.verificaValidadePauta(pautaRecuperada.getDataInicioVotacao(), pautaRecuperada.getDuracao())) {
			return new ResponseEntity<String>("A votação ainda está aberta", HttpStatus.OK);
		}
			
		ResultadoPauta resultadoPauta = pautaService.verificaResultadoPauta(pautaRecuperada);
		
		return new ResponseEntity<ResultadoPauta>(resultadoPauta, HttpStatus.OK);
		
	}

}
