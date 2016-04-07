package org.posjava;

import org.springframework.hateoas.ResourceSupport;

public class ResultadoOperacao extends ResourceSupport{

	public static final String SUCESSO="SUCESSO";
	public static final String FALHA="FALHA";
	
	private ResourceSupport objeto;
	private String status = SUCESSO;
	private String message = "";
	
	public ResourceSupport getObjeto() {
		return objeto;
	}
	public void setObjeto(ResourceSupport objeto) {
		this.objeto = objeto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
