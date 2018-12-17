/*
 * Created on 4 abr 2018 ( Time 10:40:11 )
 */
package br.com.marcelferry.javasample.web.listitem;

import br.com.marcelferry.javasample.data.model.TipoConta;
import br.com.marcelferry.javasample.web.common.ListItem;

public class TipoContaListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public TipoContaListItem(TipoConta tipoConta) {
		super();

		this.value = ""
			 + tipoConta.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = tipoConta.getDescricao();//tipoConta.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
