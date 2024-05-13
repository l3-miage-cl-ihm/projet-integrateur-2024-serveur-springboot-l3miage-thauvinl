package fr.uga.l3miage.integrator.exceptions.handlers;

import fr.uga.l3miage.integrator.errors.BadRequestErrorResponse;
import fr.uga.l3miage.integrator.exceptions.rest.BadRequestRestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class BadRequestRestExceptionHandler {
    @ExceptionHandler(BadRequestRestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadRequestErrorResponse handle (HttpServletRequest httpServletRequest, Exception e){
        BadRequestRestException exception = (BadRequestRestException) e;
        log.warn(exception.getMessage());
         return BadRequestErrorResponse
                .builder()
                .uri(httpServletRequest.getRequestURI())
                .errorMessage(exception.getMessage())
                .build();
    }

}
