package br.com.marcelferry.javasample.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.marcelferry.javasample.data.model.jpa.ContacorrenteEntity;

/**
 * Repository : Contacorrente.
 */
public interface ContacorrenteJpaRepository extends PagingAndSortingRepository<ContacorrenteEntity, Long> {

	ContacorrenteEntity findByDescricao(String descricao);

}
