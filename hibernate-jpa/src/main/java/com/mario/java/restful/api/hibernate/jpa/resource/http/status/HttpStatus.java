package com.mario.java.restful.api.hibernate.jpa.resource.http.status;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

/**
 * TODO
 * document me!
 * @author marioluan
 *
 */
public enum HttpStatus implements StatusType {

	UNPROCESSABLE_ENTITY(422, "Unprocessable Entity");

	private final int statusCode;
    private final String reasonPhrase;
    private final Family family;

	HttpStatus(final int statusCode, final String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.family = this.getFamilyFromStatusCode(statusCode);
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
    
    /**
     * Returns its family based on its statusCode.
     * @param statusCode the statusCode of the instance
     * @return the family
     */
    private Family getFamilyFromStatusCode(int statusCode) {
    	Family family = null;
    	
    	switch(statusCode/100) {
            case 1: family = Family.INFORMATIONAL; break;
            case 2: family = Family.SUCCESSFUL; break;
            case 3: family = Family.REDIRECTION; break;
            case 4: family = Family.CLIENT_ERROR; break;
            case 5: family = Family.SERVER_ERROR; break;
            default: family = Family.OTHER; break;
        }
    	
    	return family;
    }
}
