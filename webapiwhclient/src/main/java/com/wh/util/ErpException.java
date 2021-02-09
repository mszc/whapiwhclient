package com.wh.util;

import org.apache.log4j.Logger;

public class ErpException extends Exception {
    private static final Logger log = Logger.getLogger(ErpException.class);

    public ErpException() {}

    public ErpException(String message) {
        super(message);
        log.error(message);
    }

    public ErpException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
