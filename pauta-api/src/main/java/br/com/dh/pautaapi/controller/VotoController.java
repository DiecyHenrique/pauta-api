package br.com.dh.pautaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dh.pautaapi.model.Voto;
import br.com.dh.pautaapi.service.VotoService;

@RestController
@RequestMapping("/votacao")
public class VotoController {

	@Autowired
	private VotoService votoService;
	
	@PostMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> votar(@RequestBody Voto voto, @PathVariable(name = "id") Long id) throws Exception {
		
		voto.setPauta(votoService.findPautaById(id));
		
		if(!votoService.buscaValidadePauta(voto.getPauta().getDataInicioVotacao(), voto.getPauta().getDuracao())) {
			return new ResponseEntity<String>("Pauta encerrada!", HttpStatus.OK);
		}
		
		if (!votoService.verificaCpfValido(voto.getCpf())) {
			return new ResponseEntity<String>("Este associado não pode votar!", HttpStatus.OK);
		}
		
		if (!votoService.cpfPodeVotar(voto.getCpf(), voto.getPauta())) {
			return new ResponseEntity<String>("Este associado já votou!", HttpStatus.OK);
		}
		
		Voto votoComputado = votoService.efetivarVoto(voto);
		return new ResponseEntity<Voto>(votoComputado, HttpStatus.OK);

		/*
		try {
		
			if (votoService.verificaCpfValido(voto.getCpf())) {
				return new ResponseEntity<String>("Este associado não pode votar!", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>("CPF Inválido!", HttpStatus.OK);
		}
		*/
			
	}

}
