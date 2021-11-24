package org.springframework.samples.ntfh.exceptions;

import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;

public class InvalidValueException extends NestedRuntimeException {
   
   
    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }
}
