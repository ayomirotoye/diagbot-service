package com.seamhealthgroup.diagbot.exceptions;

public class InternalServerErrorException extends Exception {
	private static final long serialVersionUID = 7747361060494387904L;

	public InternalServerErrorException(Exception e) {
		super(e);
	}
}
