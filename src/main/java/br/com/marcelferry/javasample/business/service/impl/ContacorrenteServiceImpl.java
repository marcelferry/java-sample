/*
 * Created on 4 abr 2018 ( Time 10:39:56 )
 */
package br.com.marcelferry.javasample.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.marcelferry.javasample.business.service.ContacorrenteService;
import br.com.marcelferry.javasample.business.service.mapping.ContacorrenteServiceMapper;
import br.com.marcelferry.javasample.data.model.Contacorrente;
import br.com.marcelferry.javasample.data.model.jpa.ContacorrenteEntity;
import br.com.marcelferry.javasample.data.repository.jpa.ContacorrenteJpaRepository;

/**
 * Implementation of ContacorrenteService
 */
@Component
@Transactional
public class ContacorrenteServiceImpl implements ContacorrenteService {

	@Resource
	private ContacorrenteJpaRepository contacorrenteJpaRepository;

	@Resource
	private ContacorrenteServiceMapper contacorrenteServiceMapper;
	
	@Override
	public Contacorrente findById(Long id) {
		ContacorrenteEntity contacorrenteEntity = contacorrenteJpaRepository.findOne(id);
		return contacorrenteServiceMapper.mapContacorrenteEntityToContacorrente(contacorrenteEntity);
	}

	@Override
	public List<Contacorrente> findAll() {
		Iterable<ContacorrenteEntity> entities = contacorrenteJpaRepository.findAll();
		List<Contacorrente> beans = new ArrayList<>();
		for(ContacorrenteEntity contacorrenteEntity : entities) {
			beans.add(contacorrenteServiceMapper.mapContacorrenteEntityToContacorrente(contacorrenteEntity));
		}
		return beans;
	}

	@Override
	public Contacorrente save(Contacorrente contacorrente) {
		return update(contacorrente) ;
	}

	@Override
	public Contacorrente create(Contacorrente contacorrente) {
		ContacorrenteEntity contacorrenteEntity = null;
		if(contacorrente.getId() != null) {
			contacorrenteEntity = contacorrenteJpaRepository.findOne(contacorrente.getId());
		}
		if( contacorrenteEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		contacorrenteEntity = new ContacorrenteEntity();
		contacorrenteServiceMapper.mapContacorrenteToContacorrenteEntity(contacorrente, contacorrenteEntity);
		ContacorrenteEntity contacorrenteEntitySaved = contacorrenteJpaRepository.save(contacorrenteEntity);
		return contacorrenteServiceMapper.mapContacorrenteEntityToContacorrente(contacorrenteEntitySaved);
	}

	@Override
	public Contacorrente update(Contacorrente contacorrente) {
		ContacorrenteEntity contacorrenteEntity = contacorrenteJpaRepository.findOne(contacorrente.getId());
		contacorrenteServiceMapper.mapContacorrenteToContacorrenteEntity(contacorrente, contacorrenteEntity);
		ContacorrenteEntity contacorrenteEntitySaved = contacorrenteJpaRepository.save(contacorrenteEntity);
		return contacorrenteServiceMapper.mapContacorrenteEntityToContacorrente(contacorrenteEntitySaved);
	}
	
	@Override
	public boolean exists(Contacorrente contacorrente) {
		ContacorrenteEntity contacorrenteEntity = contacorrenteJpaRepository.findByDescricao(contacorrente.getDescricao());
		return ( contacorrenteEntity != null );
	}

	@Override
	public void delete(Long id) {
		contacorrenteJpaRepository.delete(id);
	}

	public void setContacorrenteJpaRepository(ContacorrenteJpaRepository contacorrenteJpaRepository) {
		this.contacorrenteJpaRepository = contacorrenteJpaRepository;
	}

	public void setContacorrenteServiceMapper(ContacorrenteServiceMapper contacorrenteServiceMapper) {
		this.contacorrenteServiceMapper = contacorrenteServiceMapper;
	}

}
