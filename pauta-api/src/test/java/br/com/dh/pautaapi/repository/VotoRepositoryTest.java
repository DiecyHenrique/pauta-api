package br.com.dh.pautaapi.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.model.Voto;

@DataJpaTest
@DisplayName("Testes para o Repositorio de VOTO")
public class VotoRepositoryTest {

	@Autowired
	private VotoRepository votoRepository;
	@Autowired
	private PautaRepository pautaRepository;

	@Test
	@DisplayName("Busca voto por ID")
	public void deveRetornarVoto_QuandoBuscarVotoPorId() {

		Voto voto = criaVoto();
		Voto votoSalvo = this.votoRepository.save(voto);

		Optional<Voto> votoRecuperado = this.votoRepository.findById(votoSalvo.getId_voto());

		Assertions.assertThat(votoRecuperado)
					.isNotNull()
					.isNotEmpty()
					.contains(votoSalvo);
	}
	
	@Test
	@DisplayName("Busca voto por CPF")
	public void deveRetornarVoto_QuandoBuscarVotoPorCPF() {
		Voto voto = criaVoto();
		Voto votoSalvo = this.votoRepository.save(voto);
		
		Voto votoRecuperado = this.votoRepository.findByCpf(votoSalvo.getCpf(), votoSalvo.getPauta());
		
		Assertions.assertThat(votoRecuperado).isNotNull();
		Assertions.assertThat(votoRecuperado.getId_voto()).isEqualTo(votoSalvo.getId_voto());
		Assertions.assertThat(votoRecuperado.getCpf()).isEqualTo(votoSalvo.getCpf());
	}

	@Test
	@DisplayName("Salva voto")
	public void deveRetornarSucesso_QuandoSalvarVoto() {
		Voto voto = criaVoto();
		Voto votoSalvo = this.votoRepository.save(voto);

		Assertions.assertThat(votoSalvo).isNotNull();
		Assertions.assertThat(votoSalvo.getId_voto()).isNotNull();
		Assertions.assertThat(votoSalvo.getCpf()).isEqualTo(voto.getCpf());
	}

	@Test
	@DisplayName("Atualiza voto")
	public void deveRetornarSucesso_QuandoAtualizarVoto() {

		Voto voto = criaVoto();
		Voto votoSalvo = this.votoRepository.save(voto);

		votoSalvo.setCpf("00000000000");
		votoSalvo.setVoto("NÃO");

		Voto votoAtualizado = this.votoRepository.save(votoSalvo);

		Assertions.assertThat(votoAtualizado).isNotNull();
		Assertions.assertThat(votoAtualizado.getId_voto()).isNotNull();
		Assertions.assertThat(votoAtualizado.getCpf()).isEqualTo(votoSalvo.getCpf());
		Assertions.assertThat(votoAtualizado.getVoto()).isEqualTo(votoSalvo.getVoto());
	}

	@Test
	@DisplayName("Deleta voto")
	public void deveRetornarSucesso_QuandoDeletarVoto() {
		Voto voto = criaVoto();
		Voto votoSalvo = this.votoRepository.save(voto);

		this.votoRepository.delete(votoSalvo);

		Optional<Voto> votoRecuperado = this.votoRepository.findById(votoSalvo.getId_voto());

		Assertions.assertThat(votoRecuperado).isEmpty();
	}

	private Voto criaVoto() {
		Voto voto = new Voto();
		Pauta pauta = this.criaPauta();

		voto.setCpf("11903737001");
		voto.setVoto("SIM");
		voto.setPauta(pauta);

		return voto;
	}

	private Pauta criaPauta() {
		Pauta pauta = new Pauta();
		pauta.setNome("Teste pauta");
		pauta.setDuracao(5l);

		return pautaRepository.save(pauta);
	}

}
