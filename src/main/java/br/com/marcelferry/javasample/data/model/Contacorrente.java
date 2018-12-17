/*
 * Created on 4 abr 2018 ( Time 10:40:10 )
 */
package br.com.marcelferry.javasample.data.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;


public class Contacorrente implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @ApiModelProperty(notes = "Identificador único de conta corrente")
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Size( max = 20 )
    @ApiModelProperty(notes = "Codigo que respresenta a conta")
    private String codigo;

    @NotNull
    @Size( min = 1, max = 50 )
    @ApiModelProperty(notes = "Descrição da conta", required=true)
    private String descricao;


    @NotNull
    @ApiModelProperty(notes = "Tipo de conta corrente", required=true)
    private Long tipoContaId;

    @ApiModelProperty(notes = "Instituição da conta corrente")
    private Long instituicaoId;

    @Size( max = 13 )
    @ApiModelProperty(notes = "Agência da conta corrente")
    private String agencia;

    @Size( max = 20 )
    @ApiModelProperty(notes = "Número da conta corrente")
    private String conta;

    @ApiModelProperty(notes = "Valor do Saldo Inicial")
    private Double vlSaldoInicial;

    @ApiModelProperty(notes = "Valor do Limite")
    private Double vlLimiteCredito;

    @ApiModelProperty(notes = "Incluir conta no resumo executivo")
    private Boolean flExcResumo;

    @Size( max = 65535 )
    @ApiModelProperty(notes = "Observações da conta corrente")
    private String obs;

    @ApiModelProperty(notes = "Valor do Saldo da Conta corrente")
    private Double vlSaldo;

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
    public void setCodigo( String codigo ) {
        this.codigo = codigo;
    }
    public String getCodigo() {
        return this.codigo;
    }

    public void setDescricao( String descricao ) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return this.descricao;
    }

    public void setTipoContaId( Long tipoContaId ) {
        this.tipoContaId = tipoContaId;
    }
    public Long getTipoContaId() {
        return this.tipoContaId;
    }

    public void setInstituicaoId( Long instituicaoId ) {
        this.instituicaoId = instituicaoId;
    }
    public Long getInstituicaoId() {
        return this.instituicaoId;
    }

    public void setAgencia( String agencia ) {
        this.agencia = agencia;
    }
    public String getAgencia() {
        return this.agencia;
    }

    public void setConta( String conta ) {
        this.conta = conta;
    }
    public String getConta() {
        return this.conta;
    }

    public void setVlSaldoInicial( Double vlSaldoInicial ) {
        this.vlSaldoInicial = vlSaldoInicial;
    }
    public Double getVlSaldoInicial() {
        return this.vlSaldoInicial;
    }

    public void setVlLimiteCredito( Double vlLimiteCredito ) {
        this.vlLimiteCredito = vlLimiteCredito;
    }
    public Double getVlLimiteCredito() {
        return this.vlLimiteCredito;
    }

    public void setFlExcResumo( Boolean flExcResumo ) {
        this.flExcResumo = flExcResumo;
    }
    public Boolean getFlExcResumo() {
        return this.flExcResumo;
    }

    public void setObs( String obs ) {
        this.obs = obs;
    }
    public String getObs() {
        return this.obs;
    }

    public void setVlSaldo( Double vlSaldo ) {
        this.vlSaldo = vlSaldo;
    }
    public Double getVlSaldo() {
        return this.vlSaldo;
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
		Contacorrente other = (Contacorrente) obj;
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
        sb.append(tipoContaId);
        sb.append("|");
        sb.append(instituicaoId);
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
