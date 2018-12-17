/*
 * Created on 4 abr 2018 ( Time 10:39:56 )
 */
package br.com.marcelferry.javasample.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.marcelferry.javasample.business.service.InstituicaoService;
import br.com.marcelferry.javasample.business.service.mapping.InstituicaoServiceMapper;
import br.com.marcelferry.javasample.data.model.Instituicao;
import br.com.marcelferry.javasample.data.model.jpa.InstituicaoEntity;
import br.com.marcelferry.javasample.data.repository.jpa.InstituicaoJpaRepository;

/**
 * Implementation of InstituicaoService
 */
@Component
@Transactional
public class InstituicaoServiceImpl implements InstituicaoService {

	@Resource
	private InstituicaoJpaRepository instituicaoJpaRepository;

	@Resource
	private InstituicaoServiceMapper instituicaoServiceMapper;
	
	@Override
	public Instituicao findById(Long id) {
		InstituicaoEntity instituicaoEntity = instituicaoJpaRepository.findOne(id);
		return instituicaoServiceMapper.mapInstituicaoEntityToInstituicao(instituicaoEntity);
	}

	@Override
	public List<Instituicao> findAll() {
		Iterable<InstituicaoEntity> entities = instituicaoJpaRepository.findAll();
		List<Instituicao> beans = new ArrayList<>();
		for(InstituicaoEntity instituicaoEntity : entities) {
			beans.add(instituicaoServiceMapper.mapInstituicaoEntityToInstituicao(instituicaoEntity));
		}
		return beans;
	}

	@Override
	public Instituicao save(Instituicao instituicao) {
		return update(instituicao) ;
	}

	@Override
	public Instituicao create(Instituicao instituicao) {
		InstituicaoEntity instituicaoEntity = null;
		if( instituicao.getId() != null ) {
			instituicaoEntity = instituicaoJpaRepository.findOne(instituicao.getId());
		}
		if( instituicaoEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		instituicaoEntity = new InstituicaoEntity();
		instituicaoServiceMapper.mapInstituicaoToInstituicaoEntity(instituicao, instituicaoEntity);
		InstituicaoEntity instituicaoEntitySaved = instituicaoJpaRepository.save(instituicaoEntity);
		return instituicaoServiceMapper.mapInstituicaoEntityToInstituicao(instituicaoEntitySaved);
	}

	@Override
	public Instituicao update(Instituicao instituicao) {
		InstituicaoEntity instituicaoEntity = instituicaoJpaRepository.findOne(instituicao.getId());
		instituicaoServiceMapper.mapInstituicaoToInstituicaoEntity(instituicao, instituicaoEntity);
		InstituicaoEntity instituicaoEntitySaved = instituicaoJpaRepository.save(instituicaoEntity);
		return instituicaoServiceMapper.mapInstituicaoEntityToInstituicao(instituicaoEntitySaved);
	}
	
	@Override
	public boolean exists(Instituicao instituicao) {
		InstituicaoEntity instituicaoEntity = instituicaoJpaRepository.findByDescricao(instituicao.getDescricao());
		return ( instituicaoEntity != null );
	}

	@Override
	public void delete(Long id) {
		instituicaoJpaRepository.delete(id);
	}

	public void setInstituicaoJpaRepository(InstituicaoJpaRepository instituicaoJpaRepository) {
		this.instituicaoJpaRepository = instituicaoJpaRepository;
	}

	public void setInstituicaoServiceMapper(InstituicaoServiceMapper instituicaoServiceMapper) {
		this.instituicaoServiceMapper = instituicaoServiceMapper;
	}

}
