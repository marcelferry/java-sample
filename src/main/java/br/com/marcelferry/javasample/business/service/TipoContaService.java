/*
 * Created on 4 abr 2018 ( Time 10:40:11 )
 */
package br.com.marcelferry.javasample.business.service;

import java.util.List;

import br.com.marcelferry.javasample.data.model.TipoConta;

/**
 * Business Service Interface for entity TipoConta.
 */
public interface TipoContaService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	TipoConta findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<TipoConta> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	TipoConta save(TipoConta entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	TipoConta update(TipoConta entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	TipoConta create(TipoConta entity);
	
	/**
	 * Verify if exists the given entity in the database
	 * @param entity
	 * @return
	 */
	boolean exists(TipoConta entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
