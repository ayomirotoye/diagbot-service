package com.seamhealthgroup.diagbot.exceptions;

public class AuthenticationException extends Exception {
	private static final long serialVersionUID = 7747361060494387904L;

	public AuthenticationException(Exception e) {
		super(e);
	}
}
