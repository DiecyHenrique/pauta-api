package br.com.dh.pautaapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pauta")
public class Pauta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pauta;
	
	private String nome;
	private Long duracao;
	private Long dataInicioVotacao;

	@OneToMany(mappedBy = "pauta", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Voto> votos = new ArrayList<Voto>();

	public Long getId_pauta() {
		return id_pauta;
	}

	public void setId_pauta(Long id_pauta) {
		this.id_pauta = id_pauta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getDuracao() {
		return duracao;
	}

	public void setDuracao(Long duracao) {
		this.duracao = duracao;
	}

	public Long getDataInicioVotacao() {
		return dataInicioVotacao;
	}

	public void setDataInicioVotacao(Long dataInicioVotacao) {
		this.dataInicioVotacao = dataInicioVotacao;
	}

	public List<Voto> getVotos() {
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_pauta == null) ? 0 : id_pauta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pauta other = (Pauta) obj;
		if (id_pauta == null) {
			if (other.id_pauta != null)
				return false;
		} else if (!id_pauta.equals(other.id_pauta))
			return false;
		return true;
	}

}
