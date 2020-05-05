package com.freelance.www.base.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorityServerModuleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthorityServerModuleException() {
        log.error("AUTH-ERR-0000", this);
    }

    public AuthorityServerModuleException(String message) {
        super(message);
        log.error("AUTH-ERR-0000", this);
    }

    public AuthorityServerModuleException(String message, Throwable cause) {
        super(message, cause);
        log.error("AUTH-ERR-0000", this);
    }

    public AuthorityServerModuleException(Throwable cause) {
        super(cause);
        log.error("AUTH-ERR-0000", this);
    }
}
