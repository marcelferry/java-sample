/*
 * Created on 4 abr 2018 ( Time 10:39:56 )
 */
package br.com.marcelferry.javasample.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import br.com.marcelferry.javasample.data.model.Contacorrente;
import br.com.marcelferry.javasample.data.model.jpa.ContacorrenteEntity;
import br.com.marcelferry.javasample.data.model.jpa.InstituicaoEntity;
import br.com.marcelferry.javasample.data.model.jpa.TipoContaEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class ContacorrenteServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ContacorrenteServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ContacorrenteEntity' to 'Contacorrente'
	 * @param contacorrenteEntity
	 */
	public Contacorrente mapContacorrenteEntityToContacorrente(ContacorrenteEntity contacorrenteEntity) {
		if(contacorrenteEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Contacorrente contacorrente = map(contacorrenteEntity, Contacorrente.class);

		//--- Link mapping ( link to TipoConta )
		if(contacorrenteEntity.getFinTipoConta() != null) {
			contacorrente.setTipoContaId(contacorrenteEntity.getFinTipoConta().getId());
		}
		//--- Link mapping ( link to Instituicao )
		if(contacorrenteEntity.getFinInstituicoes() != null) {
			contacorrente.setInstituicaoId(contacorrenteEntity.getFinInstituicoes().getId());
		}
		return contacorrente;
	}
	
	/**
	 * Mapping from 'Contacorrente' to 'ContacorrenteEntity'
	 * @param contacorrente
	 * @param contacorrenteEntity
	 */
	public void mapContacorrenteToContacorrenteEntity(Contacorrente contacorrente, ContacorrenteEntity contacorrenteEntity) {
		if(contacorrente == null) {
			return;
		}

		//--- Generic mapping 
		map(contacorrente, contacorrenteEntity);

		//--- Link mapping ( link : contacorrente )
		if( hasLinkToTipoConta(contacorrente) ) {
			TipoContaEntity tipoConta1 = new TipoContaEntity();
			tipoConta1.setId( contacorrente.getTipoContaId() );
			contacorrenteEntity.setFinTipoConta( tipoConta1 );
		} else {
			contacorrenteEntity.setFinTipoConta( null );
		}

		//--- Link mapping ( link : contacorrente )
		if( hasLinkToInstituicao(contacorrente) ) {
			InstituicaoEntity instituicao2 = new InstituicaoEntity();
			instituicao2.setId( contacorrente.getInstituicaoId() );
			contacorrenteEntity.setFinInstituicoes( instituicao2 );
		} else {
			contacorrenteEntity.setFinInstituicoes( null );
		}

	}
	
	/**
	 * Verify that TipoConta id is valid.
	 * @param TipoConta TipoConta
	 * @return boolean
	 */
	private boolean hasLinkToTipoConta(Contacorrente contacorrente) {
		if(contacorrente.getTipoContaId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Verify that Instituicao id is valid.
	 * @param Instituicao Instituicao
	 * @return boolean
	 */
	private boolean hasLinkToInstituicao(Contacorrente contacorrente) {
		if(contacorrente.getInstituicaoId() != null) {
			return true;
		}
		return false;
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