package org.collabtask.task.core.exception;

public class InvalidTaskException extends RuntimeException {
    public InvalidTaskException(String message) {
        super("Invalid task : " + message);
    }

    public InvalidTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTaskException(Throwable cause) {
        super(cause);
    }
}
