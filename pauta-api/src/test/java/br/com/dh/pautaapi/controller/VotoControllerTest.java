package br.com.dh.pautaapi.controller;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.model.Voto;
import br.com.dh.pautaapi.service.VotoService;
import br.com.dh.pautaapi.util.DataCreator;

@ExtendWith(SpringExtension.class)
public class VotoControllerTest {
	
	@InjectMocks
	private VotoController votoController;
	
	@Mock
	private VotoService votoServiceMock;
	
	@BeforeEach
	private void configuracao() {
		
		BDDMockito.when(votoServiceMock.findPautaById(ArgumentMatchers.anyLong())).thenReturn(DataCreator.criaPautaParaAtualizacao());
		BDDMockito.when(votoServiceMock.buscaValidadePauta(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(true);
		BDDMockito.when(votoServiceMock.verificaCpfValido(ArgumentMatchers.anyString())).thenReturn(true);
		BDDMockito.when(votoServiceMock.cpfPodeVotar(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pauta.class))).thenReturn(true);
		BDDMockito.when(votoServiceMock.efetivarVoto(ArgumentMatchers.any(Voto.class))).thenReturn(DataCreator.criaVoto());
		
	}
	
	@Test
	@DisplayName("Votar Pauta Encerrada - Retorna string e statusCode OK quando bem-sucedido")
	public void deveRetornarSucesso_QuandoRealizarVotoPautaEncerrada() throws Exception {
		
		BDDMockito.when(votoServiceMock.buscaValidadePauta(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(false);
		ResponseEntity<?> votoEfetuado = votoController.votar(DataCreator.criaVoto(), 1l);
		
		Assertions.assertThat(votoEfetuado.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(votoEfetuado).isNotNull();
		Assertions.assertThat(votoEfetuado.getBody()).asString();
	}
	
	
	@Test
	@DisplayName("Votar CPF Inválido - Retorna string e statusCode OK quando bem-sucedido")
	public void deveRetornarSucesso_QuandoRealizarVotoCPFInvalido() throws Exception {
		
		BDDMockito.when(votoServiceMock.verificaCpfValido(ArgumentMatchers.anyString())).thenReturn(false);
		ResponseEntity<?> votoEfetuado = votoController.votar(DataCreator.criaVoto(), 1l);
		
		Assertions.assertThat(votoEfetuado.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(votoEfetuado).isNotNull();
		Assertions.assertThat(votoEfetuado.getBody()).asString();
	}
	
	@Test
	@DisplayName("Votar com CPF repetido - Retorna string e statusCode OK quando bem-sucedido")
	public void deveRetornarSucesso_QuandoRealizarVotoCPFRepetido() throws Exception {
		
		BDDMockito.when(votoServiceMock.cpfPodeVotar(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pauta.class))).thenReturn(false);
		ResponseEntity<?> votoEfetuado = votoController.votar(DataCreator.criaVoto(), 1l);
		
		Assertions.assertThat(votoEfetuado.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(votoEfetuado).isNotNull();
		Assertions.assertThat(votoEfetuado.getBody()).asString();
	}
	
	@Test
	@DisplayName("Votar - Retorna statusCode OK quando bem-sucedido")
	public void deveRetornarSucesso_QuandoRealizarVoto() throws Exception {
		
		ResponseEntity<?> votoEfetuado = votoController.votar(DataCreator.criaVoto(), 1l);
		
		Assertions.assertThat(votoEfetuado.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(votoEfetuado).isNotNull();
	}
	
}
