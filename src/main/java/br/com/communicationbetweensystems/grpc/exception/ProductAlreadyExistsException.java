package br.com.communicationbetweensystems.grpc.exception;

import io.grpc.Status;

public class ProductAlreadyExistsException extends BaseBussinesException {

    private static final String ERROR_MESSAGE = "Produto %s já cadastrado no sistema";
    private final String name;

    public ProductAlreadyExistsException(String name) {
        super(String.format(ERROR_MESSAGE, name));
        this.name = name;
    }

    @Override
    public Status getStatusCode() {
        return Status.ALREADY_EXISTS;
    }

    @Override
    public String getErrorMessage() {
        return String.format(ERROR_MESSAGE, this.name);
    }
}
