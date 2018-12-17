/*
 * Created on 4 abr 2018 ( Time 10:40:11 )
 */
package br.com.marcelferry.javasample.business.service;

import java.util.List;

import br.com.marcelferry.javasample.data.model.Instituicao;

/**
 * Business Service Interface for entity Instituicao.
 */
public interface InstituicaoService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Instituicao findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Instituicao> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Instituicao save(Instituicao entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Instituicao update(Instituicao entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Instituicao create(Instituicao entity);
	
	/**
	 * Verify if exists the given entity in the database
	 * @param entity
	 * @return
	 */
	boolean exists(Instituicao entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
