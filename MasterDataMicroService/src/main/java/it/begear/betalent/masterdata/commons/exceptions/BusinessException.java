package it.begear.betalent.masterdata.commons.exceptions;

@SuppressWarnings("serial")
public class BusinessException extends Exception {

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable e) {
		super(message, e);
	}

}
