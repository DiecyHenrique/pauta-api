package br.com.dh.pautaapi.service;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.model.ResultadoPauta;
import br.com.dh.pautaapi.repository.PautaRepository;
import br.com.dh.pautaapi.util.DataCreator;

@ExtendWith(SpringExtension.class)
public class PautaServiceTest {

	@InjectMocks
	private PautaService pautaService;
	
	@Mock
	private PautaRepository pautaRepositoryMock;
	
	@BeforeEach
	public void configuracao() {
		
		BDDMockito.when(pautaRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(DataCreator.criaDadosPautaOptional());
		BDDMockito.when(pautaRepositoryMock.findAll()).thenReturn(List.of(DataCreator.criaPauta()));
		BDDMockito.when(pautaRepositoryMock.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
		BDDMockito.doNothing().when(pautaRepositoryMock).deleteById(ArgumentMatchers.anyLong());
		BDDMockito.when(pautaRepositoryMock.save(ArgumentMatchers.any(Pauta.class))).thenReturn(DataCreator.criaPautaParaAtualizacao());
		
	}
	
	@Test
	@DisplayName("Busca pelo ID - Retorna o objeto Pauta quando bem-sucedido")
	public void deveRetornarSucesso_QuandoBuscarPautaPeloID() {
		
		Pauta pautaRetornada = this.pautaService.findByid(DataCreator.criaPautaParaAtualizacao().getId_pauta());
		
		Assertions.assertThat(pautaRetornada).isNotNull();
		Assertions.assertThat(pautaRetornada.getNome()).isEqualTo(DataCreator.criaPautaParaAtualizacao().getNome());
		
	}
	
	@Test
	@DisplayName("Lista - Retorna Lista de pautas quando bem-sucedido")
	public void deveRetornarSucesso_QuandoBuscarListaPautas() {
		
		List<Pauta> listaPautas = pautaService.listarPautas();
		Assertions.assertThat(listaPautas).isNotNull().isNotEmpty();
		
	}
	
	@Test
	@DisplayName("Deleta  - Retorna sucesso quando bem-sucedido")
	public void deveRetornarSucesso_QuandoDeletarPauta() {
		Assertions.assertThatCode(() -> this.pautaService.deletarPauta(DataCreator.criaPautaParaAtualizacao().getId_pauta())).doesNotThrowAnyException();
	}
	
	@Test
	@DisplayName("Salvar - Retorna pauta quando bem-sucedido")
	public void deveRetornarSucesso_QuandoSalvarPauta() {
		
		Pauta pautaSalva = this.pautaService.salvarPauta(DataCreator.criaPauta());
		
		Assertions.assertThat(pautaSalva).isNotNull();
		Assertions.assertThat(pautaSalva.getNome()).isEqualTo(DataCreator.criaPauta().getNome());
		Assertions.assertThat(pautaSalva.getDuracao()).isEqualTo(DataCreator.criaPauta().getDuracao());
		
	}
	
	@Test
	@DisplayName("Abrir Pauta - Retorna pauta quando bem-sucedido")
	public void deveRetornarSucesso_QuandoAbrirPauta() {
		
		Pauta pautaAberta = this.pautaService.abrirPauta(DataCreator.criaPautaParaAtualizacao());
		
		Assertions.assertThat(pautaAberta).isNotNull();
		Assertions.assertThat(pautaAberta.getNome()).isEqualTo(DataCreator.criaPautaParaAtualizacao().getNome());
		Assertions.assertThat(pautaAberta.getDataInicioVotacao()).isEqualTo(DataCreator.criaPautaParaAtualizacao().getDataInicioVotacao());
		
	}
	
	@Test
	@DisplayName("Validade - Retorna true quando a pauta é válida")
	public void deveRetornarSucesso_QuandoVerificaPautaValida() {
		
		boolean pautaValida = this.pautaService.verificaValidadePauta(System.currentTimeMillis(), 60000l);
		
		Assertions.assertThat(pautaValida).isNotNull();
		Assertions.assertThat(pautaValida).isTrue();
		
	}
	
	@Test
	@DisplayName("Validade - Retorna false quando a pauta é inválida")
	public void deveRetornarSucesso_QuandoVerificaPautaInvalida() {
		
		boolean pautaValida = this.pautaService.verificaValidadePauta(1632896693433l, 60000l);
		
		Assertions.assertThat(pautaValida).isNotNull();
		Assertions.assertThat(pautaValida).isFalse();
		
	}
	
	@Test
	@DisplayName("Resultado pauta - Retorna false quando a pauta é inválida")
	public void deveRetornarSucesso_QuandoVerificaResultadoPauta() {
		
		ResultadoPauta resultadoPauta = this.pautaService.verificaResultadoPauta(DataCreator.criaPautaParaAtualizacao());
		Assertions.assertThat(resultadoPauta).isNotNull();
		
	}
	
}
