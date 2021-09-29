package br.com.dh.pautaapi.service;

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
import br.com.dh.pautaapi.model.Voto;
import br.com.dh.pautaapi.repository.VotoRepository;
import br.com.dh.pautaapi.util.DataCreator;

@ExtendWith(SpringExtension.class)
public class VotoServiceTest {

	@InjectMocks
	private VotoService votoService;
	
	@Mock
	private VotoRepository votoRepositoryMock;
	
	
	@BeforeEach
	public void configuracao() {
		BDDMockito.when(votoRepositoryMock.findByCpf(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pauta.class))).thenReturn(DataCreator.criaVoto());
		BDDMockito.when(votoRepositoryMock.save( ArgumentMatchers.any(Voto.class))).thenReturn(DataCreator.criaVoto());
	}
	
	@Test
	@DisplayName("Validacao CPF - Retorna sucesso quando bem-sucedido")
	public void deveRetornarSucesso_QuandoValidarCPF() {
		
		boolean cpfValido = this.votoService.verificaCpfValido(DataCreator.criaVoto().getCpf());
		
		Assertions.assertThatCode(() -> this.votoService.verificaCpfValido(DataCreator.criaVoto().getCpf())).doesNotThrowAnyException();
		Assertions.assertThat(cpfValido).isNotNull();
	}
	
	@Test
	@DisplayName("CPF Único na Votação - Retorna true quando CPF ainda não votou")
	public void deveRetornarSucesso_QuandoCPFUnicoVotacao() {
		
		BDDMockito.when(votoRepositoryMock.findByCpf(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pauta.class))).thenReturn(null);
		
		boolean cpfUnico = this.votoService.cpfPodeVotar("45903787010", DataCreator.criaVoto().getPauta());
		
		Assertions.assertThatCode(() -> this.votoService.cpfPodeVotar(DataCreator.criaVoto().getCpf(), DataCreator.criaVoto().getPauta())).doesNotThrowAnyException();
		Assertions.assertThat(cpfUnico).isNotNull().isTrue();
	}
	
	@Test
	@DisplayName("CPF Repetido na Votação - Retorna false quando CPF já tiver sido utilizado")
	public void deveRetornarSucesso_QuandoCPFRepetidoVotacao() {
		
		boolean cpfUnico = this.votoService.cpfPodeVotar(DataCreator.criaVoto().getCpf(), DataCreator.criaVoto().getPauta());
		
		Assertions.assertThatCode(() -> this.votoService.cpfPodeVotar(DataCreator.criaVoto().getCpf(), DataCreator.criaVoto().getPauta())).doesNotThrowAnyException();
		Assertions.assertThat(cpfUnico).isNotNull().isFalse();
	}
	
	@Test
	@DisplayName("Efetivar Voto - Retorna sucesso quando bem-sucedido")
	public void deveRetornarSucesso_QuandoEfetivarVoto() {
		
		Voto votoEfetivado = this.votoService.efetivarVoto(DataCreator.criaVoto());
		
		Assertions.assertThatCode(() -> this.votoService.efetivarVoto(DataCreator.criaVoto())).doesNotThrowAnyException();
		Assertions.assertThat(votoEfetivado).isNotNull();
		Assertions.assertThat(votoEfetivado.getPauta()).isEqualTo(DataCreator.criaVoto().getPauta());
		Assertions.assertThat(votoEfetivado.getVoto()).isEqualTo(DataCreator.criaVoto().getVoto());
		Assertions.assertThat(votoEfetivado.getCpf()).isEqualTo(DataCreator.criaVoto().getCpf());
	}
	
}
