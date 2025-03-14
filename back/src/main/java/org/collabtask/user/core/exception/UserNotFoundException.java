package org.collabtask.user.core.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("User not found : " + message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
