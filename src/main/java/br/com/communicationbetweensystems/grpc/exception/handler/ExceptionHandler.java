package br.com.communicationbetweensystems.grpc.exception.handler;

import br.com.communicationbetweensystems.grpc.exception.BaseBussinesException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ExceptionHandler {

    @GrpcExceptionHandler(BaseBussinesException.class)
    public StatusRuntimeException handlerBusinessException(BaseBussinesException e) {
        return e.getStatusCode().withCause(e.getCause())
                .withDescription(e.getErrorMessage())
                .asRuntimeException();
    }

}
