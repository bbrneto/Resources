package br.com.brasilti.resources.enums;

import java.text.MessageFormat;

public enum ErrorEnum {

	INVALID_ELEMENT_KIND("The element kind {0} is invalid."), 
	INVALID_ELEMENT("The element {0} is invalid."), 
	INVALID_VALUE("{0} has an invalid value.");

	private String value;

	private ErrorEnum(String value) {
		this.value = value;
	}

	public String getMessage(Object... params) {
		return MessageFormat.format(this.value, params);
	}

}
