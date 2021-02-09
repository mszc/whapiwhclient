package com.wh.util;

import org.apache.log4j.Logger;

public class ErpRunException extends RuntimeException {
    private static final Logger log = Logger.getLogger(ErpRunException.class);

    public ErpRunException() {}

    public ErpRunException(String message) {
        super(message);
        log.error(message);
    }

    public ErpRunException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
