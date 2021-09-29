package br.com.dh.pautaapi.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.util.DataCreator;

@DataJpaTest
@DisplayName("Testes para o Repositorio de Pauta")
public class PautaRepositoryTest {

	@Autowired
	private PautaRepository pautaRepository;

	@Test
	@DisplayName("Busca pauta por id quando bem-sucedido")
	public void deveRetornarSucesso_QuandoBuscarPautaPorID() {

		Pauta pauta = DataCreator.criaPauta();
		Pauta pautaSalva = this.pautaRepository.save(pauta);

		Optional<Pauta> pataEncontrada = this.pautaRepository.findById(pautaSalva.getId_pauta());

		Assertions.assertThat(pataEncontrada)
					.isNotNull()
					.isNotEmpty()
					.contains(pautaSalva);

	}

	@Test
	@DisplayName("Busca deve retornar vazio quando o id não existe")
	public void deveRetornarVazio_QuandoBuscarPautaNaoExistente() {

		Optional<Pauta> pataEncontrada = this.pautaRepository.findById(1l);
		Assertions.assertThat(pataEncontrada).isEmpty();

	}

	@Test
	@DisplayName("Salva pauta quando bem-sucedido")
	public void deveRetornarSucesso_QuandoSalvarPauta() {

		Pauta pauta = DataCreator.criaPauta();
		Pauta pautaSalva = this.pautaRepository.save(pauta);

		Assertions.assertThat(pautaSalva).isNotNull();
		Assertions.assertThat(pautaSalva.getId_pauta()).isNotNull();
		Assertions.assertThat(pautaSalva.getNome()).isEqualTo(pauta.getNome());

	}

	@Test
	@DisplayName("Atualiza pauta quando bem-sucedido")
	public void deveRetornarSucesso_QuandoAtualizarPauta() {

		Pauta pauta = DataCreator.criaPauta();
		Pauta pautaSalva = this.pautaRepository.save(pauta);

		pautaSalva.setNome("Pauta Atualizada");
		pautaSalva.setDuracao(10l);

		Pauta pautaAtualizada = this.pautaRepository.save(pautaSalva);

		Assertions.assertThat(pautaAtualizada).isNotNull();
		Assertions.assertThat(pautaAtualizada.getId_pauta()).isNotNull();
		Assertions.assertThat(pautaAtualizada.getNome()).isEqualTo(pautaSalva.getNome());

	}

	@Test
	@DisplayName("Deleta pauta quando bem-sucedido")
	public void deveRetornarSucesso_QuandoDeletarPauta() {

		Pauta pauta = DataCreator.criaPauta();
		Pauta pautaSalva = this.pautaRepository.save(pauta);

		this.pautaRepository.delete(pautaSalva);

		Optional<Pauta> pautaRecuperada = this.pautaRepository.findById(pautaSalva.getId_pauta());

		Assertions.assertThat(pautaRecuperada).isEmpty();

	}

}
