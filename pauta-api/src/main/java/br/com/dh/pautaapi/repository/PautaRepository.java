package br.com.dh.pautaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dh.pautaapi.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
}
