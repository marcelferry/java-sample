package br.com.marcelferry.javasample.test;

import br.com.marcelferry.javasample.data.model.jpa.TipoContaEntity;

public class TipoContaEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TipoContaEntity newTipoContaEntity() {

		Long id = mockValues.nextLong();

		TipoContaEntity tipoContaEntity = new TipoContaEntity();
		tipoContaEntity.setId(id);
		return tipoContaEntity;
	}
	
}
