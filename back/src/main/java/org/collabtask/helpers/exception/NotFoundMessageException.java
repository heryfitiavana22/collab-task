package org.collabtask.helpers.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class NotFoundMessageException extends WebApplicationException {
    public NotFoundMessageException(String message) {
        super(Response.status(Response.Status.NOT_FOUND).entity(new Message(message)).build());
    }

    public NotFoundMessageException(Throwable throwable) {
        this(throwable.getMessage());
    }
}
