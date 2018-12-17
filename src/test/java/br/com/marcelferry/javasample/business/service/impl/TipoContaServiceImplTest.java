/*
 * Created on 4 abr 2018 ( Time 10:39:57 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package br.com.marcelferry.javasample.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.marcelferry.javasample.business.service.impl.TipoContaServiceImpl;
import br.com.marcelferry.javasample.business.service.mapping.TipoContaServiceMapper;
import br.com.marcelferry.javasample.data.model.TipoConta;
import br.com.marcelferry.javasample.data.model.jpa.TipoContaEntity;
import br.com.marcelferry.javasample.data.repository.jpa.TipoContaJpaRepository;
import br.com.marcelferry.javasample.test.MockValues;
import br.com.marcelferry.javasample.test.TipoContaEntityFactoryForTest;

/**
 * Test : Implementation of TipoContaService
 */
@RunWith(MockitoJUnitRunner.class)
public class TipoContaServiceImplTest {

	@InjectMocks
	private TipoContaServiceImpl tipoContaService;
	@Mock
	private TipoContaJpaRepository tipoContaJpaRepository;
	@Mock
	private TipoContaServiceMapper tipoContaServiceMapper;
	
	private TipoContaEntityFactoryForTest tipoContaEntityFactoryForTest = new TipoContaEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Long id = mockValues.nextLong();
		
		TipoContaEntity tipoContaEntity = tipoContaJpaRepository.findOne(id);
		
		TipoConta tipoConta = newTipoConta();
		when(tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntity)).thenReturn(tipoConta);

		// When
		TipoConta tipoContaFound = tipoContaService.findById(id);

		// Then
		assertEquals(tipoConta.getId(),tipoContaFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<TipoContaEntity> tipoContaEntitys = new ArrayList<TipoContaEntity>();
		TipoContaEntity tipoContaEntity1 = tipoContaEntityFactoryForTest.newTipoContaEntity();
		tipoContaEntitys.add(tipoContaEntity1);
		TipoContaEntity tipoContaEntity2 = tipoContaEntityFactoryForTest.newTipoContaEntity();
		tipoContaEntitys.add(tipoContaEntity2);
		when(tipoContaJpaRepository.findAll()).thenReturn(tipoContaEntitys);
		
		TipoConta tipoConta1 = newTipoConta();
		when(tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntity1)).thenReturn(tipoConta1);
		TipoConta tipoConta2 = newTipoConta();
		when(tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntity2)).thenReturn(tipoConta2);

		// When
		List<TipoConta> tipoContasFounds = tipoContaService.findAll();

		// Then
		assertTrue(tipoConta1 == tipoContasFounds.get(0));
		assertTrue(tipoConta2 == tipoContasFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		TipoConta tipoConta = newTipoConta();

		TipoContaEntity tipoContaEntity = tipoContaEntityFactoryForTest.newTipoContaEntity();
		when(tipoContaJpaRepository.findOne(tipoConta.getId())).thenReturn(null);
		
		tipoContaEntity = new TipoContaEntity();
		tipoContaServiceMapper.mapTipoContaToTipoContaEntity(tipoConta, tipoContaEntity);
		TipoContaEntity tipoContaEntitySaved = tipoContaJpaRepository.save(tipoContaEntity);
		
		TipoConta tipoContaSaved = newTipoConta();
		when(tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntitySaved)).thenReturn(tipoContaSaved);

		// When
		TipoConta tipoContaResult = tipoContaService.create(tipoConta);

		// Then
		assertTrue(tipoContaResult == tipoContaSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		TipoConta tipoConta = newTipoConta();

		TipoContaEntity tipoContaEntity = tipoContaEntityFactoryForTest.newTipoContaEntity();
		when(tipoContaJpaRepository.findOne(tipoConta.getId())).thenReturn(tipoContaEntity);

		// When
		Exception exception = null;
		try {
			tipoContaService.create(tipoConta);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		TipoConta tipoConta = newTipoConta();

		TipoContaEntity tipoContaEntity = tipoContaEntityFactoryForTest.newTipoContaEntity();
		when(tipoContaJpaRepository.findOne(tipoConta.getId())).thenReturn(tipoContaEntity);
		
		TipoContaEntity tipoContaEntitySaved = tipoContaEntityFactoryForTest.newTipoContaEntity();
		when(tipoContaJpaRepository.save(tipoContaEntity)).thenReturn(tipoContaEntitySaved);
		
		TipoConta tipoContaSaved = newTipoConta();
		when(tipoContaServiceMapper.mapTipoContaEntityToTipoConta(tipoContaEntitySaved)).thenReturn(tipoContaSaved);

		// When
		TipoConta tipoContaResult = tipoContaService.update(tipoConta);

		// Then
		verify(tipoContaServiceMapper).mapTipoContaToTipoContaEntity(tipoConta, tipoContaEntity);
		assertTrue(tipoContaResult == tipoContaSaved);
	}

	@Test
	public void delete() {
		// Given
		Long id = mockValues.nextLong();

		// When
		tipoContaService.delete(id);

		// Then
		verify(tipoContaJpaRepository).delete(id);
		
	}
	
	
    public TipoConta newTipoConta() {

		Long id = mockValues.nextLong();
		String descricao = mockValues.nextString(20);

		TipoConta tipoConta = new TipoConta();
		tipoConta.setId(id);
		tipoConta.setDescricao(descricao);
		return tipoConta;
	}

}
