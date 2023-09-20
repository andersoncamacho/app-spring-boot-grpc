package br.com.communicationbetweensystems.grpc.exception;

import io.grpc.Status;

public abstract class BaseBussinesException extends RuntimeException {
    public BaseBussinesException(String message) {
        super(message);
    }

    public abstract Status getStatusCode();
    public abstract String getErrorMessage();

}
