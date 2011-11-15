package br.com.brasilti.resources.exceptions;

import br.com.brasilti.resources.enums.ErrorEnum;

public class ResourceException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorEnum errorEnum;

	private Object[] params;

	public ResourceException(ErrorEnum errorEnum, Object... params) {
		this.errorEnum = errorEnum;
		this.params = params;
	}

	@Override
	public String getMessage() {
		return this.errorEnum.getMessage(this.params);
	}

}
