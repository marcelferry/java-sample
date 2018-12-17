/*
 * Created on 4 abr 2018 ( Time 10:39:56 )
 */
package br.com.marcelferry.javasample.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import br.com.marcelferry.javasample.data.model.Instituicao;
import br.com.marcelferry.javasample.data.model.jpa.InstituicaoEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class InstituicaoServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public InstituicaoServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'InstituicaoEntity' to 'Instituicao'
	 * @param instituicaoEntity
	 */
	public Instituicao mapInstituicaoEntityToInstituicao(InstituicaoEntity instituicaoEntity) {
		if(instituicaoEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Instituicao instituicao = map(instituicaoEntity, Instituicao.class);

		return instituicao;
	}
	
	/**
	 * Mapping from 'Instituicao' to 'InstituicaoEntity'
	 * @param instituicao
	 * @param instituicaoEntity
	 */
	public void mapInstituicaoToInstituicaoEntity(Instituicao instituicao, InstituicaoEntity instituicaoEntity) {
		if(instituicao == null) {
			return;
		}

		//--- Generic mapping 
		map(instituicao, instituicaoEntity);

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