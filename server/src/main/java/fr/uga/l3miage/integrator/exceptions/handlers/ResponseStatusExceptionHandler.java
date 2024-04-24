package fr.uga.l3miage.integrator.exceptions.handlers;

import fr.uga.l3miage.integrator.errors.AddJourneeErrorResponse;
import fr.uga.l3miage.integrator.errors.ResponseStatusErrorResponse;
import fr.uga.l3miage.integrator.exceptions.rest.AddingTourneeRestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ResponseStatusExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseStatusErrorResponse> handle (HttpServletRequest httpServletRequest, Exception e){
        ResponseStatusException exception = (ResponseStatusException) e;
        final ResponseStatusErrorResponse response = ResponseStatusErrorResponse
                .builder()
                .uri(httpServletRequest.getRequestURI())
                .errorMessage(exception.getMessage())
                .build();
        log.warn(exception.getMessage());
        return ResponseEntity.status(400).body(response);
    }

}
