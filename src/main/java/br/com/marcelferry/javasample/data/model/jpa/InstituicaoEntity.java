/*
 * Created on 4 abr 2018 ( Time 10:39:36 )
 */
// This Bean has a basic Primary Key (not composite) 

package br.com.marcelferry.javasample.data.model.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Persistent class for entity stored in table "fin_instituicoes"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="fin_instituicoes")
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="InstituicaoEntity.countAll", query="SELECT COUNT(x) FROM InstituicaoEntity x" )
} )
public class InstituicaoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long       id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="CODIGO")
    private Integer    codigo       ;

    @Column(name="DESCRICAO", nullable=false, length=50)
    private String     descricao    ;

    @Column(name="IMAGE", length=256)
    private String     image        ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="finInstituicoes", targetEntity=ContacorrenteEntity.class)
    private List<ContacorrenteEntity> listOfFinContascorrentes;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public InstituicaoEntity() {
		super();
    }
    
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
    //--- DATABASE MAPPING : CODIGO ( INT ) 
    public void setCodigo( Integer codigo ) {
        this.codigo = codigo;
    }
    public Integer getCodigo() {
        return this.codigo;
    }

    //--- DATABASE MAPPING : DESCRICAO ( VARCHAR ) 
    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return this.descricao;
    }

    //--- DATABASE MAPPING : IMAGE ( VARCHAR ) 
    public void setImage( String image ) {
        this.image = image;
    }
    public String getImage() {
        return this.image;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfFinContascorrentes( List<ContacorrenteEntity> listOfFinContascorrentes ) {
        this.listOfFinContascorrentes = listOfFinContascorrentes;
    }
    public List<ContacorrenteEntity> getListOfFinContascorrentes() {
        return this.listOfFinContascorrentes;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(codigo);
        sb.append("|");
        sb.append(descricao);
        sb.append("|");
        sb.append(image);
        return sb.toString(); 
    } 

}
