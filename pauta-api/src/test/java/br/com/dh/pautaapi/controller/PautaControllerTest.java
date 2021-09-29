package br.com.dh.pautaapi.controller;

import java.util.List;

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
import br.com.dh.pautaapi.service.PautaService;
import br.com.dh.pautaapi.util.DataCreator;

@ExtendWith(SpringExtension.class)
public class PautaControllerTest {
	
	@InjectMocks
	private PautaController pautaController;
	
	@Mock
	private PautaService pautaServiceMock;
	
	@BeforeEach
	public void configuracao() {
		
		BDDMockito.when(pautaServiceMock.listarPautas()).thenReturn(List.of(DataCreator.criaPauta()));
		BDDMockito.when(pautaServiceMock.salvarPauta(ArgumentMatchers.any(Pauta.class))).thenReturn(DataCreator.criaPautaParaAtualizacao());
		BDDMockito.doNothing().when(pautaServiceMock).deletarPauta(ArgumentMatchers.anyLong());
		BDDMockito.when(pautaServiceMock.findByid(ArgumentMatchers.anyLong())).thenReturn(DataCreator.criaPautaParaAtualizacao());
		BDDMockito.when(pautaServiceMock.verificaResultadoPauta(ArgumentMatchers.any(Pauta.class))).thenReturn(DataCreator.criaResultadoPauta());
		
	}
	
	@Test
	@DisplayName("Listar - Retorna statusCode OK quando bem-sucedido")
	public void deveRetornarSucesso_QuandoBuscarPautas() {
		ResponseEntity<List<Pauta>> pautas = pautaController.listarPautas();
		Assertions.assertThat(pautas.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Salvar - Retorna statusCode CREATED quando bem-sucedido")
	public void deveRetornarSucesso_QuandoSalvarUmaPauta() {
		
		ResponseEntity<Pauta> pauta = pautaController.salvar(DataCreator.criaPauta());
		
		Assertions.assertThat(pauta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Assertions.assertThat(pauta.getBody()).isNotNull();
		Assertions.assertThat(pauta.getBody().getNome()).isEqualTo(DataCreator.criaPauta().getNome());
		Assertions.assertThat(pauta.getBody().getDuracao()).isEqualTo(DataCreator.criaPauta().getDuracao());
		
	}
	
	@Test
	@DisplayName("Remover Pauta - Retorna statusCode OK quando bem-sucedido")
	public void deveRetornarSucesso_QuandoRemoverPauta() {
		ResponseEntity<String> pautaDeletada = pautaController.remover(DataCreator.criaPautaParaAtualizacao().getId_pauta());
		Assertions.assertThat(pautaDeletada.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Abrir pauta - Retorna statusCode OK quando bem-sucedido")
	public void deveRetornarSucesso_QuandoAbrirPauta() {
		ResponseEntity<Pauta> pauta = pautaController.abrirVotacao(DataCreator.criaPautaParaAtualizacao());
		Assertions.assertThat(pauta.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Resultado Pauta - Retorna statusCode OK quando bem-sucedido")
	public void deveRetornarSucesso_QuandoBuscarResultadoPauta() {
		
		BDDMockito.when(pautaServiceMock.verificaValidadePauta(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(true);
		
		ResponseEntity<?> resultadoPauta = pautaController.buscarResultadoPauta(1l);
		
		Assertions.assertThat(resultadoPauta.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(resultadoPauta).isNotNull();
		
	}
	
	@Test
	@DisplayName("Resultado Pauta Aberta - Retorna string e statusCode OK quando bem-sucedido")
	public void deveRetornarMessagem_QuandoBuscarResultadoPautaAberta() {
		
		BDDMockito.when(pautaServiceMock.verificaValidadePauta(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(false);
		
		ResponseEntity<?> resultadoPauta = pautaController.buscarResultadoPauta(1l);
		
		Assertions.assertThat(resultadoPauta.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(resultadoPauta.getBody()).asString();
	}
	
}
