package com.mario.java.restful.api.hibernate.jpa.resource.response;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

/**
 * TODO
 * document me!
 * @author marioluan
 *
 */
public enum HttpStatus implements StatusType {

	UNPROCESSABLE_ENTITY(422, "Unprocessed Entity");

	private final int statusCode;
    private final String reasonPhrase;
    private final Family family;

	HttpStatus(final int statusCode, final String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.family = Family.familyOf(statusCode);
    }

	@Override
	public int getStatusCode() {
		return this.statusCode;
	}

	@Override
	public Family getFamily() {
		return this.family;
	}

    @Override
	public String getReasonPhrase() {
        return this.toString();
    }

    @Override
	public String toString() {
        return this.reasonPhrase;
    }

    public static HttpStatus fromStatusCode(final int statusCode) {
        for (HttpStatus status : HttpStatus.values()) {
            if (status.statusCode == statusCode) {
                return status;
            }
        }
        return null;
    }
}
