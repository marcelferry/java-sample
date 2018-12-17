/*
 * Created on 4 abr 2018 ( Time 10:40:10 )
 */
package br.com.marcelferry.javasample.data.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;


public class Instituicao implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @ApiModelProperty(notes = "Identificador único de Instituição")
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @ApiModelProperty(notes = "Código que representa a instituição, no caso bancário o numero do banco. Ex. 341 para Itaú")
    private Integer codigo;

    @NotNull
    @Size( min = 1, max = 50 )
    @ApiModelProperty(notes = "Descrição ou Nome da instituição")
    private String descricao;

    @Size( max = 256 )
    @ApiModelProperty(notes = "Caminho para a Logo da Instituição")
    private String image;

	//----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Long id ) {
        this.id = id ;
    }

    public Long getId() {
        return this.id;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setCodigo( Integer codigo ) {
        this.codigo = codigo;
    }
    public Integer getCodigo() {
        return this.codigo;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return this.descricao;
    }

    public void setImage( String image ) {
        this.image = image;
    }
    public String getImage() {
        return this.image;
    }
    
    


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instituicao other = (Instituicao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString() { 
		StringBuilder sb = new StringBuilder(); 
        sb.append(id);
        sb.append("|");
        sb.append(codigo);
        sb.append("|");
        sb.append(descricao);
        sb.append("|");
        sb.append(image);
        return sb.toString(); 
    } 


}
