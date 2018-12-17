/*
 * Created on 4 abr 2018 ( Time 10:39:57 )
 */
package br.com.marcelferry.javasample.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import br.com.marcelferry.javasample.data.model.TipoConta;
import br.com.marcelferry.javasample.data.model.jpa.TipoContaEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TipoContaServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public TipoContaServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'TipoContaEntity' to 'TipoConta'
	 * @param tipoContaEntity
	 */
	public TipoConta mapTipoContaEntityToTipoConta(TipoContaEntity tipoContaEntity) {
		if(tipoContaEntity == null) {
			return null;
		}

		//--- Generic mapping 
		TipoConta tipoConta = map(tipoContaEntity, TipoConta.class);

		return tipoConta;
	}
	
	/**
	 * Mapping from 'TipoConta' to 'TipoContaEntity'
	 * @param tipoConta
	 * @param tipoContaEntity
	 */
	public void mapTipoContaToTipoContaEntity(TipoConta tipoConta, TipoContaEntity tipoContaEntity) {
		if(tipoConta == null) {
			return;
		}

		//--- Generic mapping 
		map(tipoConta, tipoContaEntity);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}