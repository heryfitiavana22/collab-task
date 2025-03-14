package org.collabtask.helpers.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class BadRequestMessageException extends WebApplicationException {
    public BadRequestMessageException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(new Message(message)).build());
    }

    public BadRequestMessageException(Throwable throwable) {
        this(throwable.getMessage());
    }
}
