/*
 * Created on 4 abr 2018 ( Time 10:40:10 )
 */
package br.com.marcelferry.javasample.business.service;

import java.util.List;

import br.com.marcelferry.javasample.data.model.Contacorrente;

/**
 * Business Service Interface for entity Contacorrente.
 */
public interface ContacorrenteService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Contacorrente findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Contacorrente> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Contacorrente save(Contacorrente entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Contacorrente update(Contacorrente entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Contacorrente create(Contacorrente entity);
	
	/**
	 * Verify if exists the given entity in the database
	 * @param entity
	 * @return
	 */
	boolean exists(Contacorrente entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
