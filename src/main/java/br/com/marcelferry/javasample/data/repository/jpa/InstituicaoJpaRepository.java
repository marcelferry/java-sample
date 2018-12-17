package br.com.marcelferry.javasample.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.marcelferry.javasample.data.model.jpa.InstituicaoEntity;

/**
 * Repository : Instituicao.
 */
public interface InstituicaoJpaRepository extends PagingAndSortingRepository<InstituicaoEntity, Long> {

	InstituicaoEntity findByDescricao(String descricao);

}
