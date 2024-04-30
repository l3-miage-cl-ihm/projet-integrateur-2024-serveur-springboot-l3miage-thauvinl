package fr.uga.l3miage.integrator.exceptions.handlers;

import fr.uga.l3miage.integrator.errors.NotFoundErrorResponse;
import fr.uga.l3miage.integrator.exceptions.rest.NotFoundEntityRestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionNotFoundEntityHandler {


    @ExceptionHandler(NotFoundEntityRestException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundErrorResponse handle(HttpServletRequest httpServletRequest, Exception e){
        NotFoundEntityRestException exception = (NotFoundEntityRestException) e;
        log.warn(exception.getMessage());
        return  NotFoundErrorResponse.
                builder()
                .errorMessage(exception.getMessage())
                .uri(httpServletRequest.getRequestURI())
                .build();
    }
}