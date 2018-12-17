/*
 * Created on 4 abr 2018 ( Time 10:40:10 )
 */
package br.com.marcelferry.javasample.web.listitem;

import br.com.marcelferry.javasample.data.model.Contacorrente;
import br.com.marcelferry.javasample.web.common.ListItem;

public class ContacorrenteListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public ContacorrenteListItem(Contacorrente contacorrente) {
		super();

		this.value = ""
			 + contacorrente.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = contacorrente.getDescricao();//contacorrente.toString();
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
