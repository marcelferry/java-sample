/*
 * Created on 4 abr 2018 ( Time 10:39:36 )
 */
// This Bean has a basic Primary Key (not composite) 

package br.com.marcelferry.javasample.data.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Persistent class for entity stored in table "fin_contascorrentes"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="fin_contascorrentes")
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="ContacorrenteEntity.countAll", query="SELECT COUNT(x) FROM ContacorrenteEntity x" )
} )
public class ContacorrenteEntity implements Serializable {

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
    @Column(name="CODIGO", length=20)
    private String     codigo       ;

    @Column(name="DESCRICAO", nullable=false, length=50)
    private String     descricao    ;

    @Column(name="AGENCIA", length=13)
    private String     agencia      ;

    @Column(name="CONTA", length=20)
    private String     conta        ;

    @Column(name="VL_SALDO_INICIAL")
    private Double     vlSaldoInicial ;

    @Column(name="VL_LIMITE_CREDITO")
    private Double     vlLimiteCredito ;

    @Column(name="FL_EXC_RESUMO")
    private Boolean    flExcResumo  ;

    @Column(name="OBS")
    private String     obs          ;

    @Column(name="VL_SALDO")
    private Double     vlSaldo      ;

	// "empresaId" (column "EMPRESA_ID") is not defined by itself because used as FK in a link 
	// "tipoContaId" (column "TIPO_CONTA_ID") is not defined by itself because used as FK in a link 
	// "instituicaoId" (column "INSTITUICAO_ID") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="TIPO_CONTA_ID", referencedColumnName="ID")
    private TipoContaEntity finTipoConta;

    @ManyToOne
    @JoinColumn(name="INSTITUICAO_ID", referencedColumnName="ID")
    private InstituicaoEntity finInstituicoes;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ContacorrenteEntity() {
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
    //--- DATABASE MAPPING : CODIGO ( VARCHAR ) 
    public void setCodigo( String codigo ) {
        this.codigo = codigo;
    }
    public String getCodigo() {
        return this.codigo;
    }

    //--- DATABASE MAPPING : DESCRICAO ( VARCHAR ) 
    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return this.descricao;
    }

    //--- DATABASE MAPPING : AGENCIA ( VARCHAR ) 
    public void setAgencia( String agencia ) {
        this.agencia = agencia;
    }
    public String getAgencia() {
        return this.agencia;
    }

    //--- DATABASE MAPPING : CONTA ( VARCHAR ) 
    public void setConta( String conta ) {
        this.conta = conta;
    }
    public String getConta() {
        return this.conta;
    }

    //--- DATABASE MAPPING : VL_SALDO_INICIAL ( DOUBLE ) 
    public void setVlSaldoInicial( Double vlSaldoInicial ) {
        this.vlSaldoInicial = vlSaldoInicial;
    }
    public Double getVlSaldoInicial() {
        return this.vlSaldoInicial;
    }

    //--- DATABASE MAPPING : VL_LIMITE_CREDITO ( DOUBLE ) 
    public void setVlLimiteCredito( Double vlLimiteCredito ) {
        this.vlLimiteCredito = vlLimiteCredito;
    }
    public Double getVlLimiteCredito() {
        return this.vlLimiteCredito;
    }

    //--- DATABASE MAPPING : FL_EXC_RESUMO ( BIT ) 
    public void setFlExcResumo( Boolean flExcResumo ) {
        this.flExcResumo = flExcResumo;
    }
    public Boolean getFlExcResumo() {
        return this.flExcResumo;
    }

    //--- DATABASE MAPPING : OBS ( TEXT ) 
    public void setObs( String obs ) {
        this.obs = obs;
    }
    public String getObs() {
        return this.obs;
    }

    //--- DATABASE MAPPING : VL_SALDO ( DOUBLE ) 
    public void setVlSaldo( Double vlSaldo ) {
        this.vlSaldo = vlSaldo;
    }
    public Double getVlSaldo() {
        return this.vlSaldo;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setFinTipoConta( TipoContaEntity finTipoConta ) {
        this.finTipoConta = finTipoConta;
    }
    public TipoContaEntity getFinTipoConta() {
        return this.finTipoConta;
    }

    public void setFinInstituicoes( InstituicaoEntity finInstituicoes ) {
        this.finInstituicoes = finInstituicoes;
    }
    public InstituicaoEntity getFinInstituicoes() {
        return this.finInstituicoes;
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
        sb.append(agencia);
        sb.append("|");
        sb.append(conta);
        sb.append("|");
        sb.append(vlSaldoInicial);
        sb.append("|");
        sb.append(vlLimiteCredito);
        sb.append("|");
        sb.append(flExcResumo);
        // attribute 'obs' not usable (type = String Long Text)
        sb.append("|");
        sb.append(vlSaldo);
        return sb.toString(); 
    } 

}
