package br.com.dh.pautaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.dh.pautaapi.model.Pauta;
import br.com.dh.pautaapi.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
	
	@Query("select v from Voto v where v.cpf = ?1 and v.pauta = ?2")
	Voto findByCpf(String cpf, Pauta pauta);
	
}
