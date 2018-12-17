package br.com.marcelferry.javasample.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.marcelferry.javasample.data.model.jpa.TipoContaEntity;

/**
 * Repository : TipoConta.
 */
public interface TipoContaJpaRepository extends PagingAndSortingRepository<TipoContaEntity, Long> {

	TipoContaEntity findByDescricao(String descricao);

}
