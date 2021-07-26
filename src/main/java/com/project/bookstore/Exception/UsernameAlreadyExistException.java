package com.project.bookstore.Exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameAlreadyExistException extends AuthenticationException {

    public UsernameAlreadyExistException(final String msg) {
        super(msg);
    }

}
