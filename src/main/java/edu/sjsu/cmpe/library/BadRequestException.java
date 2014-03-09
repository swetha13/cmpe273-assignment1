package edu.sjsu.cmpe.library;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BadRequestException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadRequestException(String msg) {
		super(Response.status(Status.BAD_REQUEST).type("text/plain").
				entity(msg).build());
	}
}
