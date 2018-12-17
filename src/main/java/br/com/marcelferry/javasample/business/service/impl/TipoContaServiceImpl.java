/*
 * Created on 4 abr 2018 ( Time 10:39:57 )
 */
package br.com.marcelferry.javasample.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.marcelferry.javasample.business.service.TipoContaService;
import br.com.marcelferry.javasample.business.service.mapping.TipoContaServiceMapper;
import br.com.marcelferry.javasample.data.model.TipoConta;
import br.com.marcelferry.javasample.data.model.jpa.TipoContaEntity;
import br.com.marcelferry.javasample.data.repository.jpa.TipoContaJpaRepository;

/**
 * Implementation of TipoContaService
 */
@Component
@Transactional
public class TipoContaServiceImpl implements TipoContaService {

	@Resource
	private TipoContaJpaRepository tipoContaJpaRepository;

	@Resource
	private TipoContaServiceMapper tipoContaServiceMapper;
	
	@Override
	public TipoConta findById(Long id) {
		TipoContaEntity tipoContaEntity = tipoContaJpaRepository.findOne(id);
		return tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntity);
	}

	@Override
	public List<TipoConta> findAll() {
		Iterable<TipoContaEntity> entities = tipoContaJpaRepository.findAll();
		List<TipoConta> beans = new ArrayList<>();
		for(TipoContaEntity tipoContaEntity : entities) {
			beans.add(tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntity));
		}
		return beans;
	}

	@Override
	public TipoConta save(TipoConta tipoConta) {
		return update(tipoConta) ;
	}

	@Override
	public TipoConta create(TipoConta tipoConta) {
		TipoContaEntity tipoContaEntity = null;
		if(tipoConta.getId() != null) {
			tipoContaEntity = tipoContaJpaRepository.findOne(tipoConta.getId());
		}
		if( tipoContaEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		tipoContaEntity = new TipoContaEntity();
		tipoContaServiceMapper.mapTipoContaToTipoContaEntity(tipoConta, tipoContaEntity);
		TipoContaEntity tipoContaEntitySaved = tipoContaJpaRepository.save(tipoContaEntity);
		return tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntitySaved);
	}

	@Override
	public TipoConta update(TipoConta tipoConta) {
		TipoContaEntity tipoContaEntity = tipoContaJpaRepository.findOne(tipoConta.getId());
		tipoContaServiceMapper.mapTipoContaToTipoContaEntity(tipoConta, tipoContaEntity);
		TipoContaEntity tipoContaEntitySaved = tipoContaJpaRepository.save(tipoContaEntity);
		return tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntitySaved);
	}
	
	@Override
	public boolean exists(TipoConta tipoConta) {
		TipoContaEntity tipoContaEntity = tipoContaJpaRepository.findByDescricao(tipoConta.getDescricao());
		return ( tipoContaEntity != null );
	}

	@Override
	public void delete(Long id) {
		tipoContaJpaRepository.delete(id);
	}

	public void setTipoContaJpaRepository(TipoContaJpaRepository tipoContaJpaRepository) {
		this.tipoContaJpaRepository = tipoContaJpaRepository;
	}

	public void setTipoContaServiceMapper(TipoContaServiceMapper tipoContaServiceMapper) {
		this.tipoContaServiceMapper = tipoContaServiceMapper;
	}

}
