package org.zerock.apiserver.util;

public class CustomServiceException extends RuntimeException {

    public CustomServiceException(String msg) {
        super(msg);
    }
}
