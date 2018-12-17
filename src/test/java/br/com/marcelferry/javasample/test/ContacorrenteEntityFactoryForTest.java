package br.com.marcelferry.javasample.test;

import br.com.marcelferry.javasample.data.model.jpa.ContacorrenteEntity;

public class ContacorrenteEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ContacorrenteEntity newContacorrenteEntity() {

		Long id = mockValues.nextLong();

		ContacorrenteEntity contacorrenteEntity = new ContacorrenteEntity();
		contacorrenteEntity.setId(id);
		return contacorrenteEntity;
	}
	
}
