package fr.uga.l3miage.integrator.exceptions.rest;

import lombok.Getter;

@Getter
public class NotFoundEntityRestException extends RuntimeException{

    public NotFoundEntityRestException(String message) {
        super(message);
    }
}
