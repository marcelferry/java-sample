package br.com.marcelferry.javasample.test;

import br.com.marcelferry.javasample.data.model.jpa.InstituicaoEntity;

public class InstituicaoEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public InstituicaoEntity newInstituicaoEntity() {

		Long id = mockValues.nextLong();

		InstituicaoEntity instituicaoEntity = new InstituicaoEntity();
		instituicaoEntity.setId(id);
		return instituicaoEntity;
	}
	
}
