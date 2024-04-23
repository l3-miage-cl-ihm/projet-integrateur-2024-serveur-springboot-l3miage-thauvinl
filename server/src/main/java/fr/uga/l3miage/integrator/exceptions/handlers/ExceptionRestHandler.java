package fr.uga.l3miage.integrator.exceptions.handlers;

import fr.uga.l3miage.integrator.errors.AddJourneeErrorResponse;
import fr.uga.l3miage.integrator.exceptions.rest.AddingTourneeRestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionRestHandler {

    @ExceptionHandler(AddingTourneeRestException.class)
    public ResponseEntity<AddJourneeErrorResponse> handle(HttpServletRequest httpServletRequest, Exception e){
        AddingTourneeRestException exception = (AddingTourneeRestException) e;
        final AddJourneeErrorResponse response = AddJourneeErrorResponse
                .builder()
                .uri(httpServletRequest.getRequestURI())
                .errorMessage(exception.getMessage())
                .build();
        log.warn(exception.getMessage());
        return ResponseEntity.status(404).body(response);
    }


}
