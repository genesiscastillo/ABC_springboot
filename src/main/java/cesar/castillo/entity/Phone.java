package cesar.castillo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TUSUARIOPHONES" , schema="APP")
public class Phone implements Serializable {

	private static final long serialVersionUID = -8742763963603970871L;

	@Column(name="ID")
	private String id;
	
	@Column(name="CODIGO_CITY")
	private String codigoCity;

	@Column(name="CODIGO_PAIS")
	private String codigoPais;

	@Id
	@Column(name="ITEM")
	private String item;

	@Column(name="NUMERO")
	private String numero;

	public String getCodigoCity() {
		return codigoCity;
	}

	public void setCodigoCity(String codigoCity) {
		this.codigoCity = codigoCity;
	}

	public String getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
